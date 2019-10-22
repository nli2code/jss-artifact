package cn.edu.pku.hcst.kincoder.core.rules;

import java.util.List;

public abstract class Judger<T> {
    protected List<Rule<T>> rules;

    abstract public boolean judge(T value);
}
