package cn.edu.pku.hcst.kincoder.core.qa;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Callable;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.MethodCallExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ReplaceNodeVisitor;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.api.KinCoderConfig;
import cn.edu.pku.hcst.kincoder.core.qa.hole_resolver.CombineHoleResolver;
import cn.edu.pku.hcst.kincoder.core.qa.hole_resolver.HoleResolver;
import cn.edu.pku.hcst.kincoder.core.qa.questions.*;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.*;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.str2name;

public class RecommendService {
    @Getter
    private final HoleResolver resolver;
    private final KinCoderConfig config;

    @Inject
    public RecommendService(Set<HoleResolver> holeResolvers, KinCoderConfig config) {
        this.resolver = new CombineHoleResolver(holeResolvers.stream()
            .sorted(Comparator.comparingInt(HoleResolver::order))
            .collect(Collectors.toList()));
        this.config = config;
    }

    private double scoreOfChoice(Choice choice) {
        if (choice instanceof VariableChoice) {
            return 0.0;
        }
        if (choice instanceof EnumChoice) {
            return -0.02;
        }
        if (choice instanceof MethodChoice) {
            return -0.2;
        }
        if (choice instanceof CreateArrayChoice) {
            return -0.2;
        }
        if (choice instanceof IterableChoice) {
            return -0.2;
        }
        throw new UnsupportedOperationException();
    }

    private double penaltyOfHole(HoleExpr hole) {
        return 1.0;
    }

    private List<RecommendChoice> next(Context ctx, Expr<?> filled, int depth, double score, List<HoleExpr> originHoles, Set<HoleExpr> ignoredHoles) {
        var newSkeleton = ctx.getSkeleton();
        var remains = newSkeleton.getHoles().stream()
            .filter(Predicate.not(originHoles::contains))
            .filter(Predicate.not(ignoredHoles::contains))
            .collect(Collectors.toList());

        if (remains.isEmpty()) {
            return List.of(choice(ctx, filled, score, originHoles));
        }
        return recommend(ctx, filled, remains.get(0), depth + 1, score, originHoles, ignoredHoles);
    }

    private RecommendChoice choice(Context ctx, Expr<?> filled, double score, List<HoleExpr> originHoles) {
        var skeleton = ctx.getSkeleton();
        var finalScore = score - skeleton.getHoles().stream()
            .filter(Predicate.not(originHoles::contains))
            .map(this::penaltyOfHole)
            .reduce(0.0, Double::sum);
        return new RecommendChoice(ctx, filled, finalScore);
    }

    private Expr<?> fillExpr(@Nullable Expr<?> oldFilled, HoleExpr hole, Expr<?> expr) {
        return oldFilled == null ? expr : new ReplaceNodeVisitor(hole, expr).visit(oldFilled, null);
    }

