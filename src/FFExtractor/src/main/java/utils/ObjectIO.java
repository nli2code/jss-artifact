package utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ObjectIO {

  public static final String	DAT_FILE_EXTENSION				= ".dat";
  public static final String	DAT_FILE_NAME					= "threads";
  public static final String	FREQUENCY_MAP_PREFIX			= "[FrequencyMap]";
  public static final String	FREQUENCY_DISTRIBUTION_PREFIX	= "[FrequencyDistribution]";
  public static final String	FREQUENCY_FILE_PREFIX			= "[FreqFile]";
  public static final String	FREQUENCY_GRAPH_LIST_PREFIX		= "[FreqGraphList]";
  public static final String	SOCORPUS						= "socorpus";
  public static final String	SOTHREAD_LIBRARY				= "sothread_library";
  public static final String	CODE_DATA						= "code_data";
  public static final String	CONTENTPOOL_LIBRARY				= "contentpool_library";
  public static final String	CONTENTPOOL_FROMCOMMENT			= "contentpool_fromcomment";
  public static final String	SENTENCEPOOL_LIBRARY			= "sentencepool_library";
  public static final String	STRUCTURED_SENTENCE_LIBRARY		= "structured_sentence_library";
  public static final String	CONTENT_STRUCTURED_LIBRARY		= "content_structured_library";
  public static final String	PHRASE_LIBRARY					= "phrase_library";
  public static final String	STRUCTURE_LIBRARY				= "structure_library";
  public static final String	GRAPH_LIBRARY					= "graph_library";
  public static final String	FREQUENT_SUBGRAPH				= "frequent_subgraph";
  public static final String	FREQUENT_SUBGRAPH_LIBRARY		= "frequent_subgraph_library";
  public static final String	FREQUENT_SUBGRAPH_STR			= "frequent_subgraph_str";
  public static final String	FREQUENT_SUBGRAPH_STR_LIBRARY	= "frequent_subgraph_str_library";
  public static final String	HIERARCHICAL_FEATURES			= "hierarchical_features";
  public static final String	NEW_HIERARCHICAL_FEATURES		= "new_hierarchical_features";
  public static final String	SUBJECTS						= "subjects";
  public static final String	FEATURE_VERB_PHRASE_STRUCTURE	= "feature_vps";
  public static final String	CODE_MATCHER					= "code_matcher";

  public static final String	CONTENT							= "content";
  public static final String	SOCODE							= "socode";

  public static int			count							= 0;
  public static long			start_clock						= System.currentTimeMillis();
  public static long			clock							= System.currentTimeMillis();

  public static File getFile(String path) {
    File file = new File(path);
    File parentDir = file.getParentFile();
    if (!parentDir.exists())
      parentDir.mkdirs();
    return file;
  }

  public static boolean writeString(String content, String path) {
    try {
      File file = new File(path);
      File parentDir = file.getParentFile();
      if (!parentDir.exists())
        parentDir.mkdirs();

      FileWriter fw = new FileWriter(file);
      fw.write(content);
      fw.flush();
      fw.close();
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean writeObject(Object obj, String path) {
    ObjectOutputStream objOut;
    try {
      File file = new File(path);
      File parentDir = file.getParentFile();
      if (!parentDir.exists())
        parentDir.mkdirs();
      objOut = new ObjectOutputStream(new FileOutputStream(file));
      objOut.writeObject(obj);
      objOut.flush();
      objOut.close();
      objOut = null;
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      objOut = null;
      return false;
    }
    return true;
  }

  public static File getDataObjDirectory(String subDirName) {
    return new File("data-np" + File.separator + subDirName);
  }

  public static Object readObject(String filePath) {
    return readObject(new File(filePath));
  }

  public static Object readObject(File file) {
    ObjectInputStream objIn;
    try {
      if (file == null || !file.exists())
        return null;

      objIn = new ObjectInputStream(new FileInputStream(file));
      Object object = objIn.readObject();
      objIn.close();
      objIn = null;
      return object;
    }
    catch (Exception e) {
      System.out.println("ObjectIO.readObject: " + file.getAbsolutePath());
      e.printStackTrace();
      objIn = null;
      return null;
    }
  }
  public static List<Object> readObjectsFromDir(File dir) {
    List<Object> objList = new ArrayList<>();
    if (dir == null || !dir.exists() || !dir.isDirectory())
      return null;

    try {
      File[] dataFiles = dir.listFiles();
      for (int j = 0; j < dataFiles.length; j++) {
        File file = dataFiles[j];
        Object obj = readObject(file);
        if (obj != null)
          objList.add(obj);
        count++;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return objList;
  }

  public static void writeMapToFile(HashMap<String, Integer> freqMap, String filePath) {
    try {
      File file = new File("data-np" + File.separator + filePath);
      File parentDir = file.getParentFile();
      if (!parentDir.exists())
        parentDir.mkdirs();

      RandomAccessFile raFile = new RandomAccessFile(file, "rw");
      for (Entry<String, Integer> entry : freqMap.entrySet()) {
        if (entry.getKey() != null && entry.getValue() != null)
          raFile.writeBytes(entry.getKey() + "\t" + entry.getValue() + "\n");
      }
      raFile.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
