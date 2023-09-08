package cs3500.pa05.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for week json
 */
class WeekJsonTest {
  private WeekJson weekJson;
  private WeekJson weekJson2;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Set up before testing
   */
  @BeforeEach
  void setUp()  {
    Path sampleFile = Path.of("src/test/resources/sampleWeek.bujo");
    try {
      InputStream in = Files.newInputStream(sampleFile);
      JsonParser parser = this.mapper.getFactory().createParser(in);
      weekJson = parser.readValueAs(WeekJson.class);

      Path sampleFile2 = Path.of("src/test/resources/sampleWeek 2.bujo");
      InputStream in2 = Files.newInputStream(sampleFile2);
      JsonParser parser2 = this.mapper.getFactory().createParser(in2);
      weekJson2 = parser2.readValueAs(WeekJson.class);
    } catch (IOException e) {
      throw new IllegalStateException("Invalid test file path");
    }
  }

  /**
   * Test for converting to week
   */
  @Test
  void convertToWeek() {
    Week week2 = weekJson2.convertToWeek();
    Week week = weekJson.convertToWeek();
    assertEquals(weekJson.name(), week.getName());
    assertEquals("None", week.getCommitmentLevel(ItemType.TASK));
    assertEquals("None", week.getCommitmentLevel(ItemType.EVENT));
    assertEquals(weekJson.items().length, week.getDays().size());
  }

  /**
   * Test for name
   */
  @Test
  void name() {
    assertEquals("awe", weekJson.name());
  }

  /**
   * Test for items
   */
  @Test
  void items() {
    DayJson[] days = weekJson.items();
    assertEquals(DayOfTheWeek.SUNDAY, days[0].day());
    assertEquals(DayOfTheWeek.MONDAY, days[1].day());
    assertEquals(DayOfTheWeek.TUESDAY, days[2].day());
    assertEquals(DayOfTheWeek.WEDNESDAY, days[3].day());
    assertEquals(DayOfTheWeek.THURSDAY, days[4].day());
    assertEquals(DayOfTheWeek.FRIDAY, days[5].day());
    assertEquals(DayOfTheWeek.SATURDAY, days[6].day());
  }

  /**
   * Test for max tasks
   */
  @Test
  void maxTasks() {
    assertEquals(-1, weekJson.maxTasks());
  }

  /**
   * Test for max events
   */
  @Test
  void maxEvents() {
    assertEquals(-1, weekJson.maxEvents());
  }

  /**
   * Test for tasks
   */
  @Test
  void tasks() {
    ItemJson[] tasks = weekJson.tasks();
    for (ItemJson task : tasks) {
      assertEquals(ItemType.TASK, task.type());
    }
  }

  /**
   * Test for password
   */
  @Test
  void password() {
    assertEquals("meow", weekJson.password());
  }
}