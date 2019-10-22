package graph;

import de.parsemis.graph.Graph;
import de.parsemis.miner.general.Fragment;
import de.parsemis.miner.general.Frequency;
import entities.graph.GraphInfo;
import entities.graph.NodeInfo;
import utils.ObjectIO;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrequentGraphPostRunner implements Runnable {
  public static int							THREAD_LIMIT				= 1024;
  public static int							TIME_UNIT					= 20000;

  private static long							time						= System
          .currentTimeMillis();
  private static int							liveThreadCount				= THREAD_LIMIT;
  private static int							readIndex					= 0;

  private static String						libraryName;
  private static int							validGraphCount				= 0;
  private static List<GraphInfo>				frequentSubgraphs			= new ArrayList<>();
  private static HashMap<String, Integer>		graphUUIDToFrequencyMap		= new HashMap<>();
  private static HashMap<Integer, Integer>	frequencyDistributionMap	= new HashMap<>();

  public static synchronized void stopThread() {
    liveThreadCount--;
  }

  public static void reset(String _libraryName) {
    libraryName = _libraryName;
    validGraphCount = 0;
    frequentSubgraphs = new ArrayList<>();
    graphUUIDToFrequencyMap = new HashMap<>();
    frequencyDistributionMap = new HashMap<>();

    liveThreadCount = THREAD_LIMIT;
    readIndex = 0;
    time = System.currentTimeMillis();
  }

  @Override
  public void run() {
    Fragment<NodeInfo, Integer> nextFragment;
    while ((nextFragment = getNext()) != null) {
      Frequency freq = nextFragment.frequency();
      Graph<NodeInfo, Integer> subgraph = nextFragment.toGraph();

      GraphInfo graphInfo = GraphParser.parseGraphAndValidate(subgraph);

      if (graphInfo != null) {
        frequentSubgraphs.add(graphInfo);

        int frequency = Integer.parseInt(freq.toString());
        if (frequency > 0 && graphInfo.getUuid() != null)
          graphUUIDToFrequencyMap.put(graphInfo.getUuid(), new Integer(frequency));
        else
          System.err.println("ERROR: " + frequency + "\t" + graphInfo.getUuid());

        try {
          if (frequencyDistributionMap.get(frequency) == null)
            frequencyDistributionMap.put(frequency, new Integer(1));
          else
            frequencyDistributionMap.put(frequency,
                    new Integer(frequencyDistributionMap.get(frequency) + 1));
        }
        catch (Exception e) {
          System.err.println(frequency);
          System.err.println(frequencyDistributionMap.get(frequency));
          e.printStackTrace();
        }
        validGraphCount++;
      }
    }
    stopThread();
  }

  public static List<GraphInfo> filter(String _libraryName) {
    FrequentGraphPostRunner.reset(_libraryName);

    FrequentGraphPostRunner filters[] = new FrequentGraphPostRunner[THREAD_LIMIT];
    for (int i = 0; i < THREAD_LIMIT; i++) {
      filters[i] = new FrequentGraphPostRunner();

      Thread thread = new Thread(filters[i], "graph-filter" + i);
      thread.start();
    }
    while (true) {
      if (liveThreadCount <= 0) {
        long t_mid = System.currentTimeMillis();
        System.out.println("[" + ((t_mid - time) / (float) 1000) + "s] " + validGraphCount
                + " valid graphs have been filtered and outputed.");
        String graphListFile = ObjectIO.FREQUENT_SUBGRAPH_LIBRARY + File.separator
                + ObjectIO.FREQUENCY_GRAPH_LIST_PREFIX + libraryName
                + ObjectIO.DAT_FILE_EXTENSION;
        ObjectIO.writeObject(frequentSubgraphs, graphListFile);

        String freqFile = ObjectIO.FREQUENT_SUBGRAPH_LIBRARY + File.separator
                + ObjectIO.FREQUENCY_FILE_PREFIX + libraryName
                + ObjectIO.DAT_FILE_EXTENSION;
        ObjectIO.writeMapToFile(graphUUIDToFrequencyMap, freqFile);

        String uuidFreqMapFile = ObjectIO.FREQUENT_SUBGRAPH_LIBRARY + File.separator
                + ObjectIO.FREQUENCY_MAP_PREFIX + libraryName + ObjectIO.DAT_FILE_EXTENSION;
        ObjectIO.writeObject(graphUUIDToFrequencyMap, uuidFreqMapFile);

        Object mapObj = ObjectIO.readObject(ObjectIO.getDataObjDirectory(uuidFreqMapFile));
        if (mapObj == null) {
          System.out.println("Frequent map invalid, write map to file.." + freqFile);
        }

        System.out.println(frequencyDistributionMap);

        long t_end = System.currentTimeMillis();
        System.out.println("Output \"Frequency\" maps to file: " + (t_end - t_mid) + "ms");

        break;
      }

      try {
        Thread.sleep(TIME_UNIT);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return frequentSubgraphs;
  }

  private static synchronized Fragment<NodeInfo, Integer> getNext() {
    if (FrequentGraphMiner.getFrequentSubgraphs() == null)
      return null;
    Fragment<NodeInfo, Integer> next = null;
    try {
      if (readIndex >= FrequentGraphMiner.getFrequentSubgraphs().size())
        return null;
      next = FrequentGraphMiner.getFrequentSubgraphs().get(readIndex);
      readIndex++;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return next;
  }

  public static String getLibraryName() {
    return libraryName;
  }

  public static void setLibraryName(String libraryName) {
    FrequentGraphPostRunner.libraryName = libraryName;
  }

  public static HashMap<String, Integer> getGraphUUIDToFrequencyMap() {
    return graphUUIDToFrequencyMap;
  }

  public static void setGraphUUIDToFrequencyMap(HashMap<String, Integer> map) {
    graphUUIDToFrequencyMap = map;
  }

  public static HashMap<Integer, Integer> getFrequencyDistributionMap() {
    return frequencyDistributionMap;
  }

  public static void setFrequencyDistributionMap(
          HashMap<Integer, Integer> frequencyDistributionMap) {
    FrequentGraphPostRunner.frequencyDistributionMap = frequencyDistributionMap;
  }

}
