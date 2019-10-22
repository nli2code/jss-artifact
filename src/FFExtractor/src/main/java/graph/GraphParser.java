package graph;

import de.parsemis.graph.Edge;
import de.parsemis.graph.Graph;
import de.parsemis.graph.Node;
import entities.graph.*;
import entities.graph.dictionary.*;
import utils.GraphUtils;

import java.util.*;
import java.util.stream.Collectors;

public class GraphParser {

  public static GraphInfo parseGraphAndValidate(Graph<NodeInfo, Integer> graph) {
    if (graph == null)
      return null;
    GraphInfo graphInfo = new GraphInfo();
    boolean isVPRoot = false;
    boolean containsVerbLeaf = false;
    boolean containsNounLeaf = false;

    HashMap<Integer, NodeInfo> nodeIndex = new HashMap<>();

    Iterator<Node<NodeInfo, Integer>> nodeIterator = graph.nodeIterator();
    while (nodeIterator.hasNext()) {
      Node<NodeInfo, Integer> curNode = nodeIterator.next();
      NodeInfo nodeInfo = curNode.getLabel();
      if (nodeInfo != null) {
        graphInfo.addNode(nodeInfo);
        nodeIndex.put(curNode.getIndex(), nodeInfo);

        if (!isVPRoot && curNode.getInDegree() == 0) {
          graphInfo.setRoot(nodeInfo);
          if (nodeInfo.getLabel() == NodeLabel.VP)
            isVPRoot = true;
        }
        if (nodeInfo.getLabel() == NodeLabel.verb)
          containsVerbLeaf = true;
        if (nodeInfo.getLabel() == NodeLabel.noun)
          containsNounLeaf = true;
      }
    }

    if (!isVPRoot || !containsNounLeaf || !containsVerbLeaf) {
      return null;
    }

    Iterator<Edge<NodeInfo, Integer>> edgeIterator = graph.edgeIterator();
    while (edgeIterator.hasNext()) {
      Edge<NodeInfo, Integer> curEdge = edgeIterator.next();
      int nodeAIndex = curEdge.getNodeA().getIndex();
      int nodeBIndex = curEdge.getNodeB().getIndex();
      int direction = curEdge.getDirection();

      if (direction == Edge.INCOMING)
        graphInfo.addEdge(nodeIndex.get(nodeBIndex), nodeIndex.get(nodeAIndex),
                EdgeInfo.OUTGOING);
      else
        graphInfo.addEdge(nodeIndex.get(nodeAIndex), nodeIndex.get(nodeBIndex), direction);
    }

    return graphInfo;
  }

  public static VerbalPhraseStructureInfo parseGraphToPhraseStructure(GraphInfo graph) {
    if (graph != null) {
      TreeInfo tree = parseGraphToTree(graph);
      if (tree != null)
        return parseTreeToVPStructure(tree);
    }
    return null;
  }

  public static VerbalPhraseStructureInfo parseTreeToVPStructure(TreeInfo tree) {
    VerbalPhraseStructureInfo vpStructure = new VerbalPhraseStructureInfo();
    tree.getChildren().forEach(x -> {
      NodeLabel label = x.getLabel();
      switch (label) {
        case verb:
          vpStructure.setVerb(new VerbInfo(x.getValue()));
          break;
        case conj:
          vpStructure.setParticle(new ConjunctionInfo(x.getValue()));
          break;
        case NP:
          vpStructure.setSubNP(parseTreeToNPStructure(x));
          break;
        case PP:
          vpStructure.addSubPP(parseTreeToPPStructure(x));
          break;
        default:
          break;
      }
    });
    return vpStructure;
  }

  public static NounPhraseStructureInfo parseTreeToNPStructure(TreeInfo tree) {
    NounPhraseStructureInfo npStructure = new NounPhraseStructureInfo();
    List<WordInfo> wordChain = new ArrayList<>();
    wordChain.addAll(tree.getChildren().stream().filter(x -> x.getLabel() == NodeLabel.other)
            .map(x -> new WordInfo(x.getValue())).collect(Collectors.toList()));
    wordChain.addAll(tree.getChildren().stream().filter(x -> x.getLabel() == NodeLabel.conj)
            .map(x -> new ConjunctionInfo(x.getValue())).collect(Collectors.toList()));
    wordChain.addAll(tree.getChildren().stream().filter(x -> x.getLabel() == NodeLabel.adj)
            .map(x -> new AdjectiveInfo(x.getValue())).collect(Collectors.toList()));
    wordChain.addAll(tree.getChildren().stream().filter(x -> x.getLabel() == NodeLabel.noun)
            .map(x -> new NounInfo(x.getValue())).collect(Collectors.toList()));
    npStructure.setWordChain(wordChain);
    TreeInfo ppTree = tree.getChildren().stream().filter(x -> x.getLabel() == NodeLabel.PP)
            .findFirst().orElse(null);
    if (ppTree != null)
      npStructure.setSubPP(parseTreeToPPStructure(ppTree));
    return npStructure;
  }

  public static PrepPhraseStructureInfo parseTreeToPPStructure(TreeInfo tree) {
    PrepPhraseStructureInfo ppStructure = new PrepPhraseStructureInfo();
    tree.getChildren().stream().filter(x -> x.getLabel() == NodeLabel.conj).findFirst()
            .ifPresent(x -> ppStructure.setConjunction(new ConjunctionInfo(x.getValue())));
    tree.getChildren().stream().filter(x -> x.getLabel() == NodeLabel.NP).findFirst()
            .ifPresent(x -> ppStructure.setSubNP(parseTreeToNPStructure(x)));
    tree.getChildren().stream().filter(x -> x.getLabel() == NodeLabel.PP).findFirst()
            .ifPresent(x -> ppStructure.setSubPP(parseTreeToPPStructure(x)));
    return ppStructure;
  }

  public static TreeInfo parseGraphToTree(GraphInfo graph) {
    List<NodeInfo> roots = graph.getRoots();
    if (roots.size() != 1)
      return null;
    NodeInfo root = roots.get(0);
    TreeInfo tree = generateTreeFromNodeInGraph(root, graph);
    return tree;
  }

  public static TreeInfo generateTreeFromNodeInGraph(NodeInfo node, GraphInfo graph) {
    return generateTreeFromNodeInGraph(node, graph, new ArrayList<>());
  }

  public static TreeInfo generateTreeFromNodeInGraph(NodeInfo node, GraphInfo graph,
                                                     List<NodeInfo> ancestors) {
    if (!graph.contains(node))
      return null;
    if (GraphUtils.containsNodeObj(ancestors, node))
      return null;
    ancestors.add(node);

    TreeInfo tree = new TreeInfo(node);
    List<NodeInfo> successors = graph.getSuccessors(node);
    successors.sort(new Comparator<NodeInfo>() {
      @Override
      public int compare(NodeInfo n1, NodeInfo n2) {
        return n1.getNumber() - n2.getNumber();
      }
    });
    for (NodeInfo successor : successors) {
      TreeInfo child = generateTreeFromNodeInGraph(successor, graph, ancestors);
      if (child == null)
        return null;
      tree.addChild(child);
    }
    return tree;
  }

}

