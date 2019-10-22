package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.BlockStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.ForEachStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ReplaceNodeVisitor;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.*;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.*;

@Getter
@EqualsAndHashCode
@ToString
public class IterableChoice implements Choice {
    private final ChoiceQuestionFactory choiceQuestionFactory;
    private final List<TypeEntity> path;
    @Nullable
    private final String recommendVar;
    private final boolean recommend;

    @Inject
    public IterableChoice(ChoiceQuestionFactory choiceQuestionFactory, @Assisted List<TypeEntity> path, @Assisted @Nullable String recommendVar, @Assisted boolean recommend) {
        this.choiceQuestionFactory = choiceQuestionFactory;
        this.path = path;
        this.recommendVar = recommendVar;
        this.recommend = recommend;
    }

    private Pair<ForEachStmt, String> buildForEachStmt(Context ctx, HoleExpr hole) {
        Pair<ForEachStmt, String> innerResult = null;

        for (int i = path.size() - 1; i > 0; --i) {
            var outer = path.get(i - 1);
            var inner = path.get(i);
            var iterableType = Type.fromString(inner.getQualifiedName());
            var iterableName = ctx.findFreeVariableName(Type.fromString(outer.getQualifiedName()));
            var varName = ctx.findFreeVariableName(iterableType);
            if (innerResult == null) {
                var forEachStmt = foreach(iterableType, varName, str2name(iterableName), block(ctx.getSkeleton().parentStmtOf(hole)));
                var visitor = new ReplaceNodeVisitor(hole, str2name(varName));
                innerResult = Pair.of(forEachStmt.accept(visitor, null), iterableName);
            } else {
                var forEachStmt = foreach(iterableType, innerResult.getRight(), str2name(iterableName), block(innerResult.getLeft()));
                innerResult = Pair.of(forEachStmt, iterableName);
            }
        }

        return innerResult;
    }

    @Override
    public ChoiceResult action(Context ctx, HoleExpr hole) {
        var targetType = (ReferenceType) Type.fromString(path.get(0).getQualifiedName());
        if (path.size() == 1) {
            if (recommendVar == null) {
                return new NewQuestion(choiceQuestionFactory.forType(ctx, targetType, recommend));
            } else {
                var name = str2name(recommendVar);
                return new Filled(ctx.withSkeleton(ctx.getSkeleton().fillHole(hole, name)), name);
            }

        } else {
            var skeleton = ctx.getSkeleton();
            var stmt = skeleton.parentStmtOf(hole);
            var blockStmt = (BlockStmt) skeleton.parentOf(stmt);
            var init = skeleton.getHoleFactory().create();
            var inner = buildForEachStmt(ctx, hole);
            var varDecl = recommendVar == null ? v(targetType, str2name(inner.getRight()), init)
                : v(targetType, str2name(inner.getRight()), str2name(recommendVar));
            return new Filled(ctx.withSkeleton(ctx.getSkeleton().replaceStmtInBlock(blockStmt, stmt, expr2stmt(varDecl), inner.getLeft())), varDecl);
        }
    }
}
