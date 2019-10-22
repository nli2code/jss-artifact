package cn.edu.pku.hcst.kincoder.common.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

@UtilityClass
public class CollectionUtil {
    public <T> List<T> concat(List<? extends T> list1, List<? extends T> list2) {
        return ImmutableList.<T>builder()
            .addAll(list1)
            .addAll(list2)
            .build();
    }

    public <T, K extends T> List<T> cons(List<? extends T> list1, K value) {
        return ImmutableList.<T>builder()
            .addAll(list1)
            .add(value)
            .build();
    }

    public <K, V> Map<K, V> cons(Map<K, V> map, K key, V value) {
        return ImmutableMap.<K, V>builder()
            .putAll(map)
            .put(key, value)
            .build();
    }
}
