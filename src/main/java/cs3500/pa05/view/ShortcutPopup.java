package cs3500.pa05.view;

import cs3500.pa05.controller.PopupController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents the Shortcut screen view
 */
public class ShortcutPopup implements GuiView {
  private final FXMLLoader loader;

  /**
   * Constructor for the Shortcut Popup
   *
   * @param control the controller for the shortcut popup
   */
  public ShortcutPopup(PopupController control) {
    this.loader = new FXMLLoader();
    this.loader.setController(control);
    this.loader.setLocation(this.getClass().getClassLoader().getResource("keyboardShortcut.fxml"));
  }

  /**
   * loads the Shortcut scene
   *
   * @return the scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Unable to load shortcut fxml");
    }
  }

}
