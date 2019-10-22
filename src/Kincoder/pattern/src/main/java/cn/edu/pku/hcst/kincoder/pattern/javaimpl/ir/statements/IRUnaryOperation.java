package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.UnaryExpr.Operator;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public class IRUnaryOperation extends IRDefStatement {
    private final IRExpression source;
    @Getter
    private final Operator ope;

    public IRUnaryOperation(CFG cfg, String ty, Set<Node> from, IRExpression source, Operator ope) {
        super(cfg, ty, from);
        this.source = source;
        this.ope = ope;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(source);
    }
}
