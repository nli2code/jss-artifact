package cn.edu.pku.hcst.kincoder.pattern.utils;

import de.parsemis.graph.Edge;
import de.parsemis.graph.Graph;

import java.io.PrintStream;

public class GraphUtil {
    public static void print(Graph<?, ?> g) {
        print(g, System.out);
    }

    public static void print(Graph<?, ?> g, PrintStream ps) {
        var nodeIte = g.nodeIterator();
        while (nodeIte.hasNext()) {
            var node = nodeIte.next();
            ps.println(String.format("Node %s: %s", node.getIndex(), node.getLabel()));
        }
        var ite = g.edgeIterator();
        while (ite.hasNext()) {
            var edge = ite.next();
            if (edge.getDirection() == Edge.INCOMING) ps.println(String.format("%s -> %s", edge.getNodeB().getIndex(), edge.getNodeA().getIndex()));
            else ps.println(String.format("%s -> %s", edge.getNodeA().getIndex(), edge.getNodeB().getIndex()));
        }
    }
}
