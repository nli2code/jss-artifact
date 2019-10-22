package cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;
import org.jetbrains.annotations.Nullable;

@Value
@Wither
public class IfStmt implements Stmt<IfStmt> {
    private final Expr<?> cond;
    private final BlockStmt thenBody;
    @Nullable
    private final BlockStmt elseBody;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> IfStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
