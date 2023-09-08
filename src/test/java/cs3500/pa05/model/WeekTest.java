package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for week test
 */
class WeekTest {
  private Week week;

  /**
   * Set up before testing
   */
  @BeforeEach
  void setUp() {
    week = new Week("test", 0);
  }

  /**
   * Test for setting password
   */
  @Test
  void setPassword() {
    this.week.setPassword("pw");
    WeekJson js = this.week.convertToJson(false);
    assertEquals("pw", js.password());
  }

  /**
   * Test for get name
   */
  @Test
  void getName() {
    assertEquals("test", this.week.getName());
  }

  /**
   * Test for initDays
   */
  @Test
  void initDays() {

  }

  /**
   * Test for add items
   */
  @Test
  void addItem() {
    assertFalse(week.addItem(
        new Task("task1", "desc", DayOfTheWeek.MONDAY), ItemType.TASK));
    week.setCommitmentLevel(1, ItemType.TASK);
    assertTrue(week.addItem(
        new Task("task2", "desc", DayOfTheWeek.MONDAY), ItemType.TASK));
    assertFalse(week.addItem(
        new Task("task2", "desc", DayOfTheWeek.MONDAY), ItemType.EVENT));
    week.setCommitmentLevel(0, ItemType.EVENT);
    assertTrue(week.addItem(
        new Task("task2", "desc", DayOfTheWeek.MONDAY), ItemType.EVENT));

  }

  /**
   * Test for setName
   */
  @Test
  void setName() {
    assertEquals("test", this.week.getName());
    this.week.setName("after");
    assertEquals("after", this.week.getName());
  }


  /**
   * Test for converting to json
   */
  @Test
  void convertToJson() {
    WeekJson template = new WeekJson("name", new DayJson[0], 3, 6, new ItemJson[0], "", 0);
    Day d = new Day(DayOfTheWeek.MONDAY, new ArrayList<>(), 5, 10, 50);
    ArrayList<Day> days = new ArrayList<>();
    for (int i = 0; i < 7; i += 1) {
      days.add(d);
    }
    Week w = new Week("Week for template", days, 3, 6, new ArrayList<>(), "pw", 0);
    WeekJson templateRes = w.convertToJson(true);
    assertEquals(template.maxTasks(), templateRes.maxTasks());
    assertEquals(template.maxEvents(), templateRes.maxEvents());
    Assertions.assertNotEquals(template.password(), templateRes.password());
    Assertions.assertNotEquals(template.name(), templateRes.name());

  }

  /**
   * Test for get days
   */
  @Test
  void getDays() {
    Week w = new Week("Week", new ArrayList<>(), 0, 1, new ArrayList<>(), "pw", 9);
    w.totalTaskProgress();
    List<Day> days = w.getDays();
    assertEquals(0, days.size());
    w.addTask(new Task("task2", "desc", DayOfTheWeek.MONDAY));
  }

  /**
   * Test for total task progress
   */
  @Test
  void totalTaskProgress() {
    Day d = new Day(DayOfTheWeek.MONDAY, new ArrayList<>(), 5, 10, 50);
    ArrayList<Day> days = new ArrayList<>();
    days.add(d);
    Week w = new Week("name", days, 3, 3, new ArrayList<>(), "", 0);
    assertEquals(10.0, w.totalTaskProgress());
  }


  /**
   * Test for add task
   */
  @Test
  void addTask() {


  }

  /**
   * Test for get commitment level
   */
  @Test
  void getCommitmentLevel() {

  }

  /**
   * Test for combine week
   */
  @Test
  void combineWeek() {
    Day d = new Day(DayOfTheWeek.MONDAY, new ArrayList<>(), 5, 10, 50);
    ArrayList<Day> days = new ArrayList<>();
    days.add(d);
    Week w = new Week("name", days, 3, 3, new ArrayList<>(), "", 0);
    assertEquals(10.0, w.totalTaskProgress());
    w.combineWeek(w);
    assertEquals(10.0, w.totalTaskProgress());


  }

  /**
   * Test for set commitment level
   */
  @Test
  void setCommitmentLevel() {
    assertEquals("None", this.week.getCommitmentLevel(ItemType.TASK));
    assertEquals("None", this.week.getCommitmentLevel(ItemType.EVENT));
    this.week.setCommitmentLevel(2, ItemType.TASK);
    this.week.setCommitmentLevel(3, ItemType.EVENT);
    assertEquals("2", this.week.getCommitmentLevel(ItemType.TASK));
    assertEquals("3", this.week.getCommitmentLevel(ItemType.EVENT));
  }

  /**
   * test for removing task
   */
  @Test
  void removeTask() {
    Week w = new Week("Week", new ArrayList<>(), 0, 1, new ArrayList<>(), "pw", 0);
    w.combineWeek(w);

    List<Day> days = w.getDays();
    assertEquals(0, days.size());
    Task t = new Task("task2", "desc", DayOfTheWeek.MONDAY);
    w.addTask(t);
    assertEquals(0, w.removeTask(t));

  }


}