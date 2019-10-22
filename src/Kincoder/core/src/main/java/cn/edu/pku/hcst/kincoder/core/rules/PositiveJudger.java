package cn.edu.pku.hcst.kincoder.core.rules;

public abstract class PositiveJudger<T> extends Judger<T> {
    @Override
    final public boolean judge(T value) {
        return rules.stream().anyMatch(r -> r.valid(value));
    }
}
