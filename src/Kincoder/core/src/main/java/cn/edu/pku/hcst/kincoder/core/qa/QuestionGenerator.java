package cn.edu.pku.hcst.kincoder.core.qa;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.api.KinCoderConfig;
import cn.edu.pku.hcst.kincoder.core.qa.hole_resolver.HoleResolver;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceQuestion;
import cn.edu.pku.hcst.kincoder.core.qa.questions.EnumConstantQuestion;
import cn.edu.pku.hcst.kincoder.core.qa.questions.PrimitiveQuestion;
import cn.edu.pku.hcst.kincoder.core.qa.questions.RecommendQuestion;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.RecommendChoice;
import com.google.inject.Inject;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionGenerator {
    private final HoleResolver resolver;
    private final RecommendService recommendService;
    private final KinCoderConfig config;

    @Inject
    public QuestionGenerator(RecommendService recommendService, KinCoderConfig config) {
        this.resolver = recommendService.getResolver();
        this.recommendService = recommendService;
        this.config = config;
    }

    public Optional<Pair<HoleExpr, Question>> generate(Context ctx) {
        if (ctx.getSkeleton().getHoles().isEmpty()) {
            return Optional.empty();
        }

        for (HoleExpr hole : ctx.getSkeleton().getHoles()) {
            var result = resolver.resolve(ctx, hole, false);
            if (result.isPresent()) {
                var question = result.get();
                if (question instanceof ChoiceQuestion) {
                    var choices =  Stream.concat(
                        recommendService.recommend(ctx, hole).stream()
                            .sorted(Comparator.comparingDouble(RecommendChoice::getScore).reversed())
                            .limit(config.getRecommendNumber()),
                        ((ChoiceQuestion) question).getChoices().stream()
                    ).collect(Collectors.toList());
                    return Optional.of(Pair.of(hole, new ChoiceQuestion(question.description(), choices)));
                } else if (question instanceof PrimitiveQuestion) {
                    return Optional.of(Pair.of(hole, new RecommendQuestion(question, recommendService.recommendPrimitive((PrimitiveQuestion) question, ctx, hole))));
                } else if (question instanceof EnumConstantQuestion) {
                    return Optional.of(Pair.of(hole, new RecommendQuestion(question, recommendService.recommendEnum((EnumConstantQuestion) question, ctx, hole))));
                } else {
                    return Optional.of(Pair.of(hole, question));
                }
            }
        }

        return Optional.empty();
    }
}
