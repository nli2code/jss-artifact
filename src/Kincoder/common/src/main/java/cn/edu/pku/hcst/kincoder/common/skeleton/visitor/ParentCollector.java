package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;

import java.util.HashMap;
import java.util.Map;

public enum ParentCollector {
    INSTANCE;

    private final GenericVisitor visitor = new GenericVisitor();

    public Map<Node<?>, Node<?>> collect(Node<?> root) {
        final Map<Node<?>, Node<?>> parentMap = new HashMap<>();
        collect(null, root, parentMap);
        return parentMap;
    }

    private void collect(Node<?> parent, Node<?> current, Map<Node<?>, Node<?>> map) {
        map.put(current, parent);
        var children = current.accept(visitor, null);
        children.forEach(c -> collect(current, c, map));
    }
}
