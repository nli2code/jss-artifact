package cn.edu.pku.hcst.kincoder.core.api;

import cn.edu.pku.hcst.kincoder.core.CoreModule;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpServerConfig;
import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphConfig;
import com.google.inject.Guice;

import java.util.Map;
import java.util.Set;

public interface KinCoderService {
    StartSessionResponse startSession(String query, Map<String, String> variables, Set<String> extendedTypes);

    QAResponse selectPattern(long sessionId, int id);

    QAResponse response(long sessionId, String answer);

    UndoResponse undo(long sessionId);

    static KinCoderService start(KinCoderConfig kinCoderConfig, NlpServerConfig nlpServerConfig, KnowledgeGraphConfig knowledgeGraphConfig) {
        var injector = Guice.createInjector(
            new CoreModule(kinCoderConfig, nlpServerConfig, knowledgeGraphConfig)
        );
        return injector.getInstance(KinCoderService.class);
    }
}
