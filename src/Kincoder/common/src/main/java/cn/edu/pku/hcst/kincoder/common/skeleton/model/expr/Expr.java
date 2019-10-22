package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use= Id.CLASS, property="class")
public interface Expr<E extends Expr<?>> extends Node<E> {
    <A> E accept(HomoVisitor<A> visitor, A arg);
}
