package cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class ForEachStmt implements Stmt<ForEachStmt> {
    private final Type type;
    private final String name;
    private final Expr<?> iterable;
    private final BlockStmt body;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> ForEachStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
