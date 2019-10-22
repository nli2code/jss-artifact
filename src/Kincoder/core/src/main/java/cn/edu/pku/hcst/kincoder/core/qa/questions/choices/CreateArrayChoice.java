package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ArrayType;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ArrayLengthQuestion;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Choice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceResult;
import cn.edu.pku.hcst.kincoder.core.qa.questions.NewQuestion;
import lombok.Value;

@Value
public class CreateArrayChoice implements Choice {
    private final ArrayType type;

    @Override
    public ChoiceResult action(Context ctx, HoleExpr hole) {
//        var expr = create(type.getComponentType(), List.of(ctx.getSkeleton().getHoleFactory().create()));
//        val newPattern = context.pattern.fillHole(hole, expr)
//        Resolved(context.copy(pattern = newPattern), expr)
//        return null;
        return new NewQuestion(new ArrayLengthQuestion());
    }
}
