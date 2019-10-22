package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;

public interface LiteralExpr<T> extends Expr<LiteralExpr<T>> {
    T getValue();
}
