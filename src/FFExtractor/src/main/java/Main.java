import edu.stanford.nlp.trees.Tree;
import entities.ff.PhraseInfo;
import entities.so.SOQuestion;
import filters.PhraseFilter;
import parser.NLPParser;
import parser.PhraseExtractor;
import parser.XMLParser;
import utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {

  static List<String> extract(String sentence) {
    Tree tree = NLPParser.parseGrammaticalTree(sentence);
    List<String> verbPhrases = new ArrayList<>();
    for (PhraseInfo phrase: PhraseExtractor.extractVerbPhrases(tree)) {
      PhraseFilter.filter(phrase, sentence);
      if (phrase.getProofScore() <= 0) continue;
      verbPhrases.add(phrase.getText());
    }
    return verbPhrases;
  }

  public static void main(String[] args) {
    SOQuestion q = new SOQuestion();
    q.setBody("<pre><code>u should not see this!</code></pre>" +
            "<p>I want to set cell color to red. That's all, thank you.</p>");
    String text = XMLParser.parseForText(q.getBody());
    for (String sentence: TextUtils.splitText(text)) {
      System.out.println("input sentence: " + sentence);
      System.out.println("functional features: " + extract(sentence));
    }
  }

}
