package cn.edu.pku.hcst.kincoder.core.rules;

public abstract class NegativeJudger<T> extends Judger<T> {
    @Override
    final public boolean judge(T value) {
        return rules.stream().allMatch(r -> r.valid(value));
    }
}
