package cn.edu.pku.hcst.kincoder.pattern.api;

import de.parsemis.graph.ListGraph.Factory;
import de.parsemis.miner.environment.Settings;
import de.parsemis.miner.environment.Statistics;
import de.parsemis.miner.general.IntFrequency;
import de.parsemis.parsers.LabelParser;
import de.parsemis.strategy.ThreadedDFSStrategy;
import lombok.Builder;

@Builder
public class MinerSetting {
    @Builder.Default private int minFreq = 5;
    @Builder.Default private int minNodes = 7;
    @Builder.Default private boolean closeGraph = true;
//    def create[N, E](nodeParser: LabelParser[N], edgeParser: LabelParser[E], minFreq: Int = 3, minNodes: Int = 7,
//    closeGraph: Boolean = true): Settings[N, E] = {
//        val s = new Settings[N, E]()
//        s.minNodes = minNodes
//        s.minFreq = new IntFrequency(minFreq)
//        s.algorithm = new de.parsemis.algorithms.gSpan.Algorithm[N, E]()
//        s.threadCount = 8
//        s.strategy = new ThreadedDFSStrategy[N, E](8, new Statistics)
//        s.factory = new ListGraph.Factory[N, E](nodeParser, edgeParser)
//        s.closeGraph = closeGraph
//        s
//    }

    public <N, E> Settings<N, E> toParsemisSettings(LabelParser<N> nodeParser, LabelParser<E> edgeParser) {
        var s = new Settings<N, E>();
        s.minNodes = minNodes;
        s.minFreq = new IntFrequency(minFreq);
        s.algorithm = new de.parsemis.algorithms.gSpan.Algorithm<>();
        s.threadCount = 8;
        s.strategy = new ThreadedDFSStrategy<>(8, new Statistics());
        s.factory = new Factory<>(nodeParser, edgeParser);
        s.closeGraph = closeGraph;
        return s;
    }
}
