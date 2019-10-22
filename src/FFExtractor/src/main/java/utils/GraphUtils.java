package utils;


import de.parsemis.graph.Edge;
import de.parsemis.graph.Graph;
import de.parsemis.graph.Node;
import entities.graph.GraphInfo;
import entities.graph.NodeInfo;
import entities.graph.NodeLabel;
import filters.Rules;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GraphUtils {

  public static boolean containsNodeObj(List<NodeInfo> list, NodeInfo node) {
    for (NodeInfo nodeInfo : list) {
      if (nodeInfo.equalsObject(node))
        return true;
    }
    return false;
  }

  public static String toStringWithOrderNumber(Graph<NodeInfo, Integer> graph) {
    StringBuilder str = new StringBuilder("[graph: [node:");
    Iterator<Node<NodeInfo, Integer>> iteratorNode = graph.nodeIterator();
    while (iteratorNode.hasNext()) {
      Node<NodeInfo, Integer> curNode = iteratorNode.next();
      str.append(curNode.getLabel().toStringWithOrderNumber());
      if (iteratorNode.hasNext())
        str.append(", ");
    }
    str.append("][edge:");
    Iterator<Edge<NodeInfo, Integer>> iteratorEdge = graph.edgeIterator();
    while (iteratorEdge.hasNext()) {
      Edge<NodeInfo, Integer> curEdge = iteratorEdge.next();
      String direction;
      switch (curEdge.getDirection()) {
        case Edge.INCOMING:
          direction = "<-";
          break;
        case Edge.OUTGOING:
          direction = "->";
          break;
        case Edge.UNDIRECTED:
          direction = "--";
          break;
        default:
          direction = ", ";
          break;
      }
      str.append(
              "(" + curEdge.getNodeA().getLabel().toStringWithOrderNumber() + " " + direction
                      + " " + curEdge.getNodeB().getLabel().toStringWithOrderNumber() + ")");
      if (iteratorEdge.hasNext())
        str.append(", ");
    }
    str.append("]]");
    return str.toString();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static String toString(Graph graph) {
    StringBuilder str = new StringBuilder("[graph: [node:");
    Iterator<Node> iteratorNode = graph.nodeIterator();
    while (iteratorNode.hasNext()) {
      Node curNode = iteratorNode.next();
      str.append(curNode.getLabel());
      if (iteratorNode.hasNext())
        str.append(", ");
    }
    str.append("][edge:");
    Iterator<Edge> iteratorEdge = graph.edgeIterator();
    while (iteratorEdge.hasNext()) {
      Edge curEdge = iteratorEdge.next();
      String direction;
      switch (curEdge.getDirection()) {
        case Edge.INCOMING:
          direction = "<-";
          break;
        case Edge.OUTGOING:
          direction = "->";
          break;
        case Edge.UNDIRECTED:
          direction = "--";
          break;
        default:
          direction = ", ";
          break;
      }
      str.append("(" + curEdge.getNodeA().getLabel() + " " + direction + " "
              + curEdge.getNodeB().getLabel() + ")");
      if (iteratorEdge.hasNext())
        str.append(", ");
    }
    str.append("]]");
    return str.toString();
  }

  /**
   * @param text,
   *            "<label value>" "<label>"
   * @return
   */
  public static NodeInfo parseStringToNodeInfo(String text) {
    String[] splitted = text.split("\\<|\\>| ");
    // 长度为3：#<label value>，长度为2：#<label>
    if (splitted == null || splitted.length < 2)
      return null;

    NodeLabel label = NodeLabel.getLabel(splitted[1]);
    String value = splitted.length == 3 ? splitted[2] : null;

    NodeInfo node = new NodeInfo(label, value);

    // 修改过后的NodeString 以宽度优先搜索（先根顺序）的顺序编号开头，然后<>
    // 如果有这个数字就parse出来，没有就不管啦
    try {
      int number = Integer.parseInt(splitted[0]);
      node.setNumber(number);
    }
    catch (NumberFormatException e) {
    }

    return node;
  }

  public static boolean isValidGraph(GraphInfo graph) {
    if (graph == null || graph.getNodeList().size() <= 0)
      return false;
    for (int i = 0; i < graph.getNodeList().size(); i++) {
      NodeInfo node = graph.getNodeList().get(i);
      if (!isValidNode(node))
        return false;
    }
    return true;
  }

  public static boolean isValidNode(NodeInfo node) {
    if (node == null)
      return false;
    String nodeValue = node.getValue();
    if (nodeValue == null) // 短语结点的value为null
      return true;

    if (isTooShort(nodeValue))
      return false;// 单词太短没有意义
    if (!isValidCharacters(nodeValue))
      return false;// 字符不合格
    if (isCODEPlaceholder(nodeValue))
      return false;// 不能是CODExx
    if (node.getLabel() == NodeLabel.verb)
      if (isStopVerb(nodeValue)) // 停用动词
        return false;
    if (node.getLabel() == NodeLabel.noun)
      if (isStopNoun(nodeValue)) // 停用名词
        return false;

    return true;
  }

  public static boolean isTooShort(String str) {
    boolean isSingleChar = str.length() <= 1 && (!str.matches("[aA]"));
    return isSingleChar;
  }

  public static boolean isValidCharacters(String str) {
    String regexp = "[a-zA-Z0-9|\\-| ]*";
    return str.matches(regexp);
  }

  public static boolean isCODEPlaceholder(String str) {
    String regexp = "(CODE|code)[0-9]+";
    return str.matches(regexp);
  }

  public static boolean isStopVerb(String str) {
    return Rules.qa_verbs.toString().contains(str) || Rules.stop_verbs.toString().contains(str)
            || Rules.unlike_verbs.toString().contains(str) || Arrays.asList(Rules.BE_VERBS).contains(str)
            || Arrays.asList(Rules.MODAL_VERBS).contains(str)
            || Arrays.asList(Rules.HAVE_VERBS).contains(str);
  }

  public static boolean isStopNoun(String str) {
    return Rules.qa_nouns.toString().contains(str) || Rules.stop_nouns.toString().contains(str)
            || Rules.unlike_nouns.toString().contains(str);
  }

}

