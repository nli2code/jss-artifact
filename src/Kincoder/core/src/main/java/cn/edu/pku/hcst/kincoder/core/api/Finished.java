package cn.edu.pku.hcst.kincoder.core.api;

import cn.edu.pku.hcst.kincoder.core.qa.Context;
import lombok.Value;

@Value
public class Finished implements QAResponse{
    private final Context ctx;
}