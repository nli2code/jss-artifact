package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.core.rules.CreateMethodJudger;
import cn.edu.pku.hcst.kincoder.core.rules.GetMethodJudger;
import cn.edu.pku.hcst.kincoder.core.rules.LoadMethodJudger;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import com.google.inject.Inject;

public class MethodCategorizer {
    private final CreateMethodJudger createMethodJudger;
    private final GetMethodJudger getMethodJudger;
    private final LoadMethodJudger loadMethodJudger;

    @Inject
    public MethodCategorizer(CreateMethodJudger createMethodJudger, GetMethodJudger getMethodJudger, LoadMethodJudger loadMethodJudger) {
        this.createMethodJudger = createMethodJudger;
        this.getMethodJudger = getMethodJudger;
        this.loadMethodJudger = loadMethodJudger;
    }

    public MethodCategory category(MethodEntity method) {
        if (createMethodJudger.judge(method)) return MethodCategory.CREATE;
        if (getMethodJudger.judge(method)) return MethodCategory.GET;
        if (loadMethodJudger.judge(method)) return MethodCategory.LOAD;
        return MethodCategory.UNKNOWN;
    }
}
