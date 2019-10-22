package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public class IRBinaryOperation extends IRDefStatement {
    private final IRExpression lhs;
    private final IRExpression rhs;
    @Getter
    private final BinaryExpr.Operator ope;

    public IRBinaryOperation(CFG cfg, String ty, Set<Node> from, IRExpression lhs, IRExpression rhs, Operator ope) {
        super(cfg, ty, from);
        this.lhs = lhs;
        this.rhs = rhs;
        this.ope = ope;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(lhs, rhs);
    }
}
