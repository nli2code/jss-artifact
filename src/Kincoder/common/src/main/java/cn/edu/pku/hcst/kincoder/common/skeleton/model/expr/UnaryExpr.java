package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class UnaryExpr implements Expr<UnaryExpr> {
    private final String ope;
    private final Expr<?> expr;
    private final boolean prefix;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> UnaryExpr accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
