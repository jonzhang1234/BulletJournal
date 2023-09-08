package cs3500.pa05.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for event
 */
class EventTest {
  private ItemJson event;
  private AbstractJournalItem abEvent;

  /**
   * Set up before testing
   */
  @BeforeEach
  void setup() {
    this.event = new ItemJson(ItemType.EVENT, "Event", "Description",
        DayOfTheWeek.MONDAY, null, "03:01", "03:02");
    this.abEvent = new Event("Event", "Description",
        DayOfTheWeek.MONDAY, LocalTime.parse("03:01"), LocalTime.parse("03:02"));
  }

  /**
   * Test for converting to json
   */
  @Test
  void convertToJson() {
    AlertType test = AlertType.ERROR;
    AlertType test1 = AlertType.SUCCESS;
    AlertType test2 = AlertType.WARNING;
    ItemJson js = this.abEvent.convertToJson();
    Assertions.assertEquals(this.event.type(), js.type());
    Assertions.assertEquals(this.event.desc(), js.desc());
    Assertions.assertEquals(this.event.name(), js.name());
    Assertions.assertEquals(this.event.day(), js.day());
    Assertions.assertEquals(this.event.duration(), js.duration());
    Assertions.assertEquals(this.event.start(), js.start());
    Assertions.assertEquals(this.event.isComplete(), js.isComplete());

  }

}