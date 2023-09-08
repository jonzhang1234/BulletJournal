package cs3500.pa05.controller;

import cs3500.pa05.JournalLauncher;
import cs3500.pa05.model.AlertType;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * The Controller for the PrivacyScreen
 */
public class PrivacyController implements JournalScreenController {
  @FXML
  private PasswordField passwordInput;
  @FXML
  private Button signIn;
  @FXML
  private Button goBack;
  private final String pw;
  private final Stage stage;
  private final JournalLauncher impl;
  private final File file;
  private final int orientation;

  /**
   * Constructor for the PrivacyController
   *
   * @param s the stage
   * @param pw the password
   * @param f the file to lock
   * @param impl the launcher
   */
  public PrivacyController(Stage s, String pw, File f, JournalLauncher impl, int orientation) {
    this.stage = s;
    this.pw = pw;
    this.file = f;
    this.impl = impl;
    this.orientation = orientation;
  }

  /**
   * Starts the PrivacyController
   */
  @Override
  public void start() {
    signIn.setOnAction(e -> {
      if (passwordInput.getText() != null) {
        if (passwordInput.getText().equals(this.pw)) {
          this.impl.notifyLauncher(this.file, false, "", orientation, null);
        } else {
          new AlertControl(AlertType.ERROR, this.stage, "Wrong password!", false).start();
        }
      } else {
        new AlertControl(AlertType.ERROR, this.stage, "Enter the password", false).start();
      }
    });
    goBack.setOnAction(e -> impl.goHome());
  }
}
