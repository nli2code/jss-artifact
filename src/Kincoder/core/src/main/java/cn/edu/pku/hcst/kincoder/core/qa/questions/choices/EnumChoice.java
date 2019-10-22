package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Choice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceResult;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Filled;
import cn.edu.pku.hcst.kincoder.kg.entity.EnumEntity;
import lombok.Value;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.enumerate;

@Value
public class EnumChoice implements Choice {
    private final EnumEntity enumEntity;

    @Override
    public ChoiceResult action(Context ctx, HoleExpr hole) {
        var expr = enumerate((ReferenceType) Type.fromString(enumEntity.getQualifiedName()), ctx.getSkeleton().getHoleFactory().create());
        var newSkeleton = ctx.getSkeleton().fillHole(hole, expr);
        return new Filled(ctx.withSkeleton(newSkeleton), expr);
    }
}
