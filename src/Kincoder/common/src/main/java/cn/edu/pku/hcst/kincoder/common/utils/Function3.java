package cn.edu.pku.hcst.kincoder.common.utils;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function3<T, U, V, R> {
    R apply(T t, U u, V v);

    default <W> Function3<T, U, V, W> andThen(Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}
