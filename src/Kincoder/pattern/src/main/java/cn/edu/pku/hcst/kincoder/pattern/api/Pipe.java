package cn.edu.pku.hcst.kincoder.pattern.api;

@FunctionalInterface
public interface Pipe<In, Out> {
    Out process(In input);

    default <Out2> Pipe<In, Out2> connect(Pipe<Out, Out2> other) {
        return input -> other.process(process(input));
    }
}
