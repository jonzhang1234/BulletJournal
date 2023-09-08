package cs3500.pa05.controller;

import cs3500.pa05.model.AlertType;
import cs3500.pa05.view.AlertView;
import cs3500.pa05.view.GuiView;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Represents an Alert, 3 types of alert, warning, success, or fail
 */
public class AlertControl implements PopupController {
  @FXML
  private Button closeMessage;
  @FXML
  private Label alertMessage;
  private final String message;
  private final Stage stage;
  private final boolean persistent;
  private final Popup popup;

  /**
   * Constructor for AlertControl
   *
   * @param type the type of alert
   * @param s the stage to display the alert on
   * @param message the message the alert to display
   * @param persistent whether the alert is persistent or disappears automatically
   */
  AlertControl(AlertType type, Stage s, String message, boolean persistent) {
    this.popup = new Popup();
    GuiView alertView = new AlertView(type, this);
    this.popup.getContent().add(alertView.load().getRoot());
    this.stage = s;
    this.message = message;
    this.persistent = persistent;
  }

  /**
   * Starts the controller for the alert
   */
  @Override
  public void start() {
    alertMessage.setWrapText(true);
    alertMessage.setPadding(new Insets(0, 3, 0, 3));
    alertMessage.setTextAlignment(TextAlignment.CENTER);
    if (persistent) {
      alertMessage.setText(this.message + "\nClick to Close");
      alertMessage.setTextFill(Color.BLACK);
      closeMessage.setOnAction(e -> popup.hide());
      this.popup.show(stage);
    } else {
      alertMessage.setText(this.message);
      alertMessage.setTextFill(Color.BLACK);
      PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
      delay.setOnFinished(e -> popup.hide());
      this.popup.show(stage);
      delay.play();
    }
    this.popup.setX(stage.getX() + (stage.getWidth() - this.popup.getWidth()) / 2);
    this.popup.setY(stage.getY() + stage.getHeight() - this.popup.getHeight());
  }

  /**
   * Shows the Alert
   */
  @Override
  public void showHandler() {
    this.popup.show(stage);
  }
}
