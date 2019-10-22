package cn.edu.pku.hcst.kincoder.core.api;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.QuestionResult;
import lombok.Value;

@Value
public class NewQuestion implements QAResponse, UndoResponse, QuestionResult {
    private final Context ctx;
    private final HoleExpr hole;
    private final Question question;

}
