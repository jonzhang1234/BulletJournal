package cs3500.pa05.view;

import cs3500.pa05.controller.JournalScreenController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * The View of the Week
 */
public class TaskView implements GuiView {
  private final FXMLLoader loader;

  /**
   * Constructor for the TaskView
   *
   * @param control the controller for the TaskView
   * @param horizontal indicates when to display a horizontal or vertical layout
   *                   (had to use numbers due to some double trigger glitch with javafx)
   */
  public TaskView(JournalScreenController control, int horizontal) {
    this.loader = new FXMLLoader();
    if (horizontal % 4 == 0) {
      this.loader.setLocation(this.getClass().getClassLoader().getResource("cal.fxml"));
    } else {
      this.loader.setLocation(this.getClass().getClassLoader().getResource("calVertical.fxml"));
    }
    this.loader.setController(control);
  }

  /**
   * loads the TaskView scene
   *
   * @return the scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Calendar failed to load");
    }
  }

}
