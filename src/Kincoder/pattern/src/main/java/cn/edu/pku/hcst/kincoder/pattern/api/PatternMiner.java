package cn.edu.pku.hcst.kincoder.pattern.api;

import cn.edu.pku.hcst.kincoder.pattern.api.filter.Filter;
import cn.edu.pku.hcst.kincoder.pattern.utils.GraphUtil;
import de.parsemis.graph.Graph;
import de.parsemis.miner.environment.Settings;
import de.parsemis.miner.general.Fragment;
import de.parsemis.parsers.LabelParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class PatternMiner<Data, N, E, G extends Graph<N, E>, R> {
    private final Data source;
    private final GraphGenerator<Data, G> graphGenerator;
    private final PatternGenerator<N, E, G, R> patternGenerator;
    private final LabelParser<N> nodeParser;
    private final LabelParser<E> edgeParser;
    @Nullable
    private final Filter<List<Graph<N, E>>> resultFilter;

    public Collection<R> process(MinerSetting minerSetting) {
        var graphs = graphGenerator.generate(source);
        log.info(String.format("总数据流图数: %d", graphs.size()));
        var freqGraphs = mine(graphs, minerSetting.toParsemisSettings(nodeParser, edgeParser)).stream().map(Fragment::toGraph).collect(Collectors.toList());
        var filtered = resultFilter == null ? freqGraphs : resultFilter.process(freqGraphs);
        log.info(String.format("频繁子图数: %d", filtered.size()));
        filtered.forEach(GraphUtil::print);
        return filtered.stream().map(r -> patternGenerator.generate(graphs, r)).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Collection<Fragment<N, E>> mine(Collection<? extends Graph<N, E>> dfgs, Settings<N, E> settings) {
        return de.parsemis.Miner.mine((Collection<Graph<N, E>>) dfgs, settings);
    }
}
