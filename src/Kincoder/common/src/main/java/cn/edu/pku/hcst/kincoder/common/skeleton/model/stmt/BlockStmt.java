package cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.List;

@Value
@Wither
public class BlockStmt implements Stmt<BlockStmt> {
    private final List<Stmt<?>> statements;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> BlockStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
