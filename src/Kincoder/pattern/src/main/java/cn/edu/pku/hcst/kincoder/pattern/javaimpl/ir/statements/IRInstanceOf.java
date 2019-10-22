package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IRInstanceOf extends IRDefStatement {
    private final IRExpression source;
    private final String targetType;

    public IRInstanceOf(CFG cfg, String ty, Set<Node> from, IRExpression source, String targetType) {
        super(cfg, ty, from);
        this.source = source;
        this.targetType = targetType;
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(source);
    }
}
