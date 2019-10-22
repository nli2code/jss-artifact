package cn.edu.pku.hcst.kincoder.pattern.api;

import de.parsemis.graph.Graph;

import java.util.Collection;

@FunctionalInterface
public interface PatternGenerator<N, E, G, R> {
    R generate(Collection<G> originalGraph, Graph<N, E> graph);
}
