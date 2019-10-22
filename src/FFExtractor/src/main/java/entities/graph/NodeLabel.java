package entities.graph;

public enum NodeLabel {
  DEFAULT(0),
  VP(1),
  NP(2),
  PP(3),
  verb(4),
  noun(5),
  adj(6),
  conj(7),
  other(8)
  ;

  private int number;

  private NodeLabel(int typeNum) {
    this.number = typeNum;
  }

  public int getNumber() {
    return number;
  }

  public static NodeLabel getLabel(String str) {
    switch (str) {
      case "VP":
        return VP;
      case "NP":
        return NP;
      case "PP":
        return PP;
      case "verb":
        return verb;
      case "noun":
        return noun;
      case "adj":
        return adj;
      case "conj":
        return conj;
      case "other":
        return other;
      default:
        return DEFAULT;
    }
  }

  public static void main(String[] args) {
    for (NodeLabel v : values()) {
      System.out.println(v);
    }
  }
}
