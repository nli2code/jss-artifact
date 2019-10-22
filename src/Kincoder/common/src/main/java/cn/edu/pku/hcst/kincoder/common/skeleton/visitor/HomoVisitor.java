package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.*;

public interface HomoVisitor<A> {
    Node<?> visit(Node<?> node, A arg);

    Expr<?> visit(Expr<?> node, A arg);

    Arg visit(Arg node, A arg);

    BlockStmt visit(BlockStmt node, A arg);

    ExprStmt visit(ExprStmt node, A arg);

    IfStmt visit(IfStmt node, A arg);

    ForStmt visit(ForStmt node, A arg);

    ForEachStmt visit(ForEachStmt node, A arg);

    WhileStmt visit(WhileStmt node, A arg);

    ReturnStmt visit(ReturnStmt node, A arg);

    HoleExpr visit(HoleExpr node, A arg);

    AssignExpr visit(AssignExpr node, A arg);

    ArrayCreationExpr visit(ArrayCreationExpr node, A arg);

    BinaryExpr visit(BinaryExpr node, A arg);

    EnumConstantExpr visit(EnumConstantExpr node, A arg);

    MethodCallExpr visit(MethodCallExpr node, A arg);

    StaticMethodCallExpr visit(StaticMethodCallExpr node, A arg);

    ObjectCreationExpr visit(ObjectCreationExpr node, A arg);

    UnaryExpr visit(UnaryExpr node, A arg);

    TypeNameExpr visit(TypeNameExpr node, A arg);

    SimpleNameExpr visit(SimpleNameExpr node, A arg);

    StaticFieldAccessExpr visit(StaticFieldAccessExpr node, A arg);

    FieldAccessExpr visit(FieldAccessExpr node, A arg);

    VarDeclExpr visit(VarDeclExpr node, A arg);

    BooleanLiteral visit(BooleanLiteral node, A arg);

    ByteLiteral visit(ByteLiteral node, A arg);

    ShortLiteral visit(ShortLiteral node, A arg);

    IntLiteral visit(IntLiteral node, A arg);

    LongLiteral visit(LongLiteral node, A arg);

    FloatLiteral visit(FloatLiteral node, A arg);

    DoubleLiteral visit(DoubleLiteral node, A arg);

    CharLiteral visit(CharLiteral node, A arg);

    StringLiteral visit(StringLiteral node, A arg);

    NullLiteral visit(NullLiteral node, A arg);
}
