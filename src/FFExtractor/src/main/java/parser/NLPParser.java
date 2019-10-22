package parser;

import edu.stanford.nlp.trees.Tree;

public class NLPParser {

  public static Tree parseGrammaticalTree(String sentence) {
    int i;
    for (i = sentence.length() - 1; i >= 0; i--) {
      char ch = sentence.charAt(i);
      if (Character.isLetter(ch) || Character.isDigit(ch))
        break;
    }
    sentence = sentence.substring(0, i + 1) + ".";
    Tree tree = StanfordParser.parseTree(sentence);
    return tree;
  }

}