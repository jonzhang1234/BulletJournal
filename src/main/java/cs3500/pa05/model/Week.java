package cs3500.pa05.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * represents a Week
 */
public class Week {
  private String name;
  private final List<Day> daysOfTheWeek;
  private int maxEvents;
  private int maxTasks;
  private final List<Task> tasks;
  private String password;
  private final int orientation;

  /**
   * constructor for a default week
   *
   * @param name the name of the week
   */
  public Week(String name, int orientation) {
    this.name = name;
    this.daysOfTheWeek = new ArrayList<>();
    this.initDays(this.daysOfTheWeek);
    this.tasks = new ArrayList<>();
    this.maxEvents = -1;
    this.maxTasks = -1;
    this.password = "";
    this.orientation = orientation;
  }

  /**
   * customizable constructor for Week
   *
   * @param name the name of the Week
   * @param days the days in the week
   * @param maxTasks the max tasks set for the week
   * @param maxEvents the max events set for the week
   * @param tasks all the tasks in the week
   * @param password the password for the week
   */
  public Week(String name, List<Day> days, int maxTasks, int maxEvents, List<Task> tasks,
              String password, int orientation) {
    this.name = name;
    this.daysOfTheWeek = days;
    this.maxEvents = maxEvents;
    this.maxTasks = maxTasks;
    this.tasks = tasks;
    this.password = password;
    this.orientation = orientation;
  }

  /**
   * sets the password for the week
   *
   * @param s the password
   */
  public void setPassword(String s) {
    this.password = Objects.requireNonNullElse(s, "");
  }

  /**
   * returns the name for the week
   *
   * @return name of the week
   */
  public String getName() {
    return this.name;
  }

  /**
   * initializes the days of the week
   *
   * @param daysOfTheWeek days to add
   */
  private void initDays(List<Day> daysOfTheWeek) {
    daysOfTheWeek.add(new Day(DayOfTheWeek.SUNDAY, new ArrayList<>()));
    daysOfTheWeek.add(new Day(DayOfTheWeek.MONDAY, new ArrayList<>()));
    daysOfTheWeek.add(new Day(DayOfTheWeek.TUESDAY, new ArrayList<>()));
    daysOfTheWeek.add(new Day(DayOfTheWeek.WEDNESDAY, new ArrayList<>()));
    daysOfTheWeek.add(new Day(DayOfTheWeek.THURSDAY, new ArrayList<>()));
    daysOfTheWeek.add(new Day(DayOfTheWeek.FRIDAY, new ArrayList<>()));
    daysOfTheWeek.add(new Day(DayOfTheWeek.SATURDAY, new ArrayList<>()));
  }


  /**
   * adds item to the week
   *
   * @param item the item to add
   * @param type the type of item
   *
   * @return true if item exceeds commitment level
   *
   */
  public boolean addItem(AbstractJournalItem item, ItemType type) {
    Day specificDay = daysOfTheWeek.get(item.getDay().dayIdx);
    specificDay.addItem(item, type);
    if (type == ItemType.TASK) {
      return specificDay.exceedLevel(this.maxTasks, type);
    } else {
      return specificDay.exceedLevel(this.maxEvents, type);
    }
  }

  /**
   * sets the name of the week
   *
   * @param s the name to set the week to
   */
  public void setName(String s) {
    this.name = s;
  }

  /**
   * converts the Week into json
   *
   * @param template true whether we want to use it as a template
   * @return the json form
   */
  public WeekJson convertToJson(boolean template) {
    DayJson[] days = new DayJson[daysOfTheWeek.size()];
    ItemJson[] tasksArr = new ItemJson[this.tasks.size()];
    if (template) {
      List<Day> daysList = new ArrayList<>();
      this.initDays(daysList);
      for (int i = 0; i < daysList.size(); i += 1) {
        days[i] = daysList.get(i).convertToJson();
      }
    } else {
      for (int i = 0; i < days.length; i += 1) {
        days[i] = daysOfTheWeek.get(i).convertToJson();
      }
      for (int i = 0; i < tasksArr.length; i += 1) {
        tasksArr[i] = this.tasks.get(i).convertToJson();
      }
    }
    return new WeekJson(this.name, days, this.maxTasks, this.maxEvents, tasksArr,
        this.password, this.orientation);
  }

  /**
   * gets the days of the week
   *
   * @return the day
   */
  public List<Day> getDays() {
    return this.daysOfTheWeek;
  }

  /**
   * gets the weekly statistics
   *
   * @return the weekly statistics
   */
  public double totalTaskProgress() {
    double sum = 0.0;
    int total = 0;
    for (Day d : this.daysOfTheWeek) {
      sum += d.getCompletedTasks();
      total += d.getTotalTasks();
    }
    return sum / total;
  }

  /**
   * adds a task to the week
   *
   * @param t the task to add
   */
  public void addTask(Task t) {
    this.tasks.add(t);
  }

  /**
   * gets the commitment level of a specific item type
   *
   * @param type the type of item
   * @return the commitment level
   */
  public String getCommitmentLevel(ItemType type) {
    if (type == ItemType.TASK) {
      if (this.maxTasks == -1) {
        return "None";
      } else {
        return String.valueOf(this.maxTasks);
      }
    } else {
      if (this.maxEvents == -1) {
        return "None";
      } else {
        return String.valueOf(this.maxEvents);
      }
    }
  }

  /**
   * combines this week with another week
   *
   * @param w the week to combine with
   */
  public void combineWeek(Week w) {
    for (int i = 0; i < this.daysOfTheWeek.size(); i += 1) {
      this.daysOfTheWeek.get(i).combineDay(w.daysOfTheWeek.get(i));
    }
    this.maxTasks = w.maxTasks;
    this.maxEvents = w.maxEvents;
    this.name = w.name;
    this.tasks.addAll(w.tasks);
    this.password = w.password;
  }

  /**
   * sets the commitment level of the week
   *
   * @param level the level to set it to
   * @param type the type of item to set the level for
   */
  public void setCommitmentLevel(int level, ItemType type) {
    if (type == ItemType.TASK) {
      this.maxTasks = level;
    } else {
      this.maxEvents = level;
    }
  }

  /**
   * removes a task from the week
   *
   * @param t the task to remove
   * @return the index of the task
   */
  public int removeTask(Task t) {
    int idx = this.tasks.indexOf(t);
    this.tasks.remove(t);
    return idx;
  }
}
