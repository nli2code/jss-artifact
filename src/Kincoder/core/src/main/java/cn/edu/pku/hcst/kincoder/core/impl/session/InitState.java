package cn.edu.pku.hcst.kincoder.core.impl.session;

import cn.edu.pku.hcst.kincoder.common.collection.ImmutableStack;
import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.api.*;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpService;
import cn.edu.pku.hcst.kincoder.core.qa.ContextFactory;
import cn.edu.pku.hcst.kincoder.core.qa.QuestionGenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InitState implements SessionState {
    private final ContextFactory contextFactory;

    private final NlpService nlpService;
    private final QuestionGenerator questionGenerator;

    private final String query;
    private final Map<String, String> variables;
    private final Set<String> extendedTypes;
    private final List<Pair<Skeleton, Double>> skeletons;

    InitState(ContextFactory contextFactory, NlpService nlpService, QuestionGenerator questionGenerator, String query, Map<String, String> variables, Set<String> extendedTypes, List<Pair<Skeleton, Double>> skeletons) {
        this.contextFactory = contextFactory;
        this.nlpService = nlpService;
        this.questionGenerator = questionGenerator;
        this.query = query;
        this.variables = variables;
        this.extendedTypes = extendedTypes;
        this.skeletons = skeletons;
    }

    @Override
    public Pair<SessionState, QAResponse> selectSkeleton(int id) {
        var nlpCtx = nlpService.parse(query);
        var v = variables.entrySet().stream()
            .map(e -> Pair.of(e.getKey(), Type.fromString(e.getValue())))
            .collect(Collectors.toSet());

        var ctx = contextFactory.create(query, v, skeletons.get(id).getLeft(), extendedTypes, nlpCtx);

        return questionGenerator.generate(ctx).<Pair<SessionState, QAResponse>>map(pair -> {
            var hole = pair.getLeft();
            var question = pair.getRight();

            return Pair.of(new WaitingState(questionGenerator, ctx, hole, question, ImmutableStack.of()), new NewQuestion(ctx, hole, question));
        }).orElse(Pair.of(new FinishedState(ctx), new Finished(ctx)));
    }

    @Override
    public Pair<SessionState, QAResponse> answer(String answer) {
        return Pair.of(this, new ErrorResponse("Select Skeleton First"));
    }

    @Override
    public Pair<SessionState, UndoResponse> undo() {
        return Pair.of(this, new ErrorResponse("Select Skeleton First"));
    }
}
