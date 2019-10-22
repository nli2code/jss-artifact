package cn.edu.pku.hcst.kincoder.core.qa.hole_resolver;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Callable;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.PrimitiveType;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceQuestionFactory;
import cn.edu.pku.hcst.kincoder.core.qa.questions.PrimitiveQuestion;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.google.inject.Inject;

import java.util.Optional;

public class ArgumentHoleResolver implements HoleResolver {
    private final Repository repository;
    private final ChoiceQuestionFactory choiceQuestionFactory;

    @Inject
    public ArgumentHoleResolver(Repository repository, ChoiceQuestionFactory choiceQuestionFactory) {
        this.repository = repository;
        this.choiceQuestionFactory = choiceQuestionFactory;
    }

    @Override
    public Optional<Question> resolve(Context ctx, HoleExpr hole, boolean recommend) {
        var skeleton = ctx.getSkeleton();
        if (skeleton.parentOf(hole) instanceof Arg) {
            var arg = (Arg) skeleton.parentOf(hole);
            var callable = (Callable) skeleton.parentOf(arg);
            var index = callable.getArgs().indexOf(arg);

            if (arg.getType() instanceof PrimitiveType || arg.getType().describe().equals("java.lang.String")) {
                var entity = repository.getMethodEntity(callable.getQualifiedSignature());
                if (entity == null) return Optional.empty();
                var paramName = entity.getParamNames().split(",")[index];
                var paramJavadoc = entity.getParamJavadoc(paramName);
                return Optional.of(new PrimitiveQuestion(paramJavadoc, arg.getType()));
            } else {
                return Optional.of(choiceQuestionFactory.forType(ctx, arg.getType(), recommend));
            }
        }

        return Optional.empty();
    }

    @Override
    public int order() {
        return 0;
    }
}
