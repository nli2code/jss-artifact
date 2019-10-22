package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.*;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class GenericVisitor implements Visitor<Void, List<? extends Node>> {

    @Override
    public List<? extends Node> visit(Node<?> node, Void arg) {
        throw new RuntimeException();
    }

    @Override
    public List<? extends Node> visit(Arg node, Void arg) {
        return List.of(node.getValue());
    }

    @Override
    public List<? extends Node> visit(BlockStmt node, Void arg) {
        return node.getStatements();
    }

    @Override
    public List<? extends Node> visit(ExprStmt node, Void arg) {
        return List.of(node.getExpr());
    }

    @Override
    public List<? extends Node> visit(IfStmt node, Void arg) {
        var builder = ImmutableList.<Node>builder()
            .add(node.getCond())
            .add(node.getThenBody());
        if (node.getElseBody() != null) builder.add(node.getElseBody());
        return builder.build();
    }

    @Override
    public List<? extends Node> visit(ForStmt node, Void arg) {
        var builder = ImmutableList.<Node>builder();
        builder.addAll(node.getInits());
        if (node.getCond() != null) builder.add(node.getCond());
        builder.addAll(node.getUpdates()).add(node.getBody());
        return builder.build();
    }

    @Override
    public List<? extends Node> visit(ForEachStmt node, Void arg) {
        return List.of(node.getIterable(), node.getBody());
    }

    @Override
    public List<? extends Node> visit(WhileStmt node, Void arg) {
        return List.of(node.getCond(), node.getBody());
    }

    @Override
    public List<? extends Node> visit(ReturnStmt node, Void arg) {
        return node.getValue() != null ? List.of(node.getValue()) : List.of();
    }

    @Override
    public List<? extends Node> visit(HoleExpr node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(AssignExpr node, Void arg) {
        return List.of(node.getTarget(), node.getSource());
    }

    @Override
    public List<? extends Node> visit(ArrayCreationExpr node, Void arg) {
        return node.getInits();
    }

    @Override
    public List<? extends Node> visit(BinaryExpr node, Void arg) {
        return List.of(node.getLeft(), node.getRight());
    }

    @Override
    public List<? extends Node> visit(EnumConstantExpr node, Void arg) {
        return List.of(node.getName());
    }

    @Override
    public List<? extends Node> visit(MethodCallExpr node, Void arg) {
        var builder = ImmutableList.<Node>builder()
            .add(node.getReceiver().getRight())
            .addAll(node.getArgs());
        return builder.build();
    }

    @Override
    public List<? extends Node> visit(StaticMethodCallExpr node, Void arg) {
        return node.getArgs();
    }

    @Override
    public List<? extends Node> visit(ObjectCreationExpr node, Void arg) {
        return node.getArgs();
    }

    @Override
    public List<? extends Node> visit(UnaryExpr node, Void arg) {
        return List.of(node.getExpr());
    }

    @Override
    public List<? extends Node> visit(TypeNameExpr node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(SimpleNameExpr node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(StaticFieldAccessExpr node, Void arg) {
        return List.of(node.getName());
    }

    @Override
    public List<? extends Node> visit(FieldAccessExpr node, Void arg) {
        return List.of(node.getReceiver(), node.getName());
    }

    @Override
    public List<? extends Node> visit(VarDeclExpr node, Void arg) {
        var builder = ImmutableList.<Node>builder()
            .add(node.getName());
        if (node.getInit() != null) builder.add(node.getInit());
        return builder.build();
    }

    @Override
    public List<? extends Node> visit(BooleanLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(ByteLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(ShortLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(IntLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(LongLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(FloatLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(DoubleLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(CharLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(StringLiteral node, Void arg) {
        return List.of();
    }

    @Override
    public List<? extends Node> visit(NullLiteral node, Void arg) {
        return List.of();
    }

}
