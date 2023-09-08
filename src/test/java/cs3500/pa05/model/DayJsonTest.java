package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test for DayJson
 */
class DayJsonTest {
  private DayJson dayJson;
  private ItemJson task;
  private ItemJson event;
  private AbstractJournalItem abTask;
  private AbstractJournalItem abEvent;

  /**
   * sets up the DayJson test
   */
  @BeforeEach
  void setUp() {
    this.task = new ItemJson(ItemType.TASK, "Task", "Description",
        DayOfTheWeek.TUESDAY, true, null, null);
    this.event = new ItemJson(ItemType.EVENT, "Event", "Description",
        DayOfTheWeek.TUESDAY, false, "03:01", "03:02");
    ItemJson[] items = new ItemJson[2];
    items[0] = this.task;
    items[1] = this.event;
    this.dayJson = new DayJson(DayOfTheWeek.TUESDAY, items, 1, 2, 3);
  }

  /**
   * test for convertToDay()
   */
  @Test
  void convertToDay() {
    this.abTask = new Task("Task", "Description", DayOfTheWeek.TUESDAY);
    this.abEvent = new Event("Event", "Description",
        DayOfTheWeek.TUESDAY, LocalTime.parse("03:01"), LocalTime.parse("03:02"));
    List<AbstractJournalItem> items = new ArrayList<>();
    items.add(abTask);
    items.add(abEvent);
    Day res = new Day(DayOfTheWeek.TUESDAY, items, 1, 2, 3);
    Day other = this.dayJson.convertToDay();
    assertEquals(res.getItems().size(), other.getItems().size());
    assertEquals(res.getDay(), other.getDay());
    assertEquals(res.getCompletedTasks(), other.getCompletedTasks());
    assertEquals(res.getTotalTasks(), other.getTotalTasks());
  }


  /**
   * Test for day method
   */
  @Test
  void day() {
    assertEquals(DayOfTheWeek.TUESDAY, dayJson.day());
  }

  /**
   * Test for items method
   */
  @Test
  void items() {
    ItemJson[] items = dayJson.items();
    for (ItemJson item : items) {
      assertEquals(DayOfTheWeek.TUESDAY, item.day());
    }
  }

  /**
   * Test for task count
   */
  @Test
   void taskCount() {
    assertEquals(1, dayJson.taskCount());
  }

  /**
   * Test for event count
   */
  @Test
  void eventCount() {
    assertEquals(2, dayJson.eventCount());
  }

  /**
   * Test for completed tasks
   */
  @Test
  void completedTasks() {
    assertEquals(3, dayJson.completedTasks());
  }
}