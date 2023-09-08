package cs3500.pa05.view;

import cs3500.pa05.controller.JournalScreenController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents the View for a PrivacyScreen
 */
public class PrivacyView implements GuiView {
  private final FXMLLoader loader;

  /**
   * Constructor for the PrivacyScreen
   *
   * @param control the controller for the privacy screen
   */
  public PrivacyView(JournalScreenController control) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(this.getClass().getClassLoader().getResource("privacyLock.fxml"));
    this.loader.setController(control);
  }

  /**
   * Loads the Scene from SceneBuilder
   *
   * @return the scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Privacy Lock failed to load");
    }
  }

}
