package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use= Id.CLASS, property="class")
public interface NameExpr<N extends NameExpr<?>> extends NameOrHole<N> {
    <A> N accept(HomoVisitor<A> visitor, A arg);
}
