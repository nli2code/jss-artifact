package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.PrimitiveType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

@Value
public class PrimitiveQuestion implements Question {
    @Nullable
    private final String hint;
    private final Type type; // 可能是PrimitiveType或ReferenceType(java.lang.String)

    @Override
    public String description() {
        if (hint == null) {
            if (type instanceof PrimitiveType) return String.format("Please input a %s:", type.describe());
            return "Please input a string:";
        }
        if (type instanceof PrimitiveType) return String.format("Please input a %s(%s):", type.describe(), hint);
        return String.format("Please input %s:", hint);
    }

    @Override
    public QuestionResult processInput(Context ctx, HoleExpr hole, String input) {
        try {
            Expr<?> expr = null;
            switch (type.describe()) {
                case "boolean":
                    expr = new BooleanLiteral(Boolean.valueOf(input));
                    break;
                case "byte":
                    expr = new ByteLiteral(Byte.valueOf(input));
                    break;
                case "short":
                    expr = new ShortLiteral(Short.valueOf(input));
                    break;
                case "int":
                    expr = new IntLiteral(Integer.valueOf(input));
                    break;
                case "long":
                    expr = new LongLiteral(Long.valueOf(input));
                    break;
                case "float":
                    expr = new FloatLiteral(Float.valueOf(input));
                    break;
                case "double":
                    expr = new DoubleLiteral(Double.valueOf(input));
                    break;
                case "char":
                    expr = new CharLiteral(input.charAt(0));
                    break;
                case "java.lang.String":
                    expr = new StringLiteral(input);
                    break;
            }
            return new Filled(ctx.withSkeleton(ctx.getSkeleton().fillHole(hole, expr)), expr);
        } catch (NumberFormatException e) {
            return new ErrorInput("Error Format!");
        }
    }
}
