package filters;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import entities.ff.PhraseInfo;
import entities.ff.Proof;
import entities.ff.ProofType;

public class FilterPronoun {

  public static boolean filter(PhraseInfo phrase) {
    if (phrase == null)
      return false;
    Tree phraseTree = Tree.valueOf(phrase.getSyntaxTree());
    if (phraseTree == null)
      return false;

    // __ < ( NP < (PRP !< it|them) ) | < ( NP < ( NP < (PRP !< it|them) ) )
    String invalidPronounPattern = " ( NP < (PRP=prp !< "
            + Rules.ruleWordsConjuctionForTregex(Rules.VALID_PRONOUNS) + ") ) ";
    String filterPattern = "__ < " + invalidPronounPattern + " | < ( NP <" + invalidPronounPattern + ")";

    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(phraseTree);

    if (matcher.matches()) {
      Proof proof = new Proof(ProofType.FAIL_PERSONAL_PRONOUN);
      Tree evdTree = matcher.getNode("prp");
      proof.setEvidenceTree(evdTree.pennString().trim());
      phrase.addProof(proof);
      return false;
    }

    return true;
  }
}
