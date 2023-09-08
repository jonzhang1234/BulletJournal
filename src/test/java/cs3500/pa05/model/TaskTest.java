package cs3500.pa05.model;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for Task
 */
class TaskTest {
  private ItemJson task;
  private AbstractJournalItem abTask;
  private Task actualTask;

  /**
   * Set up before testing
   */
  @BeforeEach
  void setup() {
    this.task = new ItemJson(ItemType.TASK, "Task", "Description",
        DayOfTheWeek.MONDAY, false, null, null);
    this.abTask = new Task("Task", "Description", DayOfTheWeek.MONDAY);
    this.actualTask = new Task("Task", "Description", DayOfTheWeek.MONDAY);


  }

  /**
   * Test for converting to json
   */
  @Test
  void convertToJson() {
    ItemJson js = this.abTask.convertToJson();
    assertEquals(this.task.type(), js.type());
    assertEquals(this.task.desc(), js.desc());
    assertEquals(this.task.name(), js.name());
    assertEquals(this.task.day(), js.day());
    assertEquals(this.task.duration(), js.duration());
    assertEquals(this.task.start(), js.start());
    assertEquals(this.task.isComplete(), js.isComplete());

  }



  /**
   * Test for getting task summary
   */
  @Test
  void getTaskSummary() {
  }

  /**
   * Test if a given item is complete
   */
  @Test
  void isComplete() {
    assertFalse(this.actualTask.isComplete());
  }

  /**
   * test for the method flipComplete
   */
  @Test
  void flipComplete() {
    assertFalse(this.actualTask.isComplete());
    this.actualTask.flipComplete();
    assertTrue(this.actualTask.isComplete());
    this.actualTask.flipComplete();
    assertFalse(this.actualTask.isComplete());
  }

  /**
   * Test for complete text
   */
  @Test
  void completeText() {
    assertEquals("Incomplete.", this.actualTask.completeText());
    this.actualTask.flipComplete();
    assertEquals("Complete.", this.actualTask.completeText());
  }
}