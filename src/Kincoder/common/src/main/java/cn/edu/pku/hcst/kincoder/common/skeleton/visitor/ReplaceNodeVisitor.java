package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.*;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
public class ReplaceNodeVisitor implements HomoVisitor<Void> {
    private final Node oldNode;
    private final Node newNode;

    private <N extends Node<? extends N>, S extends Node<? extends S>> Optional<N> check(N checkNode, Function<N, S> getter, BiFunction<N, S, N> setter) {
        if (getter.apply(checkNode) == oldNode) {
            @SuppressWarnings("unchecked") var n = (S) newNode;
            return Optional.of(setter.apply(checkNode, n));
        }
        var node = getter.apply(checkNode).accept(this, null);
        if (node != null) return Optional.of(setter.apply(checkNode, node));
        return Optional.empty();
    }

    private <N extends Node<? extends N>, S extends Node<? extends S>> Optional<N> checkNullable(N checkNode, Function<N, S> getter, BiFunction<N, S, N> setter) {
        if (getter.apply(checkNode) != null) {
            return check(checkNode, getter, setter);
        }
        return Optional.empty();
    }

    private <N extends Node<? extends N>, S extends Node<? extends S>> Optional<N> checkList(N checkNode, Function<N, List<S>> getter, BiFunction<N, List<S>, N> setter) {
        var nodes = new ArrayList<S>();
        var updated = false;
        for (S a : getter.apply(checkNode)) {
            if (!updated) {
                if (a == oldNode) {
                    @SuppressWarnings("unchecked") var n = (S) newNode;
                    nodes.add(n);
                    continue;
                }
                var na = a.accept(this, null);
                if (na != null) updated = true;
                nodes.add(na);
            } else {
                nodes.add(a);
            }
        }
        if (updated) return Optional.of(setter.apply(checkNode, nodes));
        return Optional.empty();
    }

    @Override
    public Node<?> visit(Node<?> node, Void arg) {
        throw new RuntimeException();
    }

    @Override
    public Expr<?> visit(Expr<?> node, Void arg) {
        throw new RuntimeException();
    }

    @Override
    public Arg visit(Arg node, Void arg) {
        return check(node, Arg::getValue, Arg::withValue).orElse(null);
    }

    @Override
    public BlockStmt visit(BlockStmt node, Void arg) {
        return checkList(node, BlockStmt::getStatements, BlockStmt::withStatements).orElse(null);
    }

    @Override
    public ExprStmt visit(ExprStmt node, Void arg) {
        return check(node, ExprStmt::getExpr, ExprStmt::withExpr).orElse(null);
    }

    @Override
    public IfStmt visit(IfStmt node, Void arg) {
        return check(node, IfStmt::getCond, IfStmt::withCond).orElse(
            check(node, IfStmt::getThenBody, IfStmt::withThenBody).orElse(
                checkNullable(node, IfStmt::getElseBody, IfStmt::withElseBody).orElse(null)
            )
        );
    }

    @Override
    public ForStmt visit(ForStmt node, Void arg) {
        return checkList(node, ForStmt::getInits, ForStmt::withInits).orElse(
            check(node, ForStmt::getCond, ForStmt::withCond).orElse(
                checkList(node, ForStmt::getUpdates, ForStmt::withUpdates).orElse(
                    check(node, ForStmt::getBody, ForStmt::withBody).orElse(null)
                )
            )
        );
    }

    @Override
    public ForEachStmt visit(ForEachStmt node, Void arg) {
        return check(node, ForEachStmt::getIterable, ForEachStmt::withIterable).orElse(
            check(node, ForEachStmt::getBody, ForEachStmt::withBody).orElse(null)
        );
    }

    @Override
    public WhileStmt visit(WhileStmt node, Void arg) {
        return check(node, WhileStmt::getCond, WhileStmt::withCond).orElse(
            check(node, WhileStmt::getBody, WhileStmt::withBody).orElse(null)
        );
    }

    @Override
    public ReturnStmt visit(ReturnStmt node, Void arg) {
        return checkNullable(node, ReturnStmt::getValue, ReturnStmt::withValue).orElse(null);
    }

    @Override
    public HoleExpr visit(HoleExpr node, Void arg) {
        return null;
    }

