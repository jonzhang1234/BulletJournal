package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents an Item in Json form
 *
 * @param type the type of item
 * @param name the name of the item
 * @param desc the description of the item
 * @param day the day of the item
 * @param isComplete the completion status of the item
 * @param start the start time of the item
 * @param duration the duration of the item
 */
public record ItemJson(@JsonProperty("itemType") ItemType type,
                       @JsonProperty("itemName") String name,
                       @JsonProperty("itemDescription") String desc,
                       @JsonProperty("day") DayOfTheWeek day,
                        @JsonProperty("taskComplete") Boolean isComplete,
                       @JsonProperty("eventStart") String start,
                       @JsonProperty("eventDuration") String duration) {

  /**
   * converts the item to json
   *
   * @param items the items to convert
   * @return the array of converted item jsons
   */
  public static ItemJson[] convertToJson(List<AbstractJournalItem> items) {
    ItemJson[] res = new ItemJson[items.size()];
    for (int i = 0; i < res.length; i += 1) {
      res[i] = items.get(i).convertToJson();
    }
    return res;
  }

  /**
   * converts the item json into a JournalItem
   *
   * @return the journal item
   */
  public AbstractJournalItem convertToJournalItem() {
    if (this.type == ItemType.TASK) {
      return new Task(this.name, this.desc, this.day, this.isComplete);
    } else {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
      LocalTime startTime = LocalTime.parse(this.start, formatter);
      LocalTime duration = LocalTime.parse(this.duration, formatter);
      return new Event(this.name, this.desc, this.day, startTime, duration);
    }
  }
}
