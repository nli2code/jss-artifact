package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Choice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceResult;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Filled;
import cn.edu.pku.hcst.kincoder.core.qa.questions.UnImplemented;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import lombok.Value;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.arg;
import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.call;

@Value
public class MethodChoice implements Choice {
    private final MethodEntity method;

    @Override
    public ChoiceResult action(Context ctx, HoleExpr hole) {
        if (method.isConstructor()) {
            return new UnImplemented();
        }

        var skeleton = ctx.getSkeleton();
        var receiverType = method.getDeclareType().getQualifiedName();
        var args = ElementUtil.methodParams(method.getSignature()).stream().map(ty -> arg(ty, skeleton.getHoleFactory().create()));

        var expr = method.isStatic() ?
            call((ReferenceType) Type.fromString(receiverType), method.getSimpleName(), args.toArray(Arg[]::new)) :
            call(skeleton.getHoleFactory().create(), (ReferenceType) Type.fromString(receiverType), method.getSimpleName(), args.toArray(Arg[]::new));

        var newSkeleton = skeleton.fillHole(hole, expr);
        return new Filled(ctx.withSkeleton(newSkeleton), expr);
    }
}
