package cs3500.pa05.controller;

import cs3500.pa05.JournalLauncher;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Represents a class for URL Parsing into a FX Hyperlink
 */
public class HyperlinkController {
  /**
   * given a string of text, converts the urls into hyperlinks and the strings into Text
   *
   * @param description the text to parse
   * @return the parsed urls
   */
  public static TextFlow getHyperlink(String description) {
    int left = 0;
    int right = 0;
    TextFlow desc = new TextFlow();
    boolean add = false;
    while (right < description.length() && description.indexOf("http", right) != -1) {
      right = description.indexOf("http", right);
      int end = description.indexOf(" ", right);
      if (end == -1) {
        String url = description.substring(right);
        Hyperlink h = new Hyperlink(url);
        desc.getChildren().add(h);
        h.setOnAction(e -> new JournalLauncher().getHostServices().showDocument(url));
        add =  true;
        break;
      }
      if (end <= description.length() && isValidUrl(description
          .substring(right, end), right) != -1) {
        desc.getChildren().add(new Text(description.substring(left, right)));
        String url = description.substring(right, end);
        left = end;
        Hyperlink h = new Hyperlink(url);
        h.setOnAction(e -> new JournalLauncher().getHostServices().showDocument(url));

        desc.getChildren().add(h);
      } else {
        if (end >= description.length()) {
          break;
        }
      }
      right = left;
    }
    if (right < description.length() && !add) {
      desc.getChildren().add(new Text(description.substring(right)));
    }
    return desc;
  }

  /**
   * determines if the url is a valid url
   *
   * @param url the url to determine
   * @param idx the index of the url start
   *
   * @return the index where the url begins
   */
  private static int isValidUrl(String url, int idx)  {
    try {
      new URL(url).toURI();
      return idx;
    } catch (MalformedURLException | URISyntaxException e) {
      return -1;
    }
  }


}
