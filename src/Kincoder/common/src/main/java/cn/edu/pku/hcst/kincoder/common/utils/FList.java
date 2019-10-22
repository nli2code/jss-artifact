package cn.edu.pku.hcst.kincoder.common.utils;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

public interface FList<T> {
    T getHead();

    FList<T> getTail();

    int size();

    List<T> toList();

    static <T> FList<T> nil() {
        return new Nil<>();
    }

    static <T> FList<T> cons(T head, FList<T> tail) {
        return new Cons<>(head, tail);
    }

    static <T> FList<T> of(T value) {
        return new Cons<>(value, nil());
    }

    static <T> FList<T> fromIterator(Iterator<T> ite) {
        if (ite.hasNext()) {
            var head = ite.next();
            var tail = fromIterator(ite);
            return cons(head, tail);
        }
        return nil();
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    class Nil<T> implements FList<T> {

        private Nil() {
        }

        @Override
        public T getHead() {
            throw new RuntimeException("Can not get head from Nil!");
        }

        @Override
        public FList<T> getTail() {
            throw new RuntimeException("Can not get tail from Nil!");
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public List<T> toList() {
            return List.of();
        }
    }

    @Getter
    class Cons<T> implements FList<T> {
        private final T head;
        private final FList<T> tail;
        private final int size;

        private Cons(T head, FList<T> tail) {
            this.head = head;
            this.tail = tail;
            this.size = tail.size() + 1;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public List<T> toList() {
            return ImmutableList.<T>builder()
                .add(head)
                .addAll(tail.toList())
                .build();
        }
    }
}
