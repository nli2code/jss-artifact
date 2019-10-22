package cn.edu.pku.hcst.kincoder.common.skeleton;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.BlockStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.Stmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.NodeCollector;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ParentCollector;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ReplaceNodeVisitor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Streams;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Wither;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Skeleton {
    @Getter
    private final HoleFactory holeFactory;
    @Wither
    @Getter
    private final BlockStmt stmts;
    @JsonIgnore
    private final Map<Node<?>, Node<?>> parentMap;
    @JsonIgnore
    @Getter
    private final List<HoleExpr> holes;

    @JsonCreator
    public static Skeleton create(
        @JsonProperty("holeFactory") HoleFactory holeFactory,
        @JsonProperty("stmts") BlockStmt stmts
    ) {
        var parentMap = ParentCollector.INSTANCE.collect(stmts);

        var holes = NodeCollector.INSTANCE.collect(stmts, HoleExpr.class);

        return new Skeleton(holeFactory, stmts, parentMap, holes);
    }

    public Skeleton fillHole(HoleExpr hole, Expr expr) {
        var visitor = new ReplaceNodeVisitor(hole, expr);
        var newStmts = stmts.accept(visitor, null);
        return Skeleton.create(holeFactory, newStmts);
    }

    public Skeleton replaceStmtInBlock(BlockStmt block, Stmt<?> oldStmt, Stmt<?>... newStmts) {
        var front = block.getStatements().stream().takeWhile(x -> x == oldStmt);
        var back = block.getStatements().stream().dropWhile(x -> x == oldStmt).skip(1);
        var newBlockStmts = Streams.concat(front, Arrays.stream(newStmts), back).collect(Collectors.toList());
        var newBlock = block.withStatements(newBlockStmts);
        return this.withStmts(newBlock);
    }

    public Node<?> parentOf(Node<?> node) {
        return parentMap.get(node);
    }

    public Stmt<?> parentStmtOf(Node<?> node) {
        var parent = parentOf(node);
        if (parent instanceof Stmt<?>) {
            return (Stmt<?>) parent;
        }
        return parentStmtOf(parent);
    }

}
