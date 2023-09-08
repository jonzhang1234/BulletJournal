package cs3500.pa05.controller;

import cs3500.pa05.model.AlertType;
import cs3500.pa05.view.TemplateView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for the save template page
 */
public class TemplateController implements PopupController {
  private final Stage stage;
  private final JournalControllerImpl journal;
  @FXML
  private TextField templateName;
  @FXML
  private Button saveTemplate;
  @FXML
  private Button cancelTemplate;
  private final Stage dialog;

  /**
   * The constructor for the TemplateController
   *
   * @param s the stage to display the template page
   * @param j the original journal controller
   */
  TemplateController(Stage s, JournalControllerImpl j) {
    this.stage = s;
    this.journal = j;
    TemplateView pv = new TemplateView(this);
    this.dialog = new Stage();
    dialog.setScene(pv.load());
  }

  /**
   * Starts the TemplateController
   */
  @Override
  public void start() {
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(stage);
    cancelTemplate.setOnAction(e -> this.dialog.hide());
    saveTemplate.setOnAction(e -> {
      String name = templateName.getText();
      if (name == null || name.equals("") || name.contains(" ")) {
        new AlertControl(AlertType.ERROR, this.stage,
            "Please input a valid name for the template. ", false).start();
      } else {
        journal.saveAsTemplate(templateName.getText());
        this.dialog.hide();
      }
    });
    dialog.show();
  }

  /**
   * shows the handler for the popup
   */
  @Override
  public void showHandler() {
    this.dialog.show();
  }
}
