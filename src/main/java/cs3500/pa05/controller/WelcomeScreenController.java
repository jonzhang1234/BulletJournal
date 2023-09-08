package cs3500.pa05.controller;

import cs3500.pa05.JournalLauncher;
import cs3500.pa05.model.AlertType;
import cs3500.pa05.model.JsonUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller for the welcome screen
 */
public class WelcomeScreenController implements JournalScreenController {
  private final Stage stage;
  @FXML
  private Button loadFile;
  @FXML
  private Button selectFile;
  @FXML
  private Label pathLabel;
  @FXML
  private Button newFile;
  @FXML
  private TextField newFileName;
  @FXML
  private ComboBox<String> templateDrop;
  private File file;
  @FXML
  private TextField weekNameInput;
  private final JournalLauncher impl;
  private List<File> allTemplates;

  /**
   * The Controller for the WelcomeScreen
   *
   * @param s the stage to show the welcome screen on
   * @param impl the launcher for the welcome screen
   */
  public WelcomeScreenController(Stage s, JournalLauncher impl) {
    this.stage = s;
    this.impl = impl;
    this.allTemplates = new ArrayList<>();
  }

  /**
   * Starts the welcome screen
   */
  @FXML
  public void start() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Bujo File");
    selectFile.setOnAction(e -> {
      this.file = fileChooser.showOpenDialog(stage);
      if (this.file != null) {
        pathLabel.setText(file.getName() + "  selected");
      }
    });
    this.initDropdown();
    newFile.setOnAction(e -> {
      if (!newFileName.getText().isEmpty() && newFileName.getText().endsWith(".bujo")
          && !weekNameInput.getText().isEmpty()) {
        this.file = new File(newFileName.getText());
        if (templateDrop.getValue() == null) {
          this.impl.notifyLauncher(this.file, true, weekNameInput.getText(), 0, null);
        } else {
          int idx = templateDrop.getItems().indexOf(templateDrop.getValue());
          File f = this.allTemplates.get(idx);
          int orientation = JsonUtils.parseFile(f.toPath()).orientation();
          this.impl.notifyLauncher(this.file, true, weekNameInput.getText(), orientation, f);
        }
      } else {
        new AlertControl(AlertType.ERROR, this.stage, "Please set a name for the Week and "
            + "specify the Path ending with .bujo", false).start();
      }
    });

    loadFile.setOnAction(e -> {
      if (this.file != null && this.file.getPath().endsWith(".bujo")) {
        String pw = JsonUtils.parseFile(file.toPath()).password();
        int orientation = JsonUtils.parseFile(file.toPath()).orientation();
        if (pw.equals("")) {
          this.impl.notifyLauncher(this.file, false, "", orientation, null);
        } else {
          this.impl.initPassword(pw, this.file, orientation);
        }
        new AlertControl(AlertType.SUCCESS, this.stage, "File loaded", false).start();
      } else {
        new AlertControl(AlertType.ERROR, this.stage, "Not a valid .bujo file", false).start();
      }
    });
  }

  /**
   * initializes the dropdown for the templates
   */
  @FXML
  private void initDropdown() {
    File[] localTemplates = new File("./src/main/resources/TEMPLATES/").listFiles();
    List<String> fileNames = new ArrayList<>();
    List<File> templates = new ArrayList<>();
    if (localTemplates != null) {
      for (File localTemplate : localTemplates) {
        if (localTemplate.getPath().endsWith(".bujo")) {
          fileNames.add(localTemplate.getName());
          templates.add(localTemplate);
        }
      }
      this.allTemplates = templates;
      templateDrop.setItems(FXCollections.observableArrayList(fileNames));
    }
  }
}



