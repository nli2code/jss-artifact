package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IRConditional extends IRDefStatement {
    private final IRExpression condition;
    private final IRExpression thenExpr;
    private final IRExpression elseExpr;

    public IRConditional(CFG cfg, String ty, Set<Node> from, IRExpression condition, IRExpression thenExpr, IRExpression elseExpr) {
        super(cfg, ty, from);
        this.condition = condition;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(condition, thenExpr, elseExpr);
    }
}
