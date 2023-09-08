package cs3500.pa05.view;

import cs3500.pa05.controller.PopupController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * The view for the password screen
 */
public class PasswordView implements GuiView {
  private final FXMLLoader loader;

  /**
   * The constructor for PasswordView
   *
   * @param control the control for the password popup
   */
  public PasswordView(PopupController control) {
    this.loader = new FXMLLoader();
    this.loader.setController(control);
    this.loader.setLocation(this.getClass().getClassLoader().getResource("passwordPopup.fxml"));
  }

  /**
   * loads the popup from SceneBuilder
   *
   * @return the scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Password Popup failed to load");
    }
  }
}
