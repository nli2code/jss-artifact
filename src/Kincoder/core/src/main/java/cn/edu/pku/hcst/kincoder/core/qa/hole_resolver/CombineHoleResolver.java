package cn.edu.pku.hcst.kincoder.core.qa.hole_resolver;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;

import java.util.List;
import java.util.Optional;

public class CombineHoleResolver implements HoleResolver {
    private final List<HoleResolver> holeResolvers;

    public CombineHoleResolver(List<HoleResolver> holeResolvers) {
        this.holeResolvers = holeResolvers;
    }

    @Override
    public Optional<Question> resolve(Context ctx, HoleExpr hole, boolean recommend) {
        for (HoleResolver holeResolver : holeResolvers) {
            var result = holeResolver.resolve(ctx, hole, recommend);
            if (result.isPresent()) return result;
        }
        return Optional.empty();
    }

    @Override
    public int order() {
        throw new RuntimeException("Could not use this resolver directly!");
    }
}
