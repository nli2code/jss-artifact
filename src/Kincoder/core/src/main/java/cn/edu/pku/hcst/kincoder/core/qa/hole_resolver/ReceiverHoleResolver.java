package cn.edu.pku.hcst.kincoder.core.qa.hole_resolver;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.MethodCallExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.common.utils.FList;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Choice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceQuestion;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceQuestionFactory;
import cn.edu.pku.hcst.kincoder.core.qa.questions.IterableChoiceFactory;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.google.common.collect.ImmutableList;

import java.util.Optional;
import java.util.stream.Collectors;

public class ReceiverHoleResolver implements HoleResolver {
    private final Repository repository;
    private final CodeUtil codeUtil;
    private final ChoiceQuestionFactory choiceQuestionFactory;
    private final IterableChoiceFactory iterableChoiceFactory;

    public ReceiverHoleResolver(Repository repository, CodeUtil codeUtil, ChoiceQuestionFactory choiceQuestionFactory, IterableChoiceFactory iterableChoiceFactory) {
        this.repository = repository;
        this.codeUtil = codeUtil;
        this.choiceQuestionFactory = choiceQuestionFactory;
        this.iterableChoiceFactory = iterableChoiceFactory;
    }

    @Override
    public Optional<Question> resolve(Context ctx, HoleExpr hole, boolean recommend) {
        var parent = ctx.getSkeleton().parentOf(hole);

        if (parent instanceof MethodCallExpr) {
            var p = ((MethodCallExpr) parent);
            if (p.getReceiver().getRight() == hole) {
                var methodEntity = repository.getMethodEntity(p.getQualifiedSignature());
                if (methodEntity == null) return Optional.empty();

                if (!methodEntity.getReturnType().equals("void")) {
                    return Optional.of(choiceQuestionFactory.forType(ctx, p.getReceiver().getLeft(), recommend));
                } else {
                    var rawPaths = codeUtil.getIterablePaths(p.getReceiver().getLeft());
                    var paths = rawPaths.stream()
                        .filter(rawPath -> rawPaths.stream().noneMatch(rawPath2 -> rawPath.getHead().getExtendedTypes().contains(rawPath2.getHead())))
                        .map(FList::toList)
                        .collect(Collectors.toList());
                    if (paths.size() == 1) {
                        return Optional.of(choiceQuestionFactory.forType(ctx, p.getReceiver().getLeft(), recommend));
                    } else {
                        var requireObject = ElementUtil.qualifiedName2Simple(p.getReceiver().getLeft().describe()).toLowerCase();
                        var question = paths.stream()
                            .map(path -> (path.size() == 1) ?
                                String.format("A %s", requireObject) :
                                String.format("Some %ss in a %s", requireObject, path.get(0).getSimpleName().toLowerCase())
                            )
                            .collect(Collectors.joining("/", "", "?"));
                        var vars = paths.stream()
                            .map(path -> Pair.of(path, Type.fromString(path.get(0).getQualifiedName())))
                            .flatMap(pair -> ctx.findVariables(pair.getRight()).stream().map(v -> Pair.of(pair.getLeft(), v)))
                            .map(pair -> iterableChoiceFactory.create(pair.getLeft(), pair.getRight(), recommend))
                            .collect(Collectors.toList());
                        var choices = ImmutableList.<Choice>builder()
                            .addAll(vars)
                            .addAll(paths.stream().map(path -> iterableChoiceFactory.create(path, null, recommend)).collect(Collectors.toList()))
                            .build();
                        return Optional.of(new ChoiceQuestion(question, choices));
                    }
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public int order() {
        return 0;
    }
}
