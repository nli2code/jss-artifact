package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.api.GraphGenerator;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import cn.edu.pku.hcst.kincoder.pattern.api.filter.DFGNodeFilter;
import cn.edu.pku.hcst.kincoder.pattern.api.filter.DFGSizeFilter;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGFactory;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode.Type;

import java.io.File;
import java.util.Collection;

public class JavaDFGGenerator implements GraphGenerator<File, DFG> {
    private final CodeUtil codeUtil;
    private final PatternConfig config;
    private final DFGFactory dfgFactory;

    public JavaDFGGenerator(CodeUtil codeUtil, PatternConfig config, DFGFactory dfgFactory) {
        this.codeUtil = codeUtil;
        this.config = config;
        this.dfgFactory = dfgFactory;
    }

    @Override
    public Collection<DFG> generate(File source) {
        var projectParser = new JavaProjectParser(config.getSourceCodeDirs(), codeUtil, config, dfgFactory);
        if (config.getNodeSizeLimit() != null) projectParser.register(new DFGSizeFilter(config.getNodeSizeLimit()));
        config.getDfgNodeFilters().forEach(f -> projectParser.register(new DFGNodeFilter(new DFGNode(Type.METHOD_INVOCATION, f))));
        return projectParser.process(source.toPath());
    }
}
