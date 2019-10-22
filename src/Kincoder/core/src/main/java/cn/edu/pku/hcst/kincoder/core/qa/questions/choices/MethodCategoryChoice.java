package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.*;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
public class MethodCategoryChoice implements Choice {
    private final ReferenceType type;
    private final MethodCategory category;
    private final Set<MethodEntity> methods;

    @Override
    public ChoiceResult action(Context ctx, HoleExpr hole) {
        return new NewQuestion(new ChoiceQuestion("Which method?", methods.stream().map(MethodChoice::new).collect(Collectors.toList())));
    }
}
