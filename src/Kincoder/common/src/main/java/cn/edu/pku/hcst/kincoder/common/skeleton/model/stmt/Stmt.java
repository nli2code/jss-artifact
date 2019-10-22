package cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use= Id.CLASS, property="class")
public interface Stmt<S extends Stmt<?>> extends Node<S> {
    <A> S accept(HomoVisitor<A> visitor, A arg);
}
