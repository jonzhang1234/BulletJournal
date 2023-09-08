package cs3500.pa05.view;

import cs3500.pa05.controller.PopupController;
import cs3500.pa05.model.ItemType;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * the loader for the Add task/item popup
 */
public class AddPopup implements GuiView {
  private final FXMLLoader loader;

  /**
   * Constructor for the AddPopup
   *
   * @param type the type of AddPopup
   * @param control the controller for the Popup
   */
  public AddPopup(ItemType type, PopupController control) {
    this.loader = new FXMLLoader();
    this.loader.setController(control);
    if (type == ItemType.TASK) {
      this.loader.setLocation(this.getClass().getClassLoader().getResource("taskPopup.fxml"));
    } else {
      this.loader.setLocation(this.getClass().getClassLoader().getResource("eventPopup.fxml"));
    }
  }

  /**
   * displays the popup
   *
   * @return the Scene for the popup
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Add popups failed to load");
    }
  }

}
