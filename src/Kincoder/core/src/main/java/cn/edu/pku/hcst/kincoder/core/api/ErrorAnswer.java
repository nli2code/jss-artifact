package cn.edu.pku.hcst.kincoder.core.api;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import lombok.Value;

@Value
public class ErrorAnswer implements QAResponse{
    private final Context ctx;
    private final HoleExpr hole;
    private final Question question;
    private final String message;
}