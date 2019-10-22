package cn.edu.pku.hcst.kincoder.core.nlp;

import com.google.inject.Inject;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLPClient;

import java.util.List;
import java.util.Properties;

public class NlpService {
    private final StanfordCoreNLPClient client;
    private final NlpContextFactory nlpContextFactory;

    @Inject
    public NlpService(NlpServerConfig config, NlpContextFactory nlpContextFactory) {
        this.nlpContextFactory = nlpContextFactory;
        var props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner");
        props.setProperty("ner.buildEntityMentions", "false");
        this.client = new StanfordCoreNLPClient(props, config.getHost(), config.getPort());
    }

    public String getFirstSentence(String text) {
        var annotation = client.process(text);
        var document = new CoreDocument(annotation);
        var sentences = document.sentences();
        return sentences != null && sentences.size() > 0 ? sentences.get(0).text() : "";
    }

    public NlpContext parse(String query) {
        var annotation = client.process(query);
        var document = new CoreDocument(annotation);
        var sentence = document.sentences().get(0);
        return nlpContextFactory.create(sentence.dependencyParse(), sentence.tokens());
    }

    public double phraseWordSim(List<String> p, String w) {
        return p.stream().mapToDouble(word -> wordSim(word, w)).max().orElse(0.0);
    }

    public double wordSim(String word1, String word2) {
        var w1 = word1.toLowerCase();
        var w2 = word2.toLowerCase();
        if (w1.equals(w2)) return 1.0;
        if (w1.startsWith(w2)) return 0.8;
        if (w2.startsWith(w1)) return 0.8;
        if (removeVowel(w1).startsWith(w2)) return 0.6;
        if (removeVowel(w2).startsWith(w1)) return 0.6;
        return 0.0;
    }

    public String removeVowel(String word) {
        return word.replaceAll("[aeiou]", "");
    }
}
