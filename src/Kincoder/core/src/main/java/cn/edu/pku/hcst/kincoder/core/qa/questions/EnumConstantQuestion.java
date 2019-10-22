package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.str2name;

@Value
public class EnumConstantQuestion implements Question {
    private final ReferenceType type;
    private final Set<String> constants;

    @Override
    public String description() {
        return String.format("Which %s?", ElementUtil.qualifiedName2Simple(type.describe()).toLowerCase());
    }

    @Override
    public QuestionResult processInput(Context ctx, HoleExpr hole, String input) {
        return constants.stream()
            .filter(c -> c.toLowerCase().equals(input.toLowerCase()))
            .findFirst()
            .<QuestionResult>map(c -> {
                var name = str2name(c);
                return new Filled(ctx.withSkeleton(ctx.getSkeleton().fillHole(hole, name)), name);
            })
            .orElse(new ErrorInput(String.format("Valid inputs are %s.", constants.stream().map(String::toLowerCase).collect(Collectors.joining("/")))));
    }
}
