package parser;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.tartarus.snowball.ext.EnglishStemmer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Stemmer {

  private static Properties				props;
  private static final StanfordCoreNLP	pipeline;

  private static List<String>	NOT_TO_STEM_WORDS;
  private static List<String>	NOT_TO_STEM_LABELS;

  static {
    NOT_TO_STEM_WORDS = new ArrayList<String>();
    NOT_TO_STEM_WORDS.add("API");
    NOT_TO_STEM_WORDS.add("api");
    NOT_TO_STEM_WORDS.add("data");
    NOT_TO_STEM_WORDS.add("formula");
    NOT_TO_STEM_WORDS.add("POI");
    NOT_TO_STEM_WORDS.add("poi");
    NOT_TO_STEM_WORDS.add("MS");
    NOT_TO_STEM_WORDS.add("ms");
    NOT_TO_STEM_WORDS.add("xls");
    NOT_TO_STEM_WORDS.add("XLS");
    NOT_TO_STEM_WORDS.add("wiki");
    NOT_TO_STEM_WORDS.add("Wiki");

    NOT_TO_STEM_LABELS = new ArrayList<String>();
    NOT_TO_STEM_LABELS.add("JJ");
    NOT_TO_STEM_LABELS.add("JJR");
    NOT_TO_STEM_LABELS.add("JJS");
    NOT_TO_STEM_LABELS.add("NNP");
    NOT_TO_STEM_LABELS.add("DT");
  }

  static {
    props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma");
    pipeline = new StanfordCoreNLP(props);
  }

  public static Tree stemTree(Tree tree) {
    if (tree == null)
      return null;

    if (tree.isLeaf()) {
      String stemmedString = stem(tree.value());
      tree.setValue(stemmedString);
      return tree;
    }

    if (tree.isPreTerminal()) {
      String label = tree.label().toString();
      if (NOT_TO_STEM_LABELS.contains(label)) {
        return tree;
      }
    }

    Tree[] children = tree.children();
    for (int i = 0; i < children.length; i++) {
      stemTree(children[i]);
    }

    return tree;
  }

  public static String stem(String str) {
    Annotation document = new Annotation(str);
    pipeline.annotate(document);
    String sent = "";
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
        String s = token.toString().trim();
        if (!NOT_TO_STEM_WORDS.contains(s))
          s = token.get(CoreAnnotations.LemmaAnnotation.class);
        sent = sent + " " + s;
      }
    }
    return sent.trim();
  }
}
