package cn.edu.pku.hcst.kincoder.pattern.api.filter;

import java.util.List;
import java.util.stream.Collectors;

public interface ListFilter<T> extends Filter<List<T>> {
    boolean valid(T value);

    @Override
    default List<T> process(List<T> input) {
        return input.stream().filter(this::valid).collect(Collectors.toList());
    }
}
