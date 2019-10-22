package cn.edu.pku.hcst.kincoder.common.skeleton.model;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use= Id.CLASS, property="class")
public interface Node<N extends Node<?>> {
    <A, R> R accept(Visitor<A, R> visitor, A arg);

    <A> N accept(HomoVisitor<A> visitor, A arg);
}
