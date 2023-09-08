package cs3500.pa05.model;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test for abstract journal item
 */
class AbstractJournalItemTest {

  /**
   * Test getDay method
   */
  @Test
  void getDay() {
    AbstractJournalItem task = new Task("name", "desc", DayOfTheWeek.WEDNESDAY);
    assertEquals(DayOfTheWeek.WEDNESDAY, task.getDay());
  }
}