package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.common.skeleton.HoleFactory;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.Stmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.GenericVisitorWithDefaults;
import lombok.Value;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.*;
import static cn.edu.pku.hcst.kincoder.common.utils.CollectionUtil.concat;
import static cn.edu.pku.hcst.kincoder.common.utils.CollectionUtil.cons;

public class DFG2PatternStatementVisitor extends GenericVisitorWithDefaults<Pair<List<Stmt<?>>, Map<String, NameExpr<?>>>, Map<String, NameExpr<?>>> {
    private final CodeUtil codeUtil;
    private final Set<Node> nodes;
    private final HoleFactory holeFactory;
    private final DFG2PatternExpressionVisitor visitor;

    public DFG2PatternStatementVisitor(CodeUtil codeUtil, Set<Node> nodes, HoleFactory holeFactory) {
        this.codeUtil = codeUtil;
        this.nodes = nodes;
        this.holeFactory = holeFactory;
        visitor = new DFG2PatternExpressionVisitor(codeUtil, nodes, holeFactory);
    }

    @Value
    static class GenExprsResult {
        private final List<Expr<?>> e;
        private final Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> ctx;
        private final List<Stmt<?>> added;
    }

    private GenExprsResult processExprs(List<Expression> exprs, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> ctx) {
        List<Expr<?>> result = new ArrayList<>();
        List<Stmt<?>> added = new ArrayList<>();

        for (var expr : exprs) {
            var r = expr.accept(visitor, ctx);
            ctx = r.getCtx();
            added.addAll(r.getAdded());
            result.add(r.getE());
        }

        return new GenExprsResult(result, ctx, added);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> defaultAction(Node n, Map<String, NameExpr<?>> arg) {
        throw new NotImplementedException(String.format("%s not implemented", n.getClass()));
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> defaultAction(NodeList n, Map<String, NameExpr<?>> arg) {
        throw new NotImplementedException(String.format("%s not implemented", n.getClass()));
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(BlockStmt n, Map<String, NameExpr<?>> arg) {
        List<Stmt<?>> result = new ArrayList<>();
        for (Statement statement : n.getStatements()) {
            var r = statement.accept(this, arg);
            arg = r.getRight();
            result.addAll(r.getLeft());
        }
        return Pair.of(result, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(ContinueStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(DoStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(EmptyStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(ExpressionStmt n, Map<String, NameExpr<?>> arg) {
        var r = n.getExpression().accept(visitor, arg);
        return r.getE() instanceof HoleExpr
            ? Pair.of(r.getAdded(), r.getCtx())
            : Pair.of(cons(r.getAdded(), expr2stmt(r.getE())), r.getCtx());
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(ForEachStmt n, Map<String, NameExpr<?>> arg) {
        var iteResult = n.getIterable().accept(visitor, arg);
        var bodyResult = n.getBody().accept(this, iteResult.getCtx());
//        val GenExprResult(iterable, ctx, added) = generateCodeExpr(n.getIterable, nodes, names)
//        val (body, ctx2) = generateCodeStmt(n.getBody, nodes, ctx)
        return nodes.contains(n)
            ? Pair.of(cons(iteResult.getAdded(), foreach(Type.fromString(n.getVariableDeclarator().getTypeAsString()), n.getVariableDeclarator().getNameAsString(), iteResult.getE(), block(bodyResult.getLeft()))), bodyResult.getRight())
            : Pair.of(concat(iteResult.getAdded(), bodyResult.getLeft()), bodyResult.getRight());
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(ForStmt n, Map<String, NameExpr<?>> arg) {
        if (n.getCompare().isPresent()) {
            var initResult = processExprs(n.getInitialization(), arg);
            var compareResult = n.getCompare().get().accept(visitor, initResult.getCtx());
            var updateResult = processExprs(n.getUpdate(), compareResult.getCtx());
            var bodyResult = n.getBody().accept(this, updateResult.getCtx());

            return nodes.contains(n)
                ? Pair.of(cons(concat(concat(initResult.getAdded(), compareResult.getAdded()), updateResult.getAdded()), forStmt(initResult.getE(), compareResult.getE(), updateResult.getE(), block(bodyResult.getLeft()))), bodyResult.getRight())
                : Pair.of(concat(concat(concat(initResult.getAdded(), compareResult.getAdded()), updateResult.getAdded()), bodyResult.getLeft()), bodyResult.getRight());

        } else {
            var initResult = processExprs(n.getInitialization(), arg);
            var updateResult = processExprs(n.getUpdate(), initResult.getCtx());
            var bodyResult = n.getBody().accept(this, updateResult.getCtx());

            return nodes.contains(n)
                ? Pair.of(cons(concat(initResult.getAdded(), updateResult.getAdded()), forStmt(initResult.getE(), updateResult.getE(), block(bodyResult.getLeft()))), bodyResult.getRight())
                : Pair.of(concat(concat(initResult.getAdded(), updateResult.getAdded()), bodyResult.getLeft()), bodyResult.getRight());
        }
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(IfStmt n, Map<String, NameExpr<?>> arg) {
        if (n.getElseStmt().isPresent()) {
            var condResult = n.getCondition().accept(visitor, arg);
            var thenResult = n.getThenStmt().accept(this, condResult.getCtx());
            var elseResult = n.getElseStmt().get().accept(this, condResult.getCtx());
            return nodes.contains(n)
                ? Pair.of(cons(condResult.getAdded(), when(condResult.getE(), block(thenResult.getLeft()), block(elseResult.getLeft()))), elseResult.getRight())
                : Pair.of(concat(concat(condResult.getAdded(), thenResult.getLeft()), elseResult.getLeft()), elseResult.getRight());
        } else {
            var condResult = n.getCondition().accept(visitor, arg);
            var thenResult = n.getThenStmt().accept(this, condResult.getCtx());
            return nodes.contains(n)
                ? Pair.of(cons(condResult.getAdded(), when(condResult.getE(), block(thenResult.getLeft()))), thenResult.getRight())
                : Pair.of(concat(condResult.getAdded(), thenResult.getLeft()), thenResult.getRight());
        }
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(ReturnStmt n, Map<String, NameExpr<?>> arg) {
        if (n.getExpression().isPresent()) {
            var exprResult = n.getExpression().get().accept(visitor, arg);
            return nodes.contains(n)
                ? Pair.of(cons(exprResult.getAdded(), ret(exprResult.getE())), arg)
                : Pair.of(exprResult.getAdded(), arg);
        } else {
            return nodes.contains(n)
                ? Pair.of(List.of(ret()), arg)
                : Pair.of(List.of(), arg);
        }
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(WhileStmt n, Map<String, NameExpr<?>> arg) {
        var condResult = n.getCondition().accept(visitor, arg);
        var bodyResult = n.getBody().accept(this, condResult.getCtx());
        return nodes.contains(n)
            ? Pair.of(cons(condResult.getAdded(), whileStmt(condResult.getE(), block(bodyResult.getLeft()))), bodyResult.getRight())
            : Pair.of(concat(condResult.getAdded(), bodyResult.getLeft()), bodyResult.getRight());
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(TryStmt n, Map<String, NameExpr<?>> arg) {
        return n.getTryBlock().accept(this, arg);
    }

}
