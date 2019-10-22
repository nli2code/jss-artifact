package cn.edu.pku.hcst.kincoder.core.rules;

@FunctionalInterface
public interface Rule<T> {
    boolean valid(T value);
}
