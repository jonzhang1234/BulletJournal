package cs3500.pa05.view;

import javafx.scene.Scene;

/**
 * Represents the View for the FX Scene or components via SceneBuilder
 */
public interface GuiView {
  /**
   * loads the fxml file
   *
   * @return the scene
   */
  Scene load();
}
