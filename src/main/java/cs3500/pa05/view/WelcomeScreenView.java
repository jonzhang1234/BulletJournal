package cs3500.pa05.view;

import cs3500.pa05.controller.JournalScreenController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * the view for the Welcome Screen
 */
public class WelcomeScreenView implements GuiView {
  private final FXMLLoader loader;

  /**
   * Constructor for the WelcomeScreenView
   *
   * @param control the controller of the view
   */
  public WelcomeScreenView(JournalScreenController control) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(this.getClass().getClassLoader().getResource("welcomeScreen.fxml"));
    this.loader.setController(control);
  }

  /**
   * loads the view from SceneBuilder
   *
   * @return the scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Welcome screen failed to load");
    }
  }
}
