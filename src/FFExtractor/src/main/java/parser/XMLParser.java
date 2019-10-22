package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class XMLParser {

  public static String parseForText(String item) {
    Document doc = Jsoup.parse(item);
    doc.select("code").remove();
    String text = doc.text();
    return text;
  }

}
