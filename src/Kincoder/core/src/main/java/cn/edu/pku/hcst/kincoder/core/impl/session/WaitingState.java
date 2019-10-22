package cn.edu.pku.hcst.kincoder.core.impl.session;

import cn.edu.pku.hcst.kincoder.common.collection.ImmutableStack;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.common.utils.Tuple3;
import cn.edu.pku.hcst.kincoder.core.api.*;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.QuestionGenerator;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ErrorInput;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Filled;

public class WaitingState implements SessionState {
    private final QuestionGenerator questionGenerator;

    private final Context ctx;
    private final HoleExpr hole;
    private final Question question;
    private final ImmutableStack<Tuple3<Context, HoleExpr, Question>> history;

    WaitingState(QuestionGenerator questionGenerator, Context ctx, HoleExpr hole, Question question, ImmutableStack<Tuple3<Context, HoleExpr, Question>> history) {
        this.questionGenerator = questionGenerator;
        this.ctx = ctx;
        this.hole = hole;
        this.question = question;
        this.history = history;
    }

    @Override
    public Pair<SessionState, QAResponse> selectSkeleton(int id) {
        return Pair.of(this, new ErrorResponse("Skeleton has been selected."));
    }

    @Override
    public Pair<SessionState, QAResponse> answer(String answer) {
        var result = question.processInput(ctx, hole, answer);

        if (result instanceof Filled) {
            var r = ((Filled) result);
            return questionGenerator.generate(r.getContext()).<Pair<SessionState, QAResponse>>map(pair -> {
                var hole = pair.getLeft();
                var question = pair.getRight();

                return Pair.of(new WaitingState(questionGenerator, ctx, hole, question, ImmutableStack.of()), new NewQuestion(ctx, hole, question));
            }).orElse(Pair.of(new FinishedState(ctx), new Finished(ctx)));
        }

        if (result instanceof NewQuestion) {
            var r = ((NewQuestion) result);
            return Pair.of(new WaitingState(questionGenerator, ctx, hole, r.getQuestion(), history.push(Tuple3.of(ctx, hole, question))), r);
        }

        return Pair.of(this, new ErrorAnswer(ctx, hole, question, ((ErrorInput) result).getMessage()));
    }

    @Override
    public Pair<SessionState, UndoResponse> undo() {
        var pair = history.pop();
        var value = pair.getLeft();
        var newHistory = pair.getRight();
        if (value == null) {
            return Pair.of(this, new CannotUndo());
        }

        return Pair.of(new WaitingState(questionGenerator, value.getV1(), value.getV2(), value.getV3(), newHistory), new NewQuestion(value.getV1(), value.getV2(), value.getV3()));
    }
}
