package cn.edu.pku.hcst.kincoder.core.qa.hole_resolver;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;

import java.util.Optional;

public interface HoleResolver {
    Optional<Question> resolve(Context ctx, HoleExpr hole, boolean recommend);

    int order();
}
