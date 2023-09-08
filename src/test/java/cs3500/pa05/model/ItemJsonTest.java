package cs3500.pa05.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for item json
 */
class ItemJsonTest {
  private ItemJson task;
  private ItemJson event;
  private List<AbstractJournalItem> items;
  private AbstractJournalItem abTask;
  private AbstractJournalItem abEvent;

  /**
   * Set up before testing
   */
  @BeforeEach
  void initSetup() {
    this.task = new ItemJson(ItemType.TASK, "Task", "Description", DayOfTheWeek.MONDAY, false,
        null, null);
    this.event = new ItemJson(ItemType.EVENT, "Event", "Description", DayOfTheWeek.MONDAY, null,
        "03:01", "03:02");
    this.items = new ArrayList<>();
    this.abTask = new Task("Task", "Description", DayOfTheWeek.MONDAY);
    this.abEvent = new Event("Event", "Description",
        DayOfTheWeek.MONDAY, LocalTime.parse("03:01"), LocalTime.parse("03:02"));
    this.items.add(abTask);
    this.items.add(abEvent);

  }

  /**
   * Test for converting to Json
   */
  @Test
  void convertToJson() {
    assertEquals(this.task, ItemJson.convertToJson(this.items)[0]);
    assertEquals(this.event, ItemJson.convertToJson(this.items)[1]);
  }

  /**
   * Test for converting to journal item
   */
  @Test
  void convertToJournalItem() {
    ItemJson taskJson = ItemJson.convertToJson(this.items)[0];
    ItemJson eventJson = ItemJson.convertToJson(this.items)[1];
    assertEquals(this.abTask.getDay(), taskJson.convertToJournalItem().getDay());
    assertEquals(this.abEvent.getDay(), eventJson.convertToJournalItem().getDay());
    assertEquals(this.abTask.convertToJson(), taskJson);
    assertEquals(this.abEvent.convertToJson(), eventJson);
  }

  /**
   * Test the type of item
   */
  @Test
  void testType() {
    assertEquals(ItemType.TASK, this.task.type());
    assertEquals(ItemType.EVENT, this.event.type());
  }

  /**
   * Test the name
   */
  @Test
  void testName() {
    assertEquals("Task", this.task.name());
    assertEquals("Event", this.event.name());
  }

  /**
   * Test the description of the given item
   */
  @Test
  void desc() {
    assertEquals("Description", this.task.desc());
    assertEquals("Description", this.event.desc());
  }

  /**
   * Test the day
   */
  @Test
  void day() {
    assertEquals(DayOfTheWeek.MONDAY, this.task.day());
    assertEquals(DayOfTheWeek.MONDAY, this.event.day());
  }

  /**
   * Test if the item is completed
   */
  @Test
  void isComplete() {
    assertEquals(false, this.task.isComplete());
    assertNull(this.event.isComplete());
  }

  /**
   * Test start method
   */
  @Test
  void start() {
    assertEquals(null, this.task.start());
    assertEquals("03:01", this.event.start());
  }

  /**
   * Test for duration method
   */
  @Test
  void duration() {
    assertEquals(null, this.task.duration());
    assertEquals("03:02", this.event.duration());
  }
}