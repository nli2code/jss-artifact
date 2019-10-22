package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Choice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceResult;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Filled;
import lombok.Value;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.str2name;

@Value
public class VariableChoice implements Choice {
    private final String name;

    @Override
    public ChoiceResult action(Context ctx, HoleExpr hole) {
        var nameExpr = str2name(name);
        return new Filled(ctx.withSkeleton(ctx.getSkeleton().fillHole(hole, nameExpr)), nameExpr);
    }
}
