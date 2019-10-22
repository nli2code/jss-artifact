package parser;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.RuntimeInterruptedException;

public class StanfordParser implements Callable<Tree> {

  public static LexicalizedParser				lexicalizedParser;
  public static TokenizerFactory<CoreLabel>	tokenizerFactory;

  private String								strToParse;
  private Tree								parsedTree;

  static {
    lexicalizedParser = LexicalizedParser.loadModel("src/main/resources/englishPCFG.ser.gz");
    tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
  }

  public StanfordParser(String strToParse) {
    super();
    this.strToParse = strToParse;
  }

  public static Tree parseTree(String str) {
    StanfordParser parser = new StanfordParser(str);
    FutureTask<Tree> parserTask = new FutureTask<Tree>(parser);
    ThreadGroup stanfordParserThreadGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(),
            "Stanford-Parser-TG");
    Thread parserThread = new Thread(stanfordParserThreadGroup, parserTask);

    parserThread.start();

    Tree resultTree;
    try {
      resultTree = parserTask.get();
    }
    catch (Exception e) {
      resultTree = null;
    }

    return resultTree;
  }

  @Override
  public Tree call() {
    this.parseTree();
    return parsedTree;
  }

  private void parseTree() {
    try {
      List<CoreLabel> rawWords = tokenizerFactory.getTokenizer(new StringReader(strToParse)).tokenize();
      this.parsedTree = lexicalizedParser.apply(rawWords);
    }
    catch (RuntimeInterruptedException e) {}
  }

  @Deprecated
  public static Tree parseTreeWithoutMonitoring(String str) {
    List<CoreLabel> rawWords = tokenizerFactory.getTokenizer(new StringReader(str)).tokenize();
    Tree tree = lexicalizedParser.apply(rawWords);
    return tree;
  }

}