package cn.edu.pku.hcst.kincoder.core.qa.hole_resolver;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.StaticFieldAccessExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.StaticFieldAccessQuestion;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import com.google.inject.Inject;

import java.util.Optional;

public class StaticFieldHoleResolver implements HoleResolver {
    private final CodeUtil codeUtil;

    @Inject
    public StaticFieldHoleResolver(CodeUtil codeUtil) {
        this.codeUtil = codeUtil;
    }

    @Override
    public Optional<Question> resolve(Context ctx, HoleExpr hole, boolean recommend) {
        var parent = ctx.getSkeleton().parentOf(hole);
        if (parent instanceof StaticFieldAccessExpr) {
            var p = ((StaticFieldAccessExpr) parent);
            var staticFields = codeUtil.staticFields(p.getReceiverType(), p.getTargetType());
            return Optional.of(new StaticFieldAccessQuestion(p.getReceiverType(), p.getTargetType(), staticFields));
        }

        return Optional.empty();
    }

    @Override
    public int order() {
        return 0;
    }
}