    private List<RecommendChoice> recommend(Context ctx, @Nullable Expr<?> filled, Question question, HoleExpr hole, int depth, double score, List<HoleExpr> originHoles, Set<HoleExpr> ignoredHoles) {
        if (question instanceof PrimitiveQuestion && ctx.getSkeleton().parentOf(hole) instanceof Arg) {
            var skeleton = ctx.getSkeleton();
            var arg = ((Arg) skeleton.parentOf(hole));
            var method = ((MethodCallExpr) skeleton.parentOf(arg));
            return ctx.getNlpCtx().recommendPrimitive(method, arg, ((PrimitiveQuestion) question).getType()).stream()
                .flatMap(answer -> {
                    var result = question.processInput(ctx, hole, answer.getLeft());

                    if (result instanceof Filled) {
                        var filledResult = ((Filled) result);
                        return next(filledResult.getContext(), fillExpr(filled, hole, str2name(answer.getLeft())), depth, score + answer.getRight(), originHoles, ignoredHoles).stream();
                    }

                    return Stream.of();
                }).collect(Collectors.toList());
        } else if (question instanceof EnumConstantQuestion) {
            return ctx.getNlpCtx().recommendEnum(((EnumConstantQuestion) question).getType()).stream()
                .flatMap(answer -> {
                    var result = question.processInput(ctx, hole, answer.getLeft());

                    if (result instanceof Filled) {
                        var filledResult = ((Filled) result);
                        return next(filledResult.getContext(), fillExpr(filled, hole, str2name(answer.getLeft())), depth, score - 1.0 + answer.getRight(), originHoles, ignoredHoles).stream();
                    }

                    return Stream.of();
                }).collect(Collectors.toList());
        } else if (question instanceof PrimitiveQuestion || question instanceof StaticFieldAccessQuestion || question instanceof ArrayLengthQuestion) {
            var skeleton = ctx.getSkeleton();
            return skeleton.getHoles().stream()
                .filter(Predicate.not(originHoles::contains))
                .filter(Predicate.not(ignoredHoles::contains))
                .findFirst()
                .map(h -> recommend(ctx, filled, h, depth, score, originHoles, ImmutableSet.<HoleExpr>builder().addAll(ignoredHoles).add(hole).build()))
                .orElse(filled == null ? List.of() : List.of(choice(ctx, filled, score, originHoles)));
        } else if (question instanceof ChoiceQuestion) {
            return ((ChoiceQuestion) question).getChoices().stream()
                .flatMap(c -> {
                    var result = c.action(ctx, hole);

                    if (result instanceof NewQuestion) {
                        var r = ((NewQuestion) result);
                        return recommend(ctx, filled, r.getQuestion(), hole, depth, score, originHoles, ignoredHoles).stream();
                    }

                    if (result instanceof Filled) {
                        var r = ((Filled) result);
                        return next(r.getContext(), fillExpr(filled, hole, r.getFilled()), depth, score + scoreOfChoice(c), originHoles, ignoredHoles).stream();
                    }

                    return Stream.of();
                }).collect(Collectors.toList());
        }
        throw new UnsupportedOperationException("");
    }

    private List<RecommendChoice> recommend(Context ctx, @Nullable Expr<?> filled, HoleExpr hole, int depth, double score, List<HoleExpr> originHoles, Set<HoleExpr> ignoredHoles) {
        if (depth == config.getMaxSearchStep()) {
            return List.of(choice(ctx, filled, score, originHoles));
        } else {
            return resolver.resolve(ctx, hole, true).map(q -> recommend(ctx, filled, q, hole, depth, score, originHoles, ignoredHoles)).orElse(List.of());
        }
    }

    public List<RecommendChoice> recommend(Context ctx, HoleExpr hole) {
        return recommend(ctx, null, hole, 0, 0.0, ctx.getSkeleton().getHoles(), Set.of())
            .stream()
            .distinct()
            .collect(Collectors.toList());
    }

    public List<RecommendChoice> recommendPrimitive(PrimitiveQuestion q, Context ctx, HoleExpr hole) {
        return isArg(ctx, hole).map(p ->
            ctx.getNlpCtx().recommendPrimitive(p.getLeft(), p.getRight(), q.getType()).stream()
                .map(r -> {
                    var newContext = ((Filled) q.processInput(ctx, hole, r.getLeft())).getContext();
                    return new RecommendChoice(newContext, str2name(r.getLeft()), r.getRight());
                }).collect(Collectors.toList())
        ).orElse(List.of());
    }

    public List<RecommendChoice> recommendEnum(EnumConstantQuestion q, Context ctx, HoleExpr hole) {
        return ctx.getNlpCtx().recommendEnum(q.getType()).stream()
            .map(p -> {
                var newContext = ((Filled) q.processInput(ctx, hole, p.getLeft())).getContext();
                return new RecommendChoice(newContext, str2name(p.getLeft()), p.getRight());
            })
            .collect(Collectors.toList());
    }


    private Optional<Pair<Callable, Arg>> isArg(Context ctx, HoleExpr hole) {
        var skeleton = ctx.getSkeleton();
        var parent = skeleton.parentOf(hole);
        if (parent instanceof Arg) {
            var callable = ((Callable) skeleton.parentOf(parent));
            return Optional.of(Pair.of(callable, ((Arg) parent)));
        }
        return Optional.empty();
    }
}
