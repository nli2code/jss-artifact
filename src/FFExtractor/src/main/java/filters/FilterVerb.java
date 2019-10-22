package filters;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import entities.ff.PhraseInfo;
import entities.ff.Proof;
import entities.ff.ProofType;

public class FilterVerb {
  public static boolean filter(PhraseInfo phrase) {
    boolean have = filterHave(phrase);
    boolean qaVerbs = filterQAVerbs(phrase);
    boolean stopVerbs = filterStopVerbs(phrase);
    boolean unlikeVerbs = filterUnlikeVerbs(phrase);

    if (have && qaVerbs && stopVerbs && unlikeVerbs)
      return true;
    else
      return false;
  }

  public static boolean filterHave(PhraseInfo phrase) {
    if (phrase == null)
      return false;
    Tree phraseTree = Tree.valueOf(phrase.getSyntaxTree());
    if (phraseTree == null)
      return false;

    // __ < ( /VB.*/ < have|has|had|'ve|'s|'d )
    String filterPattern = "__ < ( /VB.*/=vb < " + Rules.ruleWordsConjuctionForTregex(Rules.HAVE_VERBS)
            + " ) ";

    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(phraseTree);

    if (matcher.matches()) {
      Proof proof = new Proof(ProofType.FAIL_HAVE_VERB);
      Tree evdTree = matcher.getNode("vb");
      proof.setEvidenceTree(evdTree.pennString().trim());
      phrase.addProof(proof);
      return false;
    }

    return true;
  }

  public static boolean filterQAVerbs(PhraseInfo phrase) {
    if (phrase == null)
      return false;
    Tree phraseTree = Tree.valueOf(phrase.getSyntaxTree());
    if (phraseTree == null)
      return false;

    String filterPattern = "__ < ( /VB.*/=vb < " + Rules.ruleWordsConjuctionForTregex(Rules.qa_verbs)
            + " ) ";

    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(phraseTree);

    if (matcher.matches()) {
      Proof proof = new Proof(ProofType.FAIL_STOP_VERB);
      Tree evdTree = matcher.getNode("vb");
      proof.setEvidenceTree(evdTree.pennString().trim());
      phrase.addProof(proof);
      return false;
    }

    return true;
  }

  public static boolean filterStopVerbs(PhraseInfo phrase) {
    if (phrase == null)
      return false;
    Tree phraseTree = Tree.valueOf(phrase.getSyntaxTree());
    if (phraseTree == null)
      return false;

    String filterPattern = "__ < ( /VB.*/=vb < " + Rules.ruleWordsConjuctionForTregex(Rules.stop_verbs)
            + " ) ";

    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(phraseTree);

    if (matcher.matches()) {
      Proof proof = new Proof(ProofType.FAIL_STOP_VERB);
      Tree evdTree = matcher.getNode("vb");
      proof.setEvidenceTree(evdTree.pennString().trim());
      phrase.addProof(proof);
      return false;
    }

    return true;
  }

  public static boolean filterUnlikeVerbs(PhraseInfo phrase) {
    if (phrase == null)
      return false;
    Tree phraseTree = Tree.valueOf(phrase.getSyntaxTree());
    if (phraseTree == null)
      return false;

    String filterPattern = "__ < ( /VB.*/=vb < " + Rules.ruleWordsConjuctionForTregex(Rules.unlike_verbs)
            + " ) ";

    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(phraseTree);

    if (matcher.matches()) {
      Proof proof = new Proof(ProofType.FAIL_UNLIKE_VERB);
      Tree evdTree = matcher.getNode("vb");
      proof.setEvidenceTree(evdTree.pennString().trim());
      phrase.addProof(proof);
      return false;
    }

    return true;
  }

}
