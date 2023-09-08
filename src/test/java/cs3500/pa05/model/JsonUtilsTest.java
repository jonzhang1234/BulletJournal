package cs3500.pa05.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Test for json utils
 */
class JsonUtilsTest {

  /**
   * Test for serializing records
   */
  @Test
  void serializeRecord() {
    JsonUtils testCoverage = new JsonUtils();
    ItemJson task = new ItemJson(ItemType.TASK, "Task", "Description",
        DayOfTheWeek.MONDAY, false, null, null);
    JsonNode res = JsonUtils.serializeRecord(task);
    String output = "{\"itemType\":\"TASK\",\"itemName\":\"Task\",\"itemDescription\""
        + ":\"Description\",\"day\":\"MONDAY\",\"taskComplete\""
        + ":false,\"eventStart\":null,\"eventDuration\":null}";
    assertEquals(output, res.toString());
  }

  /**
   * Test for writing to file
   */
  @Test
  void writeToFile() {
    JsonNode json = new ObjectMapper().createObjectNode();
    Path invalidPath = Path.of("some/random/invalid/path/file.json");
    assertThrows(IllegalArgumentException.class, () -> {
      JsonUtils.writeToFile(json, invalidPath);
    });

    JsonUtils.writeToFile(JsonUtils.serializeRecord(new ItemJson(ItemType.TASK, "name",
            "description", DayOfTheWeek.MONDAY, false, null, null)),
        Path.of("./src/test/resources/weekjsontest.bujo"));
    File f = new File("./src/test/resources/weekjsontest.bujo");
    StringBuilder res = new StringBuilder();
    try {
      Scanner sc = new Scanner(f);
      while (sc.hasNextLine()) {
        res.append(sc.nextLine());
      }
    } catch (IOException e) {
      throw new IllegalStateException("Test file couldn't be read");
    }

    assertEquals("{\"itemType\":\"TASK\",\"itemName\":\"name\",\"itemDescription\""
        + ":\"description\",\"day\":\"MONDAY\",\"taskComplete\":false,\"eventStart\":null,"
        + "\"eventDuration\":null}", res.toString());
  }

  /**
   * Test for parse file
   */
  @Test
  void testParseFile() {
    JsonUtils.parseFile(Path.of("./src/test/resources/sampleWeek.bujo"));
    assertThrows(IllegalArgumentException.class, () ->
        JsonUtils.parseFile(Path.of("./file")));
  }
}