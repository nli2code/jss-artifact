package cn.edu.pku.hcst.kincoder.core.qa.hole_resolver;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.VarDeclExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceQuestionFactory;
import com.google.inject.Inject;

import java.util.Optional;

public class VariableInitHoleResolver implements HoleResolver {
    private final ChoiceQuestionFactory choiceQuestionFactory;

    @Inject
    public VariableInitHoleResolver(ChoiceQuestionFactory choiceQuestionFactory) {
        this.choiceQuestionFactory = choiceQuestionFactory;
    }

    @Override
    public Optional<Question> resolve(Context ctx, HoleExpr hole, boolean recommend) {
        var parent = ctx.getSkeleton().parentOf(hole);
        if (parent instanceof VarDeclExpr) {
            var p = ((VarDeclExpr) parent);
            return Optional.of(choiceQuestionFactory.forType(ctx, p.getType(), recommend));
        }

        return Optional.empty();
    }

    @Override
    public int order() {
        return 0;
    }
}
