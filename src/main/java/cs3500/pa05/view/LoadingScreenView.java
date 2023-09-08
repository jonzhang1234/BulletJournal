package cs3500.pa05.view;

import cs3500.pa05.controller.JournalScreenController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * The View for the LoadingScreen
 */
public class LoadingScreenView implements GuiView {
  private final FXMLLoader loader;

  /**
   * Constructor for the LoadingScreen
   *
   * @param control the controller of the loading screen
   */
  public LoadingScreenView(JournalScreenController control) {
    this.loader = new FXMLLoader();
    this.loader.setController(control);
    this.loader.setLocation(this.getClass().getClassLoader().getResource("SplashScreen.fxml"));
  }

  /**
   * Loads the Scene Builder
   *
   * @return the scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Loading screen failed to load");
    }
  }
}
