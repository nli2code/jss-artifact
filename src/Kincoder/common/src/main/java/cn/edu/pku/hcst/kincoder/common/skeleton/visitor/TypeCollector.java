package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;

import java.util.HashSet;
import java.util.Set;

public enum TypeCollector implements Visitor<Set<Type>, Void> {
    INSTANCE;

    private final GenericVisitor visitor = new GenericVisitor();

    public Set<Type> collect(Node<?> root) {
        final Set<Type> types = new HashSet<>();
        root.accept(this, types);
        return types;
    }

    @Override
    public Void visit(Node<?> node, Set<Type> arg) {
        return null;
    }

    @Override
    public Void visit(Arg node, Set<Type> arg) {
        arg.add(node.getType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(BlockStmt node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(ExprStmt node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(IfStmt node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(ForStmt node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(ForEachStmt node, Set<Type> arg) {
        arg.add(node.getType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(WhileStmt node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(ReturnStmt node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(HoleExpr node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(AssignExpr node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(ArrayCreationExpr node, Set<Type> arg) {
        arg.add(node.getComponentType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(BinaryExpr node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(EnumConstantExpr node, Set<Type> arg) {
        arg.add(node.getEnumType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(MethodCallExpr node, Set<Type> arg) {
        arg.add(node.getReceiver().getLeft());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(StaticMethodCallExpr node, Set<Type> arg) {
        arg.add(node.getDeclaredType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(ObjectCreationExpr node, Set<Type> arg) {
        arg.add(node.getType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(UnaryExpr node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(TypeNameExpr node, Set<Type> arg) {
        arg.add(node.getType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(SimpleNameExpr node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(StaticFieldAccessExpr node, Set<Type> arg) {
        arg.add(node.getReceiverType());
        arg.add(node.getTargetType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(FieldAccessExpr node, Set<Type> arg) {
        arg.add(node.getReceiverType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(VarDeclExpr node, Set<Type> arg) {
        arg.add(node.getType());
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(BooleanLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(ByteLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(ShortLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(IntLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(LongLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(FloatLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(DoubleLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(CharLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(StringLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }

    @Override
    public Void visit(NullLiteral node, Set<Type> arg) {
        node.accept(visitor, null).forEach(n -> n.accept(this, arg));
        return null;
    }
}
