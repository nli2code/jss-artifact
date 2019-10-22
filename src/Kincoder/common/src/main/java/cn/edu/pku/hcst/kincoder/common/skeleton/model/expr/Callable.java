package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;

import java.util.List;

public interface Callable {
    String getQualifiedSignature();

    int findArgIndex(Arg arg);

    List<Arg> getArgs();
}
