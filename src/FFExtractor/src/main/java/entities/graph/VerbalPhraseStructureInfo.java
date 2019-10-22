package entities.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import edu.stanford.nlp.trees.Tree;
import entities.ff.TaskInfo;
import entities.graph.dictionary.ConjunctionInfo;
import entities.graph.dictionary.DictionaryInfo;
import entities.graph.dictionary.VerbInfo;
import entities.graph.dictionary.WordInfo;
import utils.TreeUtils;

/**
 * Structure: VP = verb [+ particle] + subNP + pp [+ pp..]
 */
public class VerbalPhraseStructureInfo implements Serializable {
  private static final long				serialVersionUID	= 3986066856103347784L;

  // private String uuid;
  private VerbInfo verb				= null;
  // 助词，play around*, break down*, set up*
  private ConjunctionInfo particle			= null;
  private NounPhraseStructureInfo			subNP				= null;
  private List<PrepPhraseStructureInfo>	subPPList			= new ArrayList<>();

  private static final int				BEFORE_VB			= 1;
  private static final int				AFTER_VB_BEFORE_NP	= 2;
  private static final int				AFTER_NP_BEFORE_PP	= 3;
  private static final int				AFTER_PP			= 4;

  public VerbalPhraseStructureInfo() {
    super();
  }

  // stemmed tree
  public VerbalPhraseStructureInfo(Tree tree) {
    this();

    if (!TreeUtils.isVP(tree))
      return;

    int pos = BEFORE_VB;
    Tree[] children = tree.children();
    for (int i = 0; i < children.length; i++) {
      Tree child = children[i];
      if (pos == BEFORE_VB) {
        if (TreeUtils.isVB(child)) {
          String verb = TreeUtils.getLeafString(child);
          VerbInfo keyVerb = DictionaryInfo.addVerb(verb);
          this.setVerb(keyVerb);
          pos = AFTER_VB_BEFORE_NP;
        }
      }
      else if (pos == AFTER_VB_BEFORE_NP) {
        if (TreeUtils.isParticle(child)) {
          Tree particleWord = child.getChild(0);
          if (TreeUtils.isParticleWord(particleWord)) {
            String particleStr = TreeUtils.getLeafString(particleWord);
            particle = DictionaryInfo.addConjunction(particleStr);
          }
        }
        else if (TreeUtils.isNP(child)) {
          subNP = new NounPhraseStructureInfo(child);
          pos = AFTER_NP_BEFORE_PP;
        }
        else if (TreeUtils.isPP(child)) {
          PrepPhraseStructureInfo ppStructure = new PrepPhraseStructureInfo(child);
          subPPList.add(ppStructure);
          pos = AFTER_PP;
        }
      }
      else if (pos == AFTER_NP_BEFORE_PP || pos == AFTER_PP) {
        if (TreeUtils.isPP(child)) {
          PrepPhraseStructureInfo ppStructure = new PrepPhraseStructureInfo(child);
          subPPList.add(ppStructure);
          pos = AFTER_PP;
        }
      }
    }

    adjust();
  }

  public void adjust() {
    if (subPPList.size() <= 0)
      if (getSubNP() != null && subNP.getSubPP() != null) {
        // subNP包含了一个pp
        subPPList.add(subNP.getSubPP());
        subNP.setSubPP(null);
      }
  }

  public VerbInfo getVerb() {
    return verb;
  }

  public void setVerb(VerbInfo keyVerb) {
    this.verb = keyVerb;
  }

  public NounPhraseStructureInfo getSubNP() {
    return subNP;
  }

  public void setSubNP(NounPhraseStructureInfo directNP) {
    this.subNP = directNP;
  }

  public PrepPhraseStructureInfo getDirectPP() {
    if (subPPList == null || subPPList.size() <= 0)
      return null;
    else
      return subPPList.get(0);
  }

  public List<PrepPhraseStructureInfo> getSubPPList() {
    return subPPList;
  }

  public void setSubPPList(List<PrepPhraseStructureInfo> ppList) {
    this.subPPList = ppList;
  }

  public void addSubPP(PrepPhraseStructureInfo pp) {
    subPPList.add(pp);
  }

  public ConjunctionInfo getParticle() {
    return particle;
  }

  public void setParticle(ConjunctionInfo particle) {
    this.particle = particle;
  }

  public String toNLText() {
    StringBuilder str = new StringBuilder();
    if (verb != null)
      str.append(verb.toString());
    if (particle != null)
      str.append(" " + particle.toString());
    if (subNP != null)
      str.append(" " + subNP.toNLText());
    if (subPPList != null)
      for (PrepPhraseStructureInfo subPP : subPPList) {
        str.append(" " + subPP.toNLText());
      }
    return str.toString();
  }

  public String toTaskText() {
    StringBuilder str = new StringBuilder();
    if (verb != null)
      str.append(verb.toString() + TaskInfo.POS_SEPARATOR + TaskInfo.POS_VERB);
    if (particle != null)
      str.append(" " + particle.toString() + TaskInfo.POS_SEPARATOR + TaskInfo.POS_CONJ);
    if (subNP != null)
      str.append(" " + subNP.toTaskText());
    if (subPPList != null)
      for (PrepPhraseStructureInfo subPP : subPPList) {
        str.append(" " + subPP.toTaskText());
      }
    return str.toString();
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder("<VP ");
    if (verb != null)
      str.append("<VB " + verb.toString() + ">");
    if (particle != null)
      str.append("<PRT " + particle.toString() + ">");
    if (subNP != null)
      str.append(subNP.toString());
    if (subPPList != null)
      for (PrepPhraseStructureInfo subPP : subPPList) {
        str.append(subPP.toString());
      }

    str.append("/>");
    return str.toString();
  }

  public List<WordInfo> getSubPPListAsWordList() {
    List<WordInfo> result = new ArrayList<>();
    subPPList.forEach(x -> {
      result.addAll(x.toWordList());
    });
    return result;
  }

  public List<WordInfo> toWordList() {
    List<WordInfo> result = new ArrayList<>();
    if (verb != null)
      result.add(verb);
    if (particle != null)
      result.add(particle);
    if (subNP != null)
      result.addAll(subNP.toWordList());
    if (subPPList != null)
      subPPList.forEach(x -> {
        result.addAll(x.toWordList());
      });
    return result;
  }

  public Set<String> toWordBag() {
    Set<String> wordBag = new HashSet<>();
    List<WordInfo> words = toWordList();
    for (WordInfo wordInfo : words) {
      wordBag.add(wordInfo.getName());
    }
    return wordBag;
  }

}

