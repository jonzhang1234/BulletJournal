package cs3500.pa05.model;

import cs3500.pa05.controller.HyperlinkController;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Represents a Task
 */
public class Task extends AbstractJournalItem {
  boolean isComplete;

  /**
   * Constructor for the Task
   *
   * @param name the name of the task
   * @param description the description of the task
   * @param day the day the task occurs on
   */
  public Task(String name, String description, DayOfTheWeek day) {
    super(name, description, day);
    this.isComplete = false;
  }

  /**
   * Constructor for the task
   *
   * @param name the name of the task
   * @param description the description of the task
   * @param day the day the task occurs on
   * @param isComplete the completion status of the task
   */
  public Task(String name, String description, DayOfTheWeek day, boolean isComplete) {
    super(name, description, day);
    this.isComplete = isComplete;
  }

  /**
   * converts a task into json form
   *
   * @return the json form
   */
  @Override
  public ItemJson convertToJson() {
    return new ItemJson(ItemType.TASK, super.name,
        super.description, super.day, this.isComplete, null, null);
  }

  /**
   * converts the task into a formatted text flow
   *
   * @return the TextFlow
   */
  @Override
  public TextFlow convertToTextFlow() {
    TextFlow textFlow = new TextFlow();
    Text name = new Text(super.name + "\n");
    name.setFont(Font.font("SansSerif", FontWeight.BOLD, 13));
    TextFlow desc = HyperlinkController.getHyperlink(super.description);
    textFlow.getChildren().add(name);
    textFlow.getChildren().add(desc);
    return textFlow;
  }

  /**
   * gets the summary of the task in a Text for the Task Queue
   *
   * @return the Text
   */
  public Text getTaskSummary() {
    Text name = new Text(super.name);
    name.setFont(Font.font("SansSerif", FontWeight.BOLD, 13));
    return name;
  }

  /**
   * determines if the task is complete
   *
   * @return whether the task is complete
   */
  public boolean isComplete() {
    return this.isComplete;
  }

  /**
   * toggles the completion level of the task
   */
  public void flipComplete() {
    this.isComplete = !this.isComplete;
  }

  /**
   * formats the completion level for display
   *
   * @return the string
   */
  public String completeText() {
    if (this.isComplete) {
      return "Complete.";
    }
    return "Incomplete.";
  }
}
