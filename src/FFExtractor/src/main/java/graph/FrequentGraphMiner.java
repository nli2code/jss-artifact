package graph;

import edu.stanford.nlp.trees.Tree;
import entities.ff.PhraseInfo;
import entities.graph.GraphInfo;
import entities.graph.NodeInfo;
import entities.graph.VerbalPhraseStructureInfo;
import filters.PhraseFilter;

import java.text.ParseException;
import java.util.*;

import de.parsemis.graph.Node;

import de.parsemis.Miner;
import de.parsemis.graph.Graph;
import de.parsemis.graph.ListGraph;
import de.parsemis.miner.environment.Settings;
import de.parsemis.miner.environment.Statistics;
import de.parsemis.miner.general.Fragment;
import de.parsemis.miner.general.IntFrequency;
import de.parsemis.parsers.LabelParser;
import de.parsemis.strategy.ThreadedDFSStrategy;
import parser.NLPParser;
import parser.PhraseExtractor;
import parser.Stemmer;
import parser.XMLParser;
import utils.GraphUtils;

import static utils.TextUtils.splitText;

public class FrequentGraphMiner {

  private static String								libraryName;
  private static List<Graph<NodeInfo, Integer>> graphs				= new ArrayList<>();
  private static List<Fragment<NodeInfo, Integer>>	frequentSubgraphs	= new ArrayList<>();
  private static List<GraphInfo> obj = new ArrayList<>();
  private static double graphFreqThreshold = 1.0;


  public static void reset(String _libraryName) {
    for (Fragment<NodeInfo, Integer> fragment : frequentSubgraphs) {
      fragment.clear();
    }
    graphs = new ArrayList<>();
    frequentSubgraphs = new ArrayList<>();
    libraryName = _libraryName;
  }

  public static void main(String[] args) {
    String body = "<pre><code>u should not see this!</code></pre>" +
            "<p>I want to set cell color to red. That's all, thank you.</p>" +
            "<p>I want to set cell color to red. That's all, thank you.</p>";
    String text = XMLParser.parseForText(body);
    List<String> sentences = splitText(text);
    for (String sentence: sentences) {
      Tree tree = NLPParser.parseGrammaticalTree(sentence);
      PhraseInfo[] verbPhrases = PhraseExtractor.extractVerbPhrases(tree);
      for (PhraseInfo phraseInfo : verbPhrases) {
        PhraseFilter.filter(phraseInfo, sentence);
        if (phraseInfo.getProofScore() > 0) {
          Tree stemmedPhraseTree = Stemmer.stemTree(Tree.valueOf(phraseInfo.getSyntaxTree()));
          VerbalPhraseStructureInfo vp = new VerbalPhraseStructureInfo(stemmedPhraseTree);
          GraphInfo graphInfo = GraphBuilder.buildGraphInfoFromStructure(vp);
          obj.add(graphInfo);
          Graph<NodeInfo, Integer> g = GraphBuilder.convertToParsemisGraph(graphInfo);
          Iterator<Node<NodeInfo, Integer>> ite = g.nodeIterator();
          while (ite.hasNext()) {
            Node<NodeInfo, Integer> n = ite.next();
          }
        }
      }
    }

    if (obj != null && obj instanceof List<?>) {
      System.out.println("******************************************************************");
      System.out.println(obj.size());
      System.out.println(obj);
      List<GraphInfo> graphList = obj;
      for (GraphInfo graphInfo : graphList) {
        Graph<NodeInfo, Integer> graph = GraphBuilder.convertToParsemisGraph(graphInfo);
        if (graph != null) {
          graphs.add(graph);
        }
      }
      System.out.println("Start to mine frequent subgraphs from [" + graphs.size() + "] graphs...");

      mineFrequentGraph();
      graphs = null;

      System.out.println("frequent sub-graphs");
      System.out.println(frequentSubgraphs.size());

      List<GraphInfo> frequentSubgraphsFromFilter = FrequentGraphPostRunner.filter(libraryName);
      System.out.println("frequentSubgraphsFromFilter");
      System.out.println(frequentSubgraphsFromFilter.size());
      int cnt = 0;
      for (GraphInfo graphInfo: frequentSubgraphsFromFilter) {
        VerbalPhraseStructureInfo vp = GraphParser.parseGraphToPhraseStructure(graphInfo);
        if (vp != null) {
          System.out.println(vp);
          cnt++;
        }
      }
      System.out.println("*************************");
      System.out.println(cnt);
    }
  }

  public static List<Fragment<NodeInfo, Integer>> mineFrequentGraph() {
    for (int i = 0; i < graphs.size(); i++) {
      Graph<NodeInfo, Integer> g = graphs.get(i);
      if (g == null) {
        System.out.println("ERROR_NULL_GRAPH!!! INDEX: " + i + "\t" + graphs.get(i));
        FrequentGraphMiner.getGraphs().remove(i);
        i--;
      }
    }
    Settings<NodeInfo, Integer> settings = new Settings<>();
    settings.threadCount = 16;
    settings.naturalOrderedNodeLabels = true;
    settings.algorithm = new de.parsemis.algorithms.gSpan.Algorithm<>();
    settings.strategy = new ThreadedDFSStrategy<>(16, new Statistics());
    System.out.println("number of graph mined: " + graphs.size()
        + ", threshold of number to be frequent: " + (int) (graphs.size() * graphFreqThreshold));
    settings.minFreq = new IntFrequency((int) (graphs.size() * graphFreqThreshold));
    settings.factory = new ListGraph.Factory<>(new NodeInfoLabelParser(), new IntegerLabelParser());

    Collection<Fragment<NodeInfo, Integer>> result = Miner.mine(graphs, settings);

    frequentSubgraphs.addAll(result);
    return frequentSubgraphs;
  }

  public static String getLibraryName() {
    return libraryName;
  }

  public static void setLibraryName(String libraryName) {
    FrequentGraphMiner.libraryName = libraryName;
  }

  public synchronized static List<Graph<NodeInfo, Integer>> getGraphs() {
    return graphs;
  }

  public static void setGraphs(List<Graph<NodeInfo, Integer>> graphs) {
    FrequentGraphMiner.graphs = graphs;
  }

  public static List<Fragment<NodeInfo, Integer>> getFrequentSubgraphs() {
    return frequentSubgraphs;
  }

  public static void setFrequentSubgraphs(List<Fragment<NodeInfo, Integer>> frequentSubgraphs) {
    FrequentGraphMiner.frequentSubgraphs = frequentSubgraphs;
  }
}

class NodeInfoLabelParser implements LabelParser<NodeInfo> {
  private static final long serialVersionUID = 8844206741183890442L;

  @Override
  public NodeInfo parse(String s) throws ParseException {
    return GraphUtils.parseStringToNodeInfo(s);
  }

  @Override
  public String serialize(NodeInfo node) {
    return node.toString();
  }
}

class IntegerLabelParser implements LabelParser<Integer> {
  private static final long serialVersionUID = 4752511081764291545L;

  @Override
  public Integer parse(String s) throws ParseException {
    return Integer.parseInt(s);
  }

  @Override
  public String serialize(Integer integer) {
    return integer.toString();
  }
}