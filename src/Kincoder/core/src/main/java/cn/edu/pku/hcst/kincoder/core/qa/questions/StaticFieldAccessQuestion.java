package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.str2name;

@Value
public class StaticFieldAccessQuestion implements Question {
    private final ReferenceType receiverType;
    private final Type targetType;
    private final Set<String> staticFields;

    @Override
    public String description() {
        return "Which field?";
    }

    @Override
    public QuestionResult processInput(Context ctx, HoleExpr hole, String input) {
        return staticFields.stream()
            .filter(c -> c.toLowerCase().equals(input.toLowerCase()))
            .findFirst()
            .<QuestionResult>map(c -> {
                var name = str2name(c);
                return new Filled(ctx.withSkeleton(ctx.getSkeleton().fillHole(hole, name)), name);
            })
            .orElse(new ErrorInput(String.format("Valid inputs are %s.", staticFields.stream().map(String::toLowerCase).collect(Collectors.joining("/")))));
    }
}
