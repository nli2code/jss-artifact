package cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Value
@Wither
public class ForStmt implements Stmt<ForStmt> {
    private final List<Expr<?>> inits;
    @Nullable
    private final Expr<?> cond;
    private final List<Expr<?>> updates;
    private final BlockStmt body;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> ForStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
