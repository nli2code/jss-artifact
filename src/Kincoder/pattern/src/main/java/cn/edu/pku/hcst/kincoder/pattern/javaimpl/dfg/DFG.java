package cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg;

import cn.edu.pku.hcst.kincoder.common.utils.FList;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import de.parsemis.graph.Graph;
import de.parsemis.graph.ListGraph;
import de.parsemis.graph.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DFG extends ListGraph<DFGNode, DFGEdge> {
    @Getter
    private final CFG cfg;

    @Setter
    private Map<Node<DFGNode, DFGEdge>, Set<com.github.javaparser.ast.Node>> map;

    public DFG(CFG cfg) {
        this.cfg = cfg;
    }

    public Set<com.github.javaparser.ast.Node> recover(Set<Node<DFGNode, DFGEdge>> nodes) {
        return nodes.stream()
            .map(map::get)
            .filter(Objects::nonNull)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }


    public Pair<Boolean, Set<Node<DFGNode, DFGEdge>>> isSuperGraph(Graph<DFGNode, DFGEdge> graph) {
        var list = FList.fromIterator(graph.nodeIterator());

        if (list.isEmpty()) {
            return Pair.of(true, Set.of());
        }

        return isSuperGraph(HashBiMap.create(), list.getHead(), list.getTail());
    }

    public Pair<Boolean, Set<Node<DFGNode, DFGEdge>>> isSuperGraph(BiMap<Node<DFGNode, DFGEdge>, Node<DFGNode, DFGEdge>> nodeMap, Node<DFGNode, DFGEdge> current, FList<Node<DFGNode, DFGEdge>> remain){
        Pair<Boolean, Set<Node<DFGNode, DFGEdge>>> result = Pair.of(false, Set.of());
        var ite = nodeIterator();
        while (ite.hasNext() && !result.getLeft()) {
            var node = ite.next();
            if (nodeMap.containsKey(node)) continue;
            if (!node.getLabel().equals(current.getLabel())) continue;

            var outDiff = false;
            var outs = current.outgoingEdgeIterator();
            while (outs.hasNext() && !outDiff) {
                var outEdge = outs.next();
                var other = outEdge.getOtherNode(current);
                if (nodeMap.containsValue(other)) {
                    var mappedDDGBlock = nodeMap.inverse().get(other);
                    if (Streams.stream(node.outgoingEdgeIterator()).map(o -> o.getOtherNode(node)).noneMatch(o -> o.getLabel().equals(mappedDDGBlock.getLabel()))) outDiff = true;
                }
            }

            var inDiff = false;
            var ins = current.incommingEdgeIterator();
            while (ins.hasNext() && !inDiff) {
                var inEdge = ins.next();
                var other = inEdge.getOtherNode(current);
                if (nodeMap.containsValue(other)) {
                    var mappedDDGBlock = nodeMap.inverse().get(other);
                    if (Streams.stream(node.incommingEdgeIterator()).map(o -> o.getOtherNode(node)).noneMatch(o -> o.getLabel().equals(mappedDDGBlock.getLabel()))) inDiff = true;
                }
            }

            if (!inDiff && !outDiff) {
                nodeMap.put(node, current);
                if (remain.isEmpty()) {
                    result = Pair.of(true, nodeMap.keySet());
                } else {
                    var p = isSuperGraph(nodeMap, remain.getHead(), remain.getTail());
                    var newNodes = ImmutableSet.<Node<DFGNode, DFGEdge>>builder().addAll(p.getRight()).add(node).build();
                    if (p.getLeft()) result = Pair.of(true, newNodes);
                    nodeMap.remove(node);
                }
            }
        }
        return result;
    }
}
