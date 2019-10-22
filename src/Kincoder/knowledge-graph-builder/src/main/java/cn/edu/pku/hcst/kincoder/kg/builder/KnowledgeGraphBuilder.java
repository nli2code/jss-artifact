package cn.edu.pku.hcst.kincoder.kg.builder;

import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphSessionFactory;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserAnonymousClassDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import com.google.inject.Guice;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class KnowledgeGraphBuilder {

    private final EntityManager entityManager;
    private final KnowledgeGraphBuilderConfig builderConfig;
    private final KnowledgeGraphSessionFactory sessionFactory;
    private final Session session;

    @Inject
    public KnowledgeGraphBuilder(EntityManager entityManager, KnowledgeGraphBuilderConfig builderConfig, KnowledgeGraphSessionFactory sessionFactory) {
        this.entityManager = entityManager;
        this.builderConfig = builderConfig;
        this.sessionFactory = sessionFactory;
        this.session = sessionFactory.get();
    }

    private void buildTypeMapping(Stream<TypeDeclaration<?>> typeDeclarations) {
        typeDeclarations.map(TypeDeclaration::resolve).forEach(entityManager::createTypeEntity);
    }

    private void buildExtendRelation(Stream<ClassOrInterfaceDeclaration> classDecls) {
        classDecls.forEach(decl -> {
            var qualifiedName = decl.resolve().getQualifiedName();
            var typeEntity = entityManager.getTypeEntityOrCreate(qualifiedName);
            var parentTypes = Stream.concat(decl.getExtendedTypes().stream(), decl.getImplementedTypes().stream());
            typeEntity.addExtendedTypes(
                parentTypes
                    .map(this::type2entity)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toSet())
            );
            typeEntity.setIterableType(getIterableType(decl.resolve()));
        });
    }

    private void buildMethodExtendRelation(Stream<CompilationUnit> cus) {
        cus.map(cu -> cu.findAll(MethodDeclaration.class))
            .flatMap(Collection::stream)
            .forEach(decl -> {
                try {
                    var resolvedMethod = (JavaParserMethodDeclaration) decl.resolve();
                    assert resolvedMethod.getWrappedNode().getParentNode().isPresent();
                    var parentNode = resolvedMethod.getWrappedNode().getParentNode().get();
                    if (!(parentNode instanceof ObjectCreationExpr)) { // ignore AnonymousClass
                        var qualifiedSignature = resolvedMethod.getQualifiedSignature();
                        var methodEntity = entityManager.getMethodEntity(qualifiedSignature);
                        var typeEntity = methodEntity.getDeclareType();
                        methodEntity.addExtendedMethods(
                            typeEntity.getExtendedTypes().stream()
                                .map(TypeEntity::getHasMethods)
                                .flatMap(Collection::stream)
                                .filter(m -> m.getSignature().equals(methodEntity.getSignature()))
                                .collect(Collectors.toSet())
                        );
                    }
                } catch (UnsolvedSymbolException e) {
                    if (builderConfig.isPrintUnsolvedSymbol()) {
                        log.warn("Unsolved Symbol", e);
                    }
                } catch (Throwable e) {
                    log.error("", e);
                }
            });
    }

    private void buildProduceRelation(List<CompilationUnit> cus) {
        cus.stream().map(cu -> cu.findAll(MethodDeclaration.class))
            .flatMap(Collection::stream)
            .forEach(decl -> {
                try {
                    var resolvedMethod = decl.resolve();
                    if (!(resolvedMethod.declaringType() instanceof JavaParserAnonymousClassDeclaration)) {
                        var methodEntity = entityManager.getMethodEntity(resolvedMethod.getQualifiedSignature());
                        var returnType = resolvedMethod.getReturnType();
                        if (returnType.isReferenceType()) {
                            var name = returnType.asReferenceType().getQualifiedName();
                            methodEntity.setReturns(entityManager.getTypeEntityOrCreate(name));
                        } else if (returnType.isArray()) {
                            var name = getComponentTypeRecursively(returnType);
                            if (name.isReferenceType()) {
                                methodEntity.setReturnsMultiple(entityManager.getTypeEntityOrCreate(name.asReferenceType().getQualifiedName()));
                            }
                        }

                    }
                } catch (UnsolvedSymbolException | UnsupportedOperationException | IllegalArgumentException e) {
                    if (builderConfig.isPrintUnsolvedSymbol()) {
                        log.warn("Unsolved Symbol", e);
                    }
                } catch (Throwable e) {
                    log.error("", e);
                }
            });
        cus.stream().map(cu -> cu.findAll(ConstructorDeclaration.class))
            .flatMap(Collection::stream)
            .forEach(decl -> {
                try {
                    var resolvedMethod = decl.resolve();
                    if (!(resolvedMethod.declaringType() instanceof JavaParserAnonymousClassDeclaration)) {
                        var methodEntity = entityManager.getMethodEntity(resolvedMethod.getQualifiedSignature());
                        var returnType = resolvedMethod.declaringType();
                        var name = returnType.asReferenceType().getQualifiedName();
                        methodEntity.setReturns(entityManager.getTypeEntityOrCreate(name));
                    }
                } catch (UnsolvedSymbolException e) {
                    if (builderConfig.isPrintUnsolvedSymbol()) {
                        log.warn("Unsolved Symbol", e);
                    }
                } catch (Throwable e) {
                    log.error("", e);
                }
            });
    }

    private Optional<TypeEntity> type2entity(ClassOrInterfaceType decl) {
        try {
            var qualifiedName = decl.resolve().getQualifiedName();
            return Optional.of(entityManager.getTypeEntityOrCreate(qualifiedName));
        } catch (UnsolvedSymbolException e) {
            if (builderConfig.isPrintUnsolvedSymbol()) {
                log.warn("Unsolved Symbol", e);
            }
            return Optional.empty();
        }
    }

    private TypeEntity getIterableType(ResolvedReferenceTypeDeclaration resolved) {
        try {
            return resolved.getAllAncestors().stream()
                .filter(ancestor -> ancestor.getQualifiedName().equals("java.lang.Iterable"))
                .findFirst()
                .map(type -> type.getGenericParameterByName("T").get())
                .filter(ResolvedType::isReferenceType)
                .map(ResolvedType::asReferenceType)
                .map(ResolvedReferenceType::getQualifiedName)
                .map(entityManager::getTypeEntityOrCreate)
                .orElse(null);
        } catch (UnsolvedSymbolException e) {
            if (builderConfig.isPrintUnsolvedSymbol()) {
                log.warn("Unsolved Symbol", e);
            }
            return null;
        }
    }

    private ResolvedType getComponentTypeRecursively(ResolvedType ty) {
        return ty.isArray() ? getComponentTypeRecursively(ty.asArrayType().getComponentType()) : ty;
    }

    public void build() {
        var strategy = new SymbolSolverCollectionStrategy();

        log.info("Start parsing codes.");

        var cus = builderConfig.getProjectSrcCodeDirs().stream()
            .map(Path::of)
            .map(strategy::collect)
            .map(ProjectRoot::getSourceRoots)
            .flatMap(Collection::stream)
            .map(SourceRoot::tryToParseParallelized)
            .flatMap(Collection::stream)
            .filter(ParseResult::isSuccessful)
            .map(ParseResult::getResult)
            .map(Optional::get)
            .collect(Collectors.toList());

        var classOrInterfaceDeclarations = cus.stream().flatMap(cu -> cu.findAll(ClassOrInterfaceDeclaration.class).stream()).collect(Collectors.toList());
        var enumDeclarations = cus.stream().flatMap(cu -> cu.findAll(EnumDeclaration.class).stream()).collect(Collectors.toList());

        log.info("Start building type entities");
        buildTypeMapping(Stream.concat(classOrInterfaceDeclarations.stream(), enumDeclarations.stream()));
        log.info("Start building extend relations");
        buildExtendRelation(classOrInterfaceDeclarations.stream());
        log.info("Start building method extend relations");
        buildMethodExtendRelation(cus.stream());
        log.info("Start building produce relations");
        buildProduceRelation(cus);

        entityManager.save(session);
        sessionFactory.close();
    }

    public static void main(String[] args) throws IOException {
        var yaml = new Yaml();

        try (var configFile = KnowledgeGraphBuilder.class.getResourceAsStream("/application.yaml")) {
            var config = yaml.loadAs(configFile, KnowledgeGraphBuilderConfig.class);
            var injector = Guice.createInjector(new BuilderModule(config));
            var builder = injector.getInstance(KnowledgeGraphBuilder.class);
            builder.build();
        }
    }


}
