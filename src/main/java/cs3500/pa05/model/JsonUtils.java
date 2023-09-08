package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Simple utils class used to hold static methods that help with serializing and deserializing JSON.
 */
public class JsonUtils {
  /**
   * Converts a given record object to a JsonNode.
   *
   * @param record the record to convert
   * @return the JsonNode representation of the given record
   */
  public static JsonNode serializeRecord(Record record) {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.convertValue(record, JsonNode.class);
  }

  /**
   * writes the given json into a bujo file
   *
   * @param json the json node
   * @param p the path to the bujo file
   */
  public static void writeToFile(JsonNode json, Path p) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(new File(p.toString()), json);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid path provided");
    }
  }

  /**
   * parses the given bujo file and converts it to a record
   *
   * @param p the path of the bujo file
   * @return the WeekJson of it
   */
  public static WeekJson parseFile(Path p) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonParser parser = mapper.getFactory()
          .createParser(new FileReader(p.toString()));
      return parser.readValueAs(WeekJson.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid path provided");
    }
  }
}