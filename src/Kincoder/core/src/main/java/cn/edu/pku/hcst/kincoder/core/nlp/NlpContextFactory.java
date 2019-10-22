package cn.edu.pku.hcst.kincoder.core.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.semgraph.SemanticGraph;

import java.util.List;

public interface NlpContextFactory {
    NlpContext create(SemanticGraph semanticGraph, List<CoreLabel> coreLabels);
}
