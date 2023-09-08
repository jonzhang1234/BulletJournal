package cs3500.pa05.model;

import cs3500.pa05.controller.HyperlinkController;
import java.time.LocalTime;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Represents an Event in a Bullet Journal
 */
public class Event extends AbstractJournalItem {
  private final LocalTime startTime;
  private final LocalTime duration;

  /**
   * represents an Event
   *
   * @param name the name of the event
   * @param description the description of the event
   * @param day the day of the week
   * @param startTime the start time of the event
   * @param duration the duration of the event
   */
  public Event(String name, String description, DayOfTheWeek day, LocalTime startTime,
               LocalTime duration) {
    super(name, description, day);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * converts the Event into Json form
   *
   * @return the Json
   */
  @Override
  public ItemJson convertToJson() {
    return new ItemJson(ItemType.EVENT, super.name,
        super.description, super.day, null, this.startTime.toString(), this.duration.toString());
  }

  /**
   * converts the Event into a formatted TextFlow
   *
   * @return the TextFlow
   */
  @Override
  public TextFlow convertToTextFlow() {
    TextFlow textFlow = new TextFlow();
    Text name = new Text(super.name + "\n");
    name.setFont(Font.font("SansSerif", FontWeight.BOLD, 13));
    TextFlow desc = HyperlinkController.getHyperlink(super.description);
    Text start = new Text("\nStarts @: " + this.startTime.toString() + "\n");
    Text duration = new Text("Duration: " + this.duration.toString());
    textFlow.getChildren().add(name);
    textFlow.getChildren().add(desc);
    textFlow.getChildren().add(start);
    textFlow.getChildren().add(duration);
    return textFlow;
  }

}
