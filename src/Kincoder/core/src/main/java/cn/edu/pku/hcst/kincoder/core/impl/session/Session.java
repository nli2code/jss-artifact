package cn.edu.pku.hcst.kincoder.core.impl.session;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.api.QAResponse;
import cn.edu.pku.hcst.kincoder.core.api.UndoResponse;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpService;
import cn.edu.pku.hcst.kincoder.core.qa.ContextFactory;
import cn.edu.pku.hcst.kincoder.core.qa.QuestionGenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Session {
    private final NlpService nlpService;
    private final QuestionGenerator questionGenerator;
    private SessionState state;

    public Session(ContextFactory contextFactory, NlpService nlpService, QuestionGenerator questionGenerator, String query, Map<String, String> variables, Set<String> extendedTypes, List<Pair<Skeleton, Double>> skeletons) {
        this.nlpService = nlpService;
        this.questionGenerator = questionGenerator;
        this.state = new InitState(contextFactory, nlpService, questionGenerator, query, variables, extendedTypes, skeletons);
    }

    public QAResponse selectSkeleton(int id) {
        var result = state.selectSkeleton(id);
        state = result.getLeft();
        return result.getRight();
    }

    public QAResponse answer(String answer) {
        var result = state.answer(answer);
        state = result.getLeft();
        return result.getRight();
    }

    public UndoResponse undo() {
        var result = state.undo();
        state = result.getLeft();
        return result.getRight();
    }
}
