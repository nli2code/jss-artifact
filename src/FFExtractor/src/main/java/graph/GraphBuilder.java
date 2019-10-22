package graph;

import de.parsemis.graph.ListGraph;
import de.parsemis.graph.Node;
import entities.graph.*;
import entities.graph.dictionary.*;
import utils.GraphUtils;

import java.util.HashMap;

public class GraphBuilder {
  public static final int		EDGETYPE	= 0;

  public static ListGraph<NodeInfo, Integer> buildGraph(VerbalPhraseStructureInfo vps) {
    GraphInfo graphInfo = buildGraphInfoFromStructure(vps);
    return convertToParsemisGraph(graphInfo);
  }

  public static GraphInfo addNodeIndexNumber(GraphInfo graph) {
    if (graph == null || graph.getNodeList().size() <= 0)
      return null;
    for (int i = 0; i < graph.getNodeList().size(); i++) {
      graph.getNodeList().get(i).setNumber(i);
    }
    return graph;
  }

  public static ListGraph<NodeInfo, Integer> convertToParsemisGraph(GraphInfo graphInfo) {
    if (graphInfo == null || graphInfo.getNodeList().size() <= 0)
      return null;

    ListGraph<NodeInfo, Integer> graph = new ListGraph<>(graphInfo.getName());

    HashMap<String, Node<NodeInfo, Integer>> nodeMap = new HashMap<>();
    for (int i = 0; i < graphInfo.getNodeList().size(); i++) {
      NodeInfo node = graphInfo.getNodeList().get(i);
      if (node.getUuid() == null)
        node.setUuid();
      Node<NodeInfo, Integer> addedNode = graph.addNode(node);
      nodeMap.put(node.getUuid(), addedNode);
    }

    for (EdgeInfo edge : graphInfo.getEdgeList()) {
      Node<NodeInfo, Integer> nodeA = nodeMap.get(edge.getNodeA().getUuid());
      Node<NodeInfo, Integer> nodeB = nodeMap.get(edge.getNodeB().getUuid());
      if (graph.addEdge(nodeA, nodeB, EDGETYPE, edge.getDirection()) == null) {
        System.err.println("==============================");
        System.err.println(graphInfo);
        System.err.println(GraphUtils.toString(graph));
        System.err.println(edge);
      }
    }
    return graph;
  }

  public static GraphInfo buildGraphInfoFromStructure(VerbalPhraseStructureInfo vps) {
    GraphInfo graph = new GraphInfo(vps.toString());

    NodeInfo root = new NodeInfo(NodeLabel.VP, null);
    int index = graph.addNode(root);
    root.setNumber(index);
    graph.setRoot(root);

    NodeInfo verb = new NodeInfo(NodeLabel.verb, vps.getVerb().toString());
    index = graph.addNode(verb);
    verb.setNumber(index);
    graph.addEdge(root, verb, EdgeInfo.OUTGOING);

    if (vps.getParticle() != null) {
      NodeInfo particle = new NodeInfo(NodeLabel.conj, vps.getParticle().toString());
      index = graph.addNode(particle);
      particle.setNumber(index);
      graph.addEdge(root, particle, EdgeInfo.OUTGOING);
    }

    if (vps.getSubNP() != null) {
      GraphInfo subNPGraph = buildGraphInfoFromStructure(vps.getSubNP());
      graph.addEdge(root, subNPGraph.getRoot(), EdgeInfo.OUTGOING);
      graph.merge(subNPGraph);
    }

    for (int i = 0; i < vps.getSubPPList().size(); i++) {
      GraphInfo subPPGraph = buildGraphInfoFromStructure(vps.getSubPPList().get(i));
      graph.addEdge(root, subPPGraph.getRoot(), EdgeInfo.OUTGOING);
      graph.merge(subPPGraph);
    }

    return graph;
  }

  public static GraphInfo buildGraphInfoFromStructure(NounPhraseStructureInfo nps) {
    GraphInfo graph = new GraphInfo(nps.toString());

    NodeInfo root = new NodeInfo(NodeLabel.NP, null);
    int index = graph.addNode(root);
    root.setNumber(index);
    graph.setRoot(root);

    for (int i = 0; i < nps.getWordChain().size(); i++) {
      WordInfo word = nps.getWordChain().get(i);
      NodeInfo wordNode;
      if (word instanceof VerbInfo)
        wordNode = new NodeInfo(NodeLabel.verb, word.toString());
      else if (word instanceof NounInfo)
        wordNode = new NodeInfo(NodeLabel.noun, word.toString());
      else if (word instanceof AdjectiveInfo)
        wordNode = new NodeInfo(NodeLabel.adj, word.toString());
      else if (word instanceof ConjunctionInfo)
        wordNode = new NodeInfo(NodeLabel.conj, word.toString());
      else
        wordNode = new NodeInfo(NodeLabel.other, word.toString());
      index = graph.addNode(wordNode);
      wordNode.setNumber(index);
      graph.addEdge(root, wordNode, EdgeInfo.OUTGOING);
    }
    if (nps.getSubPP() != null) {
      GraphInfo subPPGraph = buildGraphInfoFromStructure(nps.getSubPP());
      graph.addEdge(root, subPPGraph.getRoot(), EdgeInfo.OUTGOING);
      graph.merge(subPPGraph);
    }

    return graph;
  }

  public static GraphInfo buildGraphInfoFromStructure(PrepPhraseStructureInfo pps) {
    GraphInfo graph = new GraphInfo(pps.toString());

    NodeInfo root = new NodeInfo(NodeLabel.PP, null);
    int index = graph.addNode(root);
    root.setNumber(index);
    graph.setRoot(root);

    if (pps.getConjunction() != null) {
      NodeInfo conj = new NodeInfo(NodeLabel.conj, pps.getConjunction().toString());
      int idx = graph.addNode(conj);
      conj.setNumber(idx);
      graph.addEdge(root, conj, EdgeInfo.OUTGOING);
    }

    if (pps.getSubNP() != null) {
      GraphInfo subNPGraph = buildGraphInfoFromStructure(pps.getSubNP());
      graph.addEdge(root, subNPGraph.getRoot(), EdgeInfo.OUTGOING);
      graph.merge(subNPGraph);
    }

    if (pps.getSubPP() != null) {
      GraphInfo subPPGraph = buildGraphInfoFromStructure(pps.getSubPP());
      graph.addEdge(root, subPPGraph.getRoot(), EdgeInfo.OUTGOING);
      graph.merge(subPPGraph);
    }

    return graph;
  }

}

