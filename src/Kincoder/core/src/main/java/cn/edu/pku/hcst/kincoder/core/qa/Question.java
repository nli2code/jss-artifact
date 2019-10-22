package cn.edu.pku.hcst.kincoder.core.qa;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.questions.QuestionResult;

public interface Question {
    String description();

    QuestionResult processInput(Context ctx, HoleExpr hole, String input);
}
