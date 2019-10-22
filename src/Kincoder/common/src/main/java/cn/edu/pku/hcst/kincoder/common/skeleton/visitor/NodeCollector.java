package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;

import java.util.ArrayList;
import java.util.List;

public enum NodeCollector {
    INSTANCE;

    private final GenericVisitor visitor = new GenericVisitor();

    public <T extends Node<T>> List<T> collect(Node<?> root, Class<T> clazz) {
        final List<T> nodes = new ArrayList<>();
        collect(root, clazz, nodes);
        return nodes;
    }

    @SuppressWarnings("unchecked")
    private <T extends Node<T>> void collect(Node<?> current, Class<T> clazz, List<T> nodes) {
        if (current.getClass() == clazz) nodes.add((T) current);
        var children = current.accept(visitor, null);
        children.forEach(c -> collect(c, clazz, nodes));
    }
}
