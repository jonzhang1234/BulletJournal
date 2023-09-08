package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Day in json form
 *
 * @param day the day of the week
 * @param items the items the day has
 * @param taskCount the number of tasks in the day
 * @param eventCount the number of events in the day
 * @param completedTasks the number of completed tasks in the day
 */
public record DayJson(@JsonProperty("dayOfTheWeek") DayOfTheWeek day,
                      @JsonProperty("items") ItemJson[] items,
                      @JsonProperty("taskCount") int taskCount,
                      @JsonProperty("eventCount") int eventCount,
                      @JsonProperty("completedTasks") int completedTasks) {

  /**
   * converts the DayJson into an actual Day object
   *
   * @return the Day object
   */
  public Day convertToDay() {
    List<AbstractJournalItem> res = new ArrayList<>();
    for (ItemJson d : this.items) {
      res.add(d.convertToJournalItem());
    }
    return new Day(this.day, res, this.taskCount, this.eventCount, this.completedTasks);
  }
}