    @Override
    public AssignExpr visit(AssignExpr node, Void arg) {
        if (node == oldNode) return (AssignExpr) newNode;
        return check(node, AssignExpr::getTarget, AssignExpr::withTarget).orElse(
            check(node, AssignExpr::getSource, AssignExpr::withSource).orElse(null)
        );
    }

    @Override
    public ArrayCreationExpr visit(ArrayCreationExpr node, Void arg) {
        return checkList(node, ArrayCreationExpr::getInits, ArrayCreationExpr::withInits).orElse(null);
    }

    @Override
    public BinaryExpr visit(BinaryExpr node, Void arg) {
        return check(node, BinaryExpr::getLeft, BinaryExpr::withLeft).orElse(
            check(node, BinaryExpr::getRight, BinaryExpr::withRight).orElse(null)
        );
    }

    @Override
    public EnumConstantExpr visit(EnumConstantExpr node, Void arg) {
        return this.<EnumConstantExpr, NameOrHole<?>>check(node, EnumConstantExpr::getName, EnumConstantExpr::withName).orElse(null);
    }

    @Override
    public MethodCallExpr visit(MethodCallExpr node, Void arg) {
        return check(node, n -> n.getReceiver().getRight(), (MethodCallExpr n, Expr<?> s) -> n.withReceiver(Pair.of(n.getReceiver().getLeft(), s))).orElse(
            checkList(node, MethodCallExpr::getArgs, MethodCallExpr::withArgs).orElse(null)
        );
    }

    @Override
    public StaticMethodCallExpr visit(StaticMethodCallExpr node, Void arg) {
        return checkList(node, StaticMethodCallExpr::getArgs, StaticMethodCallExpr::withArgs).orElse(null);
    }

    @Override
    public ObjectCreationExpr visit(ObjectCreationExpr node, Void arg) {
        return checkList(node, ObjectCreationExpr::getArgs, ObjectCreationExpr::withArgs).orElse(null);
    }

    @Override
    public UnaryExpr visit(UnaryExpr node, Void arg) {
        return check(node, UnaryExpr::getExpr, UnaryExpr::withExpr).orElse(null);
    }

    @Override
    public TypeNameExpr visit(TypeNameExpr node, Void arg) {
        return null;
    }

    @Override
    public SimpleNameExpr visit(SimpleNameExpr node, Void arg) {
        return null;
    }

    @Override
    public StaticFieldAccessExpr visit(StaticFieldAccessExpr node, Void arg) {
        return this.<StaticFieldAccessExpr, NameOrHole<?>>check(node, StaticFieldAccessExpr::getName, StaticFieldAccessExpr::withName).orElse(null);
    }

    @Override
    public FieldAccessExpr visit(FieldAccessExpr node, Void arg) {
        return this.<FieldAccessExpr, NameOrHole<?>>check(node, FieldAccessExpr::getName, FieldAccessExpr::withName).orElse(
            check(node, FieldAccessExpr::getReceiver, FieldAccessExpr::withReceiver).orElse(null)
        );
    }

    @Override
    public VarDeclExpr visit(VarDeclExpr node, Void arg) {
        return checkNullable(node, VarDeclExpr::getInit, VarDeclExpr::withInit).orElse(
            this.<VarDeclExpr, NameExpr<?>>check(node, VarDeclExpr::getName, VarDeclExpr::withName).orElse(null)
        );
    }

    @Override
    public BooleanLiteral visit(BooleanLiteral node, Void arg) {
        return null;
    }

    @Override
    public ByteLiteral visit(ByteLiteral node, Void arg) {
        return null;
    }

    @Override
    public ShortLiteral visit(ShortLiteral node, Void arg) {
        return null;
    }

    @Override
    public IntLiteral visit(IntLiteral node, Void arg) {
        return null;
    }

    @Override
    public LongLiteral visit(LongLiteral node, Void arg) {
        return null;
    }

    @Override
    public FloatLiteral visit(FloatLiteral node, Void arg) {
        return null;
    }

    @Override
    public DoubleLiteral visit(DoubleLiteral node, Void arg) {
        return null;
    }

    @Override
    public CharLiteral visit(CharLiteral node, Void arg) {
        return null;
    }

    @Override
    public StringLiteral visit(StringLiteral node, Void arg) {
        return null;
    }

    @Override
    public NullLiteral visit(NullLiteral node, Void arg) {
        return null;
    }
}
