package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Choice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceResult;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Filled;
import lombok.Value;

@Value
public class RecommendChoice implements Choice {
    private final Context newCtx;
    private final Expr<?> filled;
    private final double score;

    @Override
    public ChoiceResult action(Context ctx, HoleExpr hole) {
        return new Filled(newCtx, filled);
    }
}
