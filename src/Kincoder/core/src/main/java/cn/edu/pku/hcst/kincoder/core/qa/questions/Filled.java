package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import lombok.Value;

@Value
public class Filled implements ChoiceResult, QuestionResult {
    private final Context context;
    private final Expr filled;
}
