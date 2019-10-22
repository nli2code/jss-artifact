package cn.edu.pku.hcst.kincoder.pattern.api.filter;

import cn.edu.pku.hcst.kincoder.pattern.api.Pipe;

import java.util.Collection;
import java.util.function.Consumer;

public interface Filter<T> extends Pipe<T, T> {
    default Filter<T> connect(Filter<T> other) {
        return input -> other.process(process(input));
    }

    static <V> Filter<V> id() {
        return input -> input;
    }

    static <V> Filter<V> peek(Consumer<V> func) {
        return input -> {
            func.accept(input);
            return input;
        };
    }

    @SuppressWarnings("unchecked")
    static <V, F extends Filter<V>> F reduce(Collection<F> filters) {
        return filters.stream().reduce((F) id(), (f1, f2) -> (F) f1.connect(f2));
    }
}
