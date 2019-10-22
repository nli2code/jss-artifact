package cn.edu.pku.hcst.kincoder.common.collection;

import cn.edu.pku.hcst.kincoder.common.utils.Pair;

public interface ImmutableStack<T> {
    ImmutableStack<T> push(T value);

    T peek();

    Pair<T, ImmutableStack<T>> pop();

    static <T> ImmutableStack<T> of() {
        return new Empty<>();
    }

    class Empty<T> implements ImmutableStack<T> {
        @Override
        public ImmutableStack<T> push(T value) {
            return new Cons<>(value, this);
        }

        @Override
        public T peek() {
            return null;
        }

        @Override
        public Pair<T, ImmutableStack<T>> pop() {
            return Pair.of(null, this);
        }
    }

    class Cons<T> implements ImmutableStack<T> {
        private final T head;
        private final ImmutableStack<T> tail;

        private Cons(T head, ImmutableStack<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public ImmutableStack<T> push(T value) {
            return new Cons<>(value, this);
        }

        @Override
        public T peek() {
            return head;
        }

        @Override
        public Pair<T, ImmutableStack<T>> pop() {
            return Pair.of(head, tail);
        }
    }
}
