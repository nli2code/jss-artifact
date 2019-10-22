package entities.graph;

import java.io.Serializable;
import java.util.UUID;

public class NodeInfo implements Serializable, Comparable<NodeInfo> {
  private static final long serialVersionUID = 9135849985209562786L;

  private String		uuid	= null;
  private NodeLabel	label;
  private String		value;
  private int			number	= -1;

  public NodeInfo(NodeLabel label, String value) {
    uuid = UUID.randomUUID().toString();
    this.setLabel(label);
    this.setValue(value);
  }

  public NodeLabel getLabel() {
    return label;
  }

  public void setLabel(NodeLabel label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    if (value == "")
      this.value = null;
    else
      this.value = value;
  }

  public String toStringWithOrderNumber() {
    return number + this.toString();
  }

  @Override
  public String toString() {
    if (value == null)
      return "<" + label.toString() + ">";
    else
      return "<" + label.toString() + " " + value + ">";
  }

  // 按照内存对象比较相同，自己的GraphInfo
  public boolean equalsObject(Object obj) {
    return super.equals(obj);
  }

  // 完全按照节点的值来比较是否相同 - For Parsemis
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof NodeInfo))
      return false;
    NodeInfo comparedTo = (NodeInfo) obj;
    return this.toString().equals(comparedTo.toString());
  }

  @Override
  public int hashCode() {
    return this.getClass().getName().hashCode() + this.toString().hashCode();
  }

  @Override
  public int compareTo(NodeInfo other) {
    if (other != null) {
      int strComp = this.toString().compareTo(other.toString());
      return strComp;
    }
    return this.toString().compareTo(null);
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid() {
    this.uuid = UUID.randomUUID().toString();
  }
}
