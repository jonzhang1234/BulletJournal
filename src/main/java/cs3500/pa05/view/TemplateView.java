package cs3500.pa05.view;

import cs3500.pa05.controller.TemplateController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents the View for the Template
 */
public class TemplateView implements GuiView {
  private final FXMLLoader loader;

  /**
   * The constructor for the template view
   *
   * @param control the controller for the template view
   */
  public TemplateView(TemplateController control) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(this.getClass().getClassLoader().getResource("saveTemplate.fxml"));
    this.loader.setController(control);
  }

  /**
   * loads the template view
   *
   * @return the scene
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Save template failed to load");
    }

  }


}


