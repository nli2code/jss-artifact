package cn.edu.pku.hcst.kincoder.core.qa.hole_resolver;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.EnumConstantExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.EnumConstantQuestion;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;

import java.util.Optional;
import java.util.Set;

public class EnumConstantHoleResolver implements HoleResolver {
    private final Repository repository;

    public EnumConstantHoleResolver(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Question> resolve(Context ctx, HoleExpr hole, boolean recommend) {
        var parent = ctx.getSkeleton().parentOf(hole);

        if (parent instanceof EnumConstantExpr) {
            var p = ((EnumConstantExpr) parent);
            var enumEntity = repository.getEnumEntity(p.getEnumType().describe());
            Set<String> constants = enumEntity == null ? Set.of() : Set.of(enumEntity.getConstants().split(","));
            return Optional.of(new EnumConstantQuestion(p.getEnumType(), constants));
        }

        return Optional.empty();
    }

    @Override
    public int order() {
        return 0;
    }
}
