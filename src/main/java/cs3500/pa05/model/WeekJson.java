package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a Week in Json form
 *
 * @param name the name of the week
 * @param items the items of the week
 * @param maxTasks the max tasks in the week
 * @param maxEvents the max events in the week
 * @param tasks the tasks of the week
 * @param password the password for the week
 */
public record WeekJson(@JsonProperty("weekName") String name,
                       @JsonProperty("days") DayJson[] items,
                       @JsonProperty("maxTasks") int maxTasks,
                       @JsonProperty("maxEvents") int maxEvents,
                       @JsonProperty("tasks") ItemJson[] tasks,
                       @JsonProperty("password") String password,
                       @JsonProperty("orientation") int orientation) {

  /**
   * converts JsonWeek into a week
   *
   * @return the week
   */
  public Week convertToWeek() {
    List<Day> days = new ArrayList<>();
    for (DayJson d : this.items) {
      days.add(d.convertToDay());
    }
    List<Task> tasks = new ArrayList<>();
    for (Day d : days) {
      for (AbstractJournalItem item : d.getItems()) {
        if (item instanceof Task) {
          tasks.add((Task) item);
        }
      }
    }
    return new Week(this.name, days, this.maxTasks, this.maxEvents, tasks, this.password,
        this.orientation);
  }
}
