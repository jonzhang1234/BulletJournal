package cs3500.pa05.model;

import javafx.scene.text.TextFlow;

/**
 * Represents an Item in a Journal
 */
public abstract class AbstractJournalItem {
  protected final String name;
  protected final String description;
  protected  final DayOfTheWeek day;

  /**
   * Constructor fhr the AbstractJournalItem
   *
   * @param name the name of the item
   * @param description the description of the item
   * @param day the day this item occurs on
   */
  AbstractJournalItem(String name, String description, DayOfTheWeek day) {
    this.name = name;
    this.description = description;
    this.day = day;
  }

  /**
   * gets the day of the week this item occurs on
   *
   * @return the day of the week
   */
  public DayOfTheWeek getDay() {
    return this.day;
  }

  /**
   * converts this item into a JSON
   *
   * @return the json
   */
  public abstract ItemJson convertToJson();

  /**
   * converts this item into a formatted TextFlow
   *
   * @return the TextFlow
   */
  public abstract TextFlow convertToTextFlow();


}
