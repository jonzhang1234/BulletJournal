package cs3500.pa05.view;

import cs3500.pa05.controller.PopupController;
import cs3500.pa05.model.AlertType;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * the View for the Alert
 */
public class AlertView implements GuiView {
  private final FXMLLoader loader;

  /**
   * The constructor for the Alert
   *
   * @param type the type of alert
   * @param control the controller for the alert
   */
  public AlertView(AlertType type, PopupController control) {
    this.loader = new FXMLLoader();
    this.loader.setController(control);
    if (type == AlertType.SUCCESS) {
      this.loader.setLocation(this.getClass().getClassLoader().getResource("successAlert.fxml"));
    } else if (type == AlertType.ERROR) {
      this.loader.setLocation(this.getClass().getClassLoader().getResource("errorAlert.fxml"));
    } else {
      this.loader.setLocation(this.getClass().getClassLoader().getResource("warning.fxml"));
    }
  }

  /**
   * loads the FXML file
   *
   * @return the scene for the file
   */
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Alert popup failed to load");
    }
  }



}
