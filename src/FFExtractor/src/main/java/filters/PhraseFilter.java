package filters;

import edu.stanford.nlp.trees.Tree;
import entities.ff.PhraseInfo;


public class PhraseFilter {

  public static void filter(PhraseInfo phrase, String sentence) {

    if (phrase.getPhraseType() == PhraseInfo.PHRASE_TYPE_VP)
      CheckPhraseForm.checkVP(phrase);
    else if (phrase.getPhraseType() == PhraseInfo.PHRASE_TYPE_NP)
      CheckPhraseForm.checkNP(phrase);

    FilterBeVerb.filterInRootOnly(phrase);
    FilterModalVerb.filterInRootOnly(phrase);

    FilterNegation.filterInRootOnly(phrase);
    FilterNegation.filterThoroughly(phrase);
    FilterPronoun.filter(phrase);

    FilterVerb.filter(phrase);

    FilterNoun filterNoun = new FilterNoun(phrase);
    filterNoun.filter();

    FilterPhrase.filter(phrase);

    FilterContext filterContext = new FilterContext(phrase, sentence);
    filterContext.filter();

  }

}
