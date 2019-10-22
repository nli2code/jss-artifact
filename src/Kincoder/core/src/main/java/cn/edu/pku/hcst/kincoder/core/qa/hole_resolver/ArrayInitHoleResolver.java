package cn.edu.pku.hcst.kincoder.core.qa.hole_resolver;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.ArrayCreationExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;

import java.util.Optional;

public class ArrayInitHoleResolver implements HoleResolver {
    @Override
    public Optional<Question> resolve(Context ctx, HoleExpr hole, boolean recommend) {
        var skeleton = ctx.getSkeleton();
        var parent = skeleton.parentOf(hole);
        if (parent instanceof ArrayCreationExpr) {
            var p = ((ArrayCreationExpr) parent);

        }
        return Optional.empty();
    }

    @Override
    public int order() {
        return 0;
    }
}
