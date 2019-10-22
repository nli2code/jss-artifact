package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import cn.edu.pku.hcst.kincoder.pattern.api.Pipe;
import cn.edu.pku.hcst.kincoder.pattern.api.filter.Filter;
import cn.edu.pku.hcst.kincoder.pattern.api.filter.ListFilter;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGFactory;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions.IRArg;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.CompilationUnit.Storage;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.google.common.collect.Streams;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class JavaProjectParser implements Pipe<Path, Collection<DFG>> {
    private final ThreadLocal<JavaParser> javaParser;
    private final CodeUtil codeUtil;
    private final PatternConfig config;
    private final DFGFactory dfgFactory;

    private ParseResult<CompilationUnit> parse(File file) {
        try {
            return javaParser.get().parse(file);
        } catch (Throwable e) {
            return new ParseResult<>(null, List.of(new Problem(e.getMessage(), null, e)), null);
        }
    }

    private Optional<String> resolveType(Type ty) {
        try {
            return Optional.of(ty.resolve().describe());
        } catch (Throwable e) {
            return Optional.empty();
        }
    }

    private Pipe<Path, List<File>> fileGenerator = path ->
        Streams.stream(Files.fileTraverser().depthFirstPreOrder(new File(path.toUri())))
            .filter(Predicate.not(File::isDirectory))
            .filter(f -> f.getName().endsWith(".java"))
            .collect(Collectors.toList());

    private Pipe<List<File>, List<CompilationUnit>> files2cus = files ->
        files.parallelStream()
            .map(this::parse)
            .filter(ParseResult::isSuccessful)
            .map(ParseResult::getResult)
            .map(Optional::get)
            .collect(Collectors.toList());

    private Pipe<List<CompilationUnit>, List<CFG>> cus2cfgs;

    private Pipe<List<CFG>, List<DFG>> cfgs2dfgs;

    private List<ListFilter<File>> fileFilters = new ArrayList<>();
    private List<ListFilter<CompilationUnit>> cuFilters = new ArrayList<>();
    private List<ListFilter<CFG>> cfgFilters = new ArrayList<>();
    private List<ListFilter<DFG>> dfgFilters = new ArrayList<>();

    public JavaProjectParser(List<String> sourceCodeDirs, CodeUtil codeUtil, PatternConfig config, DFGFactory dfgFactory) {
        this.codeUtil = codeUtil;
        this.config = config;
        this.dfgFactory = dfgFactory;
        var typeSolvers = sourceCodeDirs.stream()
            .map(Path::of)
            .map(JavaParserTypeSolver::new)
            .toArray(JavaParserTypeSolver[]::new);

        var parserConfiguration = new ParserConfiguration();
        parserConfiguration.setSymbolResolver(new JavaSymbolSolver(new CombinedTypeSolver(typeSolvers)));
        this.javaParser = ThreadLocal.withInitial(() -> new JavaParser(parserConfiguration));

        this.cus2cfgs = cus ->
            cus.stream()
                .flatMap(cu -> cu.findAll(CallableDeclaration.class).stream())
                .map(decl -> (CallableDeclaration<?>) decl)
                .flatMap(decl -> {
                    try {
                        var path = decl.findCompilationUnit().flatMap(CompilationUnit::getStorage).map(Storage::getPath).orElse(null);
                        var cfg = new CFG(path, decl.getName().asString(), decl);
                        BlockStmt body;
                        if (decl instanceof ConstructorDeclaration) {
                            body = decl.asConstructorDeclaration().getBody();
                        } else if (decl instanceof MethodDeclaration) {
                            body = decl.asMethodDeclaration().getBody().orElse(new BlockStmt());
                        } else {
                            body = new BlockStmt();
                        }
                        decl.getParameters().forEach(p -> {
                            resolveType(p.getType()).ifPresent(ty -> cfg.writeVar(p.getName().getIdentifier(), cfg.getEntry(), new IRArg(p.getName().getIdentifier(), ty)));
                        });
                        var statementVisitor = new JavaStatementVisitor(codeUtil, config, cfg);
                        var pair = body.accept(statementVisitor.getVisitor(), cfg.new Context(cfg.getEntry()));
                        pair.getBlock().seal();
                        pair.getBlock().setNext(cfg.getExit());
                        return Stream.of(cfg);
                    } catch (Throwable e) {
                        log.error("Cfg generate error", e);
                        return Stream.of();
                    }
                })
                .collect(Collectors.toList());

        this.cfgs2dfgs = cfgs -> cfgs.stream()
            .map(dfgFactory::create)
            .collect(Collectors.toList());
    }

    public JavaProjectParser register(ListFilter<DFG> filter) {
        dfgFilters.add(filter);
        return this;
    }

    @Override
    public Collection<DFG> process(Path path) {
        var pipeline = fileGenerator.connect(Filter.reduce(fileFilters))
            .connect(files2cus)
            .connect(Filter.reduce(cuFilters))
            .connect(cus2cfgs)
            .connect(Filter.reduce(cfgFilters))
            .connect(cfgs2dfgs)
            .connect(Filter.reduce(dfgFilters));

        return pipeline.process(path);
    }
}
