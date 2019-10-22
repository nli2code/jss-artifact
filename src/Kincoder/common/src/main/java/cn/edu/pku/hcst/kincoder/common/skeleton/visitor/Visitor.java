package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.*;

public interface Visitor<A, R> {
    R visit(Node<?> node, A arg);

    R visit(Arg node, A arg);

    R visit(BlockStmt node, A arg);

    R visit(ExprStmt node, A arg);

    R visit(IfStmt node, A arg);

    R visit(ForStmt node, A arg);

    R visit(ForEachStmt node, A arg);

    R visit(WhileStmt node, A arg);

    R visit(ReturnStmt node, A arg);

    R visit(HoleExpr node, A arg);

    R visit(AssignExpr node, A arg);

    R visit(ArrayCreationExpr node, A arg);

    R visit(BinaryExpr node, A arg);

    R visit(EnumConstantExpr node, A arg);

    R visit(MethodCallExpr node, A arg);

    R visit(StaticMethodCallExpr node, A arg);

    R visit(ObjectCreationExpr node, A arg);

    R visit(UnaryExpr node, A arg);

    R visit(TypeNameExpr node, A arg);

    R visit(SimpleNameExpr node, A arg);

    R visit(StaticFieldAccessExpr node, A arg);

    R visit(FieldAccessExpr node, A arg);

    R visit(VarDeclExpr node, A arg);

    R visit(BooleanLiteral node, A arg);

    R visit(ByteLiteral node, A arg);

    R visit(ShortLiteral node, A arg);

    R visit(IntLiteral node, A arg);

    R visit(LongLiteral node, A arg);

    R visit(FloatLiteral node, A arg);

    R visit(DoubleLiteral node, A arg);

    R visit(CharLiteral node, A arg);

    R visit(StringLiteral node, A arg);

    R visit(NullLiteral node, A arg);
}
