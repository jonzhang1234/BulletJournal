package cs3500.pa05.model;

/**
 * Represents the days of the week, indexed by start of the week
 */
public enum DayOfTheWeek {
  SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6);
  public final int dayIdx;

  /**
   * Constructor for day of the week
   *
   * @param idx index of the day
   */
  DayOfTheWeek(int idx) {
    this.dayIdx = idx;
  }

}
