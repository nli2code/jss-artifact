package entities.graph;

import de.parsemis.graph.Edge;

import java.io.Serializable;
import java.util.UUID;

public class EdgeInfo implements Serializable{
  private static final long serialVersionUID = 6038310513494512664L;

  public static final int	INCOMING	= Edge.INCOMING;
  public static final int	OUTGOING	= Edge.OUTGOING;
  public static final int	UNDIRECTED	= Edge.UNDIRECTED;

  private String		uuid;
  private NodeInfo	nodeA;
  private NodeInfo	nodeB;
  private int			direction;

  public EdgeInfo(NodeInfo nodeA, NodeInfo nodeB, int direction) {
    uuid = UUID.randomUUID().toString();
    this.nodeA = nodeA;
    this.nodeB = nodeB;
    this.direction = direction;
  }

  public NodeInfo getNodeA() {
    return nodeA;
  }

  public void setNodeA(NodeInfo nodeA) {
    this.nodeA = nodeA;
  }

  public NodeInfo getNodeB() {
    return nodeB;
  }

  public void setNodeB(NodeInfo nodeB) {
    this.nodeB = nodeB;
  }

  public int getDirection() {
    return direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof EdgeInfo))
      return false;
    EdgeInfo objEdge = (EdgeInfo) obj;
    if (this.nodeA.equals(objEdge.nodeA) && this.nodeB.equals(objEdge.nodeB)
            && this.direction == objEdge.direction)
      return true;
    else if (this.nodeA.equals(objEdge.nodeB) && this.nodeB.equals(objEdge.nodeA)
            && getOppositeDirection(this.direction) == objEdge.direction)
      return true;
    else
      return false;
  }

  @Override
  public int hashCode() {
    return nodeA.hashCode() + nodeB.hashCode() + direction;
  }

  public boolean equalsObject(Object obj) {
    if (obj == null || !(obj instanceof EdgeInfo))
      return false;
    EdgeInfo objEdge = (EdgeInfo) obj;
    if (this.nodeA.equalsObject(objEdge.nodeA) && this.nodeB.equalsObject(objEdge.nodeB)
            && this.direction == objEdge.direction)
      return true;
    else if (this.nodeA.equalsObject(objEdge.nodeB) && this.nodeB.equalsObject(objEdge.nodeA)
            && getOppositeDirection(this.direction) == objEdge.direction)
      return true;
    else
      return false;
  }

  @Override
  public String toString() {
    String directionStr;
    switch (direction) {
      case INCOMING:
        directionStr = "<-";
        break;
      case OUTGOING:
        directionStr = "->";
        break;
      case UNDIRECTED:
        directionStr = "--";
        break;
      default:
        directionStr = ", ";
        break;
    }
    return "(" + nodeA + " " + directionStr + " " + nodeB + ")";
  }

  public String toStringWithOrderNumber() {
    String directionStr;
    switch (direction) {
      case INCOMING:
        directionStr = "<-";
        break;
      case OUTGOING:
        directionStr = "->";
        break;
      case UNDIRECTED:
        directionStr = "--";
        break;
      default:
        directionStr = ", ";
        break;
    }
    return "(" + nodeA.toStringWithOrderNumber() + " " + directionStr + " "
            + nodeB.toStringWithOrderNumber() + ")";
  }
  public static int getOppositeDirection(int direction) {
    switch (direction) {
      case INCOMING:
        return OUTGOING;
      case OUTGOING:
        return INCOMING;
      case UNDIRECTED:
        return UNDIRECTED;
      default:
        return -1;
    }
  }
}
