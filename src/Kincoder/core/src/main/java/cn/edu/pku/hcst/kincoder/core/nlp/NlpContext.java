package cn.edu.pku.hcst.kincoder.core.nlp;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Callable;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NlpContext {
    private final Repository repository;
    private final NlpService nlpService;
    private final SemanticGraph semanticGraph;
    private final List<CoreLabel> coreLabels;

    @Inject
    public NlpContext(Repository repository, NlpService nlpService, @Assisted SemanticGraph semanticGraph, @Assisted List<CoreLabel> coreLabels) {
        this.repository = repository;
        this.nlpService = nlpService;
        this.semanticGraph = semanticGraph;
        this.coreLabels = coreLabels;
    }

    private double enumSim(String constant) {
        var textSim = coreLabels.stream().map(CoreLabel::originalText).mapToDouble(t -> nlpService.wordSim(t, constant)).max().orElse(0.0);
        var nerSim = coreLabels.stream().map(CoreLabel::ner).filter(ner -> !ner.equals("O")).mapToDouble(ner -> nlpService.wordSim(ner, constant)).max().orElse(0.0);
        return Math.max(textSim, nerSim);
    }

    public List<Pair<String, Double>> recommendEnum(ReferenceType type) {
        var entity = repository.getEnumEntity(type.describe());
        if (entity == null) return List.of();
        return Arrays.stream(entity.getConstants().split(","))
            .map(c -> Pair.of(c, enumSim(c)))
            .filter(c -> c.getRight() > 0.5)
            .collect(Collectors.toList());
    }

    public List<Pair<String, Double>> recommendPrimitive(Callable method, Arg arg, Type type) {
        var methodEntity = repository.getMethodEntity(method.getQualifiedSignature());
        if (methodEntity == null) return List.of();
        var index = method.findArgIndex(arg);
        var paramName = methodEntity.getParamNames().split(",")[index];
        var paramPhrase = ElementUtil.lowerCamelCaseToPhrase(paramName);
        var paramJavadoc = methodEntity.getParamJavadoc(paramName);
        var javadocPhrase = paramJavadoc == null ? new String[0] : paramJavadoc.split(" ");

        switch (type.describe()) {
            case "byte":
            case "short":
            case "int":
            case "long":
                return coreLabels.stream()
                    .map(l -> Pair.of(l, l.get(CoreAnnotations.NumericCompositeValueAnnotation.class)))
                    .filter(l -> l.getRight() != null)
                    .map(l -> {
                        var word = semanticGraph.getNodeByIndex(l.getLeft().index());
                        var score = semanticGraph.getIncomingEdgesSorted(word).stream()
                            .map(SemanticGraphEdge::getSource)
                            .map(IndexedWord::originalText)
                            .mapToDouble(w -> Math.max(nlpService.phraseWordSim(List.of(paramPhrase), w), nlpService.phraseWordSim(List.of(javadocPhrase), w)))
                            .max()
                            .orElse(0.0);
                        return Pair.of((l.getRight().longValue() - 1) + "", score);
                    })
                    .filter(l -> l.getRight() > 0.5)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
