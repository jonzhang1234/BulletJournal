package cs3500.pa05.controller;

import cs3500.pa05.model.Week;
import cs3500.pa05.view.GuiView;
import cs3500.pa05.view.PasswordView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * The Controller for the Save file popup
 */
public class SaveController implements PopupController {
  private final JournalControllerImpl journal;
  @FXML
  private PasswordField passwordInput;
  @FXML
  private Button saveWithPw;
  @FXML
  private Button saveTemplate;
  @FXML
  private Button saveCancel;
  private final Stage dialog;

  /**
   * The Constructor for the SaveController
   *
   * @param j the original journal controller
   */
  SaveController(JournalControllerImpl j) {
    this.journal = j;
    this.dialog = new Stage();
    GuiView pv = new PasswordView(this);
    dialog.setScene(pv.load());
  }

  /**
   * Starts the save controller
   */
  @Override
  public void start() {
    saveWithPw.setOnAction(e -> {
      journal.save(true, true);
      this.dialog.close();
    });
    saveTemplate.setOnAction(e -> new TemplateController(this.dialog, journal).start());
    saveCancel.setOnAction(e -> this.dialog.close());
  }

  /**
   * shows the popup for the save screen
   */
  @Override
  public void showHandler() {
    this.dialog.show();
  }

  /**
   * sets the password if the user wants to lock the file
   *
   * @param w the week to lock
   */
  public void setPassword(Week w) {
    w.setPassword(passwordInput.getText());
  }
}
