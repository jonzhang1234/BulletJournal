package cs3500.pa05.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for Day class
 */
class DayTest {
  Day day;

  /**
   * Test up before testing
   */
  @BeforeEach
  void setup() {
    List<AbstractJournalItem> items = new ArrayList<>();
    this.day = new Day(DayOfTheWeek.MONDAY, items);
  }

  /**
   * Test for adding item
   */
  @Test
  void addItem() {
    Assertions.assertEquals(0, day.getItems().size());
    Task t = new Task("task", "desc", DayOfTheWeek.MONDAY);
    day.addItem(t, ItemType.TASK);
    Assertions.assertEquals(1, day.getItems().size());
    Assertions.assertEquals(1, day.getTotalTasks());
    Assertions.assertEquals(t, day.getItems().get(0));
  }

  /**
   * Test for converting to json
   */
  @Test
  void convertToJson() {
    this.day.addItem(new Task("task", "desc", DayOfTheWeek.MONDAY), ItemType.TASK);
    DayJson js = this.day.convertToJson();
    assertEquals(DayOfTheWeek.MONDAY, js.day());
    assertEquals(1, js.taskCount());
    assertEquals(0, js.eventCount());
    assertEquals(0, js.completedTasks());
  }

  /**
   * Test for getting items
   */
  @Test
  void getItems() {
    Event abEvent = new Event("Event", "Description",
        DayOfTheWeek.MONDAY, LocalTime.parse("03:01"), LocalTime.parse("03:02"));
    Task t = new Task("task", "desc", DayOfTheWeek.MONDAY);
    this.day.addItem(abEvent, ItemType.EVENT);
    this.day.addItem(t, ItemType.TASK);
    assertEquals(2, this.day.getItems().size());
    assertEquals(abEvent, this.day.getItems().get(0));
    assertEquals(t, this.day.getItems().get(1));
  }

  /**
   * Test for combine day
   */
  @Test
  void combineDay() {
    Event abEvent = new Event("Event", "Description",
        DayOfTheWeek.MONDAY, LocalTime.parse("03:01"), LocalTime.parse("03:02"));
    Task t = new Task("task", "desc", DayOfTheWeek.MONDAY);
    this.day.addItem(abEvent, ItemType.EVENT);
    this.day.addItem(t, ItemType.TASK);
    Day d = new Day(DayOfTheWeek.MONDAY, new ArrayList<>());
    d.combineDay(this.day);

    assertEquals(d.getItems(), this.day.getItems());
    assertEquals(d.getTotalTasks(), this.day.getTotalTasks());
    assertEquals(d.getCompletedTasks(), this.day.getCompletedTasks());
    assertEquals(d.getDay(), this.day.getDay());
  }

  /**
   * Test for exceed level
   */
  @Test
  void exceedLevel() {
    assertFalse(this.day.exceedLevel(-1, ItemType.TASK));
    this.day.addItem(new Task("task", "desc", DayOfTheWeek.MONDAY), ItemType.TASK);
    assertFalse(this.day.exceedLevel(1, ItemType.TASK));
    assertTrue(this.day.exceedLevel(0, ItemType.TASK));
    assertFalse(this.day.exceedLevel(-1, ItemType.EVENT));
    this.day.addItem(new Event("Event", "Description",
        DayOfTheWeek.MONDAY, LocalTime.parse("03:01"), LocalTime.parse("03:02")), ItemType.EVENT);
    assertFalse(this.day.exceedLevel(1, ItemType.EVENT));
    assertTrue(this.day.exceedLevel(0, ItemType.EVENT));
  }

  /**
   * Test for mark task
   */
  @Test
  void markTask() {
    assertEquals(0, this.day.getCompletedTasks());
    this.day.markTask(true);
    assertEquals(1, this.day.getCompletedTasks());
    this.day.markTask(false);
    assertEquals(0, this.day.getCompletedTasks());

  }

  /**
   * Test for getting total tasks
   */
  @Test
  void getTotalTasks() {
    assertEquals(0, this.day.getTotalTasks());
    this.day.addItem(new Task("task", "desc", DayOfTheWeek.MONDAY), ItemType.TASK);
    assertEquals(1, this.day.getTotalTasks());
  }

  /**
   * Test for getting completed tasks
   */
  @Test
  void getCompletedTasks() {
    assertEquals(0, this.day.getCompletedTasks());
    this.day.markTask(true);
    assertEquals(1, this.day.getCompletedTasks());
    this.day.markTask(false);
    assertEquals(0, this.day.getCompletedTasks());
  }

  /**
   * Test for getting day
   */
  @Test
  void getDay() {
    assertEquals(DayOfTheWeek.MONDAY.toString(),
        this.day.getDay());
  }

  /**
   * Test for removing item
   */
  @Test
  void removeItem() {
    assertEquals(0, day.getItems().size());
    Task t = new Task("task", "desc", DayOfTheWeek.MONDAY);

    day.addItem(t, ItemType.TASK);
    assertEquals(1, day.getItems().size());
    assertEquals(1, day.getTotalTasks());
    assertEquals(t, day.getItems().get(0));
    day.removeItem(0);
    assertEquals(0, day.getItems().size());
    assertEquals(0, day.getTotalTasks());
    t.flipComplete();
    day.addItem(t, ItemType.TASK);
    day.markTask(true);
    assertEquals(1, day.getCompletedTasks());
    day.removeItem(0);
    assertEquals(0, day.getCompletedTasks());
    this.day.addItem(new Event("Event", "Description",
        DayOfTheWeek.MONDAY, LocalTime.parse("03:01"), LocalTime.parse("03:02")), ItemType.EVENT);
    day.removeItem(0);


  }
}