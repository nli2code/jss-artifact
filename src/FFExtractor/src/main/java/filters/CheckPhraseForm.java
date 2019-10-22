package filters;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import entities.ff.PhraseInfo;
import entities.ff.Proof;
import entities.ff.ProofType;

// 基本格式的检查，符合vp的三类格式
public class CheckPhraseForm {
  public static void checkNP(PhraseInfo phrase) {

  }

  public static void checkVP(PhraseInfo phrase) {
    if (phrase == null)
      return;
    Tree tree = Tree.valueOf(phrase.getSyntaxTree());
    if (tree == null)
      return;

    if (phrase.getPhraseType() != PhraseInfo.PHRASE_TYPE_VP) {
      phrase.addProof(new Proof(ProofType.ILLEGAL_VP_PHRASE));
      return;
    }

    Tree[] children = tree.children();
    boolean hasVB = false;
    boolean hasNP_NN = false;
    boolean hasNP_NN_PP = false;
    boolean hasNP_DT = false;
    boolean hasNP_PRP = false;
    boolean hasPP = false;
    for (int i = 0; i < children.length; i++) {
      Tree child = children[i];

      if (!hasVB) {
        if (child.label().toString().startsWith("VB")) {
          hasVB = true;
          continue;
        }
      }
      if (!hasPP) {
        if (!hasNP_NN && !hasNP_PRP && !hasNP_DT) {
          if (child.label().toString().startsWith("S"))
            break;
          if (child.label().toString().startsWith(","))
            break;
        }

        if (matchNP_NN(child)) {
          hasNP_NN = true;
          if (matchNP_NN_PP(child)) {
            hasNP_NN_PP = true;
          }
          continue;
        }
        else if (!hasNP_NN) {
          if (matchNP_PRP(child)) {
            hasNP_PRP = true;
            continue;
          }
          else if (matchNP_DT(child)) {
            hasNP_DT = true;
            continue;
          }
        }
        if (matchPP(child)) {
          hasPP = true;
        }
      }
      else {
        break;
      }
    }

    Proof formProof = new Proof();
    if (hasNP_NN && !hasPP) {
      // VP=VB+NP
      formProof.setType(ProofType.FORM_VP_NP);
    }
    else if (hasNP_NN && hasPP) {
      // vp=vb+np+pp
      formProof.setType(ProofType.FORM_VP_NP_PP);
    }
    else if (hasPP) {
      // vp=vb+[dt|prp]+pp
      formProof.setType(ProofType.FORM_VP_PP);
    }
    else {
      formProof.setType(ProofType.ILLEGAL_VP_PHRASE);
      phrase.addProof(formProof);
      return;
    }

    phrase.addProof(formProof);

    Proof npFeatureProof = new Proof();
    if (hasNP_NN && hasNP_NN_PP) {
      // ..np=nn+pp
      npFeatureProof.setType(ProofType.FEATURE_NP_NN_PP);
    }
    else if (hasNP_NN && !hasNP_NN_PP) {
      // ..np=nn
      npFeatureProof.setType(ProofType.FEATURE_NP_NN);
    }
    else if (!hasNP_NN && hasNP_PRP) {
      // ..np=prp
      npFeatureProof.setType(ProofType.FEATURE_NP_PRP);
    }
    else if (!hasNP_NN && hasNP_DT) {
      // ..np=dt
      npFeatureProof.setType(ProofType.FEATURE_NP_DT);
    }
    else {
      npFeatureProof = null;
    }

    if (npFeatureProof != null)
      phrase.addProof(npFeatureProof);
  }

  private static boolean matchNP_NN(Tree tree) {
    if (tree == null)
      return false;

    String filterPattern = Rules.NP_NN_PATTERN;
    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(tree);

    return matcher.matches();
  }

  private static boolean matchNP_NN_PP(Tree tree) {
    if (tree == null)
      return false;

    String filterPattern = Rules.NP_NN_PP_PATTERN;
    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(tree);

    return matcher.matches();
  }

  private static boolean matchNP_PRP(Tree tree) {
    if (tree == null)
      return false;

    String filterPattern = Rules.NP_PRP_PATTERN;
    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(tree);

    return matcher.matches();
  }

  private static boolean matchNP_DT(Tree tree) {
    if (tree == null)
      return false;

    String filterPattern = Rules.NP_DT_PATTERN;
    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(tree);

    return matcher.matches();
  }

  private static boolean matchPP(Tree tree) {
    if (tree == null)
      return false;

    String filterPattern = Rules.PP_PATTERN;
    TregexPattern tregexPattern = TregexPattern.compile(filterPattern);
    TregexMatcher matcher = tregexPattern.matcher(tree);

    return matcher.matches();
  }

}
