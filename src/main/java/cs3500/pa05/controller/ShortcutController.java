package cs3500.pa05.controller;

import cs3500.pa05.view.GuiView;
import cs3500.pa05.view.ShortcutPopup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The Controller for the Shortcut page
 */
public class ShortcutController implements PopupController {
  @FXML
  private Button closeButton;
  private final Stage dialog;
  private final GuiView popupView;

  /**
   * The constructor for the shortcut screen
   */
  ShortcutController() {
    this.dialog = new Stage();
    this.popupView = new ShortcutPopup(this);
  }

  /**
   * Starts the shortcut page
   */
  public void start() {
    this.dialog.setScene(popupView.load());
    closeButton.setOnAction(e -> this.dialog.close());
  }

  /**
   * Shows the shortcut page
   */
  public void showHandler() {
    this.dialog.show();
  }


}

