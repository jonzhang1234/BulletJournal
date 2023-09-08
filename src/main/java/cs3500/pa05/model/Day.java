package cs3500.pa05.model;

import java.util.List;

/**
 * Represents a Day
 */
public class Day {
  private final DayOfTheWeek day;
  private final List<AbstractJournalItem> items;
  private int taskCount;
  private int eventCount;
  private int completedTasks;

  /**
   * Constructor for Day
   *
   * @param day the day of the week
   * @param items the items in the day
   */
  public Day(DayOfTheWeek day, List<AbstractJournalItem> items) {
    this.day = day;
    this.items = items;
    this.taskCount = 0;
    this.eventCount = 0;
    this.completedTasks = 0;
  }

  /**
   * Constructor for day
   *
   * @param day the day of the week
   * @param items the items in the week
   * @param taskCount the number of tasks in the day
   * @param eventCount the number of events in the day
   * @param completedTasks the number of completed tasks in the day
   */
  public Day(DayOfTheWeek day, List<AbstractJournalItem> items, int taskCount, int eventCount,
             int completedTasks) {
    this.day = day;
    this.items = items;
    this.taskCount = taskCount;
    this.eventCount = eventCount;
    this.completedTasks = completedTasks;
  }

  /**
   * Adds an item to the day
   *
   * @param item the item to add
   * @param type the item type
   */
  void addItem(AbstractJournalItem item, ItemType type) {
    this.items.add(item);
    if (type == ItemType.TASK) {
      taskCount += 1;
    } else {
      eventCount += 1;
    }
  }

  /**
   * converts the Day into Json form
   *
   * @return the json form of the day
   */
  DayJson convertToJson() {
    return new DayJson(this.day, ItemJson.convertToJson(items), this.taskCount,
        this.eventCount, this.completedTasks);
  }

  /**
   * gets the items of the day
   *
   * @return the items of the day
   */
  public List<AbstractJournalItem> getItems() {
    return this.items;
  }

  /**
   * combines this day with another
   *
   * @param d the day to combine with
   */
  void combineDay(Day d) {
    this.items.addAll(d.items);
    this.taskCount = d.taskCount;
    this.eventCount = d.eventCount;
    this.completedTasks = d.completedTasks;
  }

  /**
   * determines if the day has exceeded the max level of set commitment
   *
   * @param level the level of commitment
   * @param type the type of item
   * @return true if exceeded, false if not
   */
  public boolean exceedLevel(int level, ItemType type) {
    if (type == ItemType.TASK) {
      return this.taskCount > level && level != -1;
    } else {
      return this.eventCount > level && level != -1;
    }
  }

  /**
   * marks a task as complete/incomplete
   *
   * @param up true if complete, else incomplete
   */
  public void markTask(boolean up) {
    if (up) {
      this.completedTasks += 1;
    } else {
      this.completedTasks -= 1;
    }
  }

  /**
   * gets the total number of tasks in the day
   *
   * @return the total number of tasks
   */
  public int getTotalTasks() {
    return this.taskCount;
  }

  /**
   * gets the number of completed tasks in the day
   *
   * @return the number of completed tasks
   */
  public int getCompletedTasks() {
    return this.completedTasks;
  }

  /**
   * the day in string form
   *
   * @return a string of the day
   */
  public String getDay() {
    return this.day.toString();
  }

  /**
   * removes an item from the day
   *
   * @param idx the index of the item
   * @return the task that was removed
   */
  public Task removeItem(int idx) {
    AbstractJournalItem item = this.items.remove(idx);
    Task t = null;
    if (item instanceof Task) {
      t = (Task) item;
      if (t.isComplete()) {
        this.completedTasks -= 1;
      }
      this.taskCount -= 1;
    }
    return t;
  }
}
