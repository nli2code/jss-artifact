package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import entities.ff.PhraseInfo;
import entities.ff.Proof;
import entities.ff.ProofType;
import utils.TreeUtils;

public class PhraseExtractor {

  public static PhraseInfo[] extractVerbPhrases(Tree sentenceTree) {
    if (sentenceTree == null)
      return null;

    List<PhraseInfo> phraseList = new ArrayList<>();
    String vpPattern = "VP < /VB.*/";

    TregexPattern tregexPattern = TregexPattern.compile(vpPattern);
    TregexMatcher matcher = tregexPattern.matcher(sentenceTree);

    HashSet<Tree> treeSet = new HashSet<>();
    while (matcher.findNextMatchingNode()) {
      Tree matchedTree = matcher.getMatch();
      if (treeSet.contains(matchedTree)) continue;
      treeSet.add(matchedTree);

      PhraseInfo phrase = new PhraseInfo();
      phrase.setPhraseType(PhraseInfo.PHRASE_TYPE_VP);
      phrase.setText(TreeUtils.interpretTreeToString(matchedTree));
      phrase.setSyntaxTree(matchedTree.toString());
      phrase.addProof(new Proof(ProofType.INIT_EXTRACTION_VP));

      phraseList.add(phrase);
    }
    return phraseList.toArray(new PhraseInfo[phraseList.size()]);
  }

}