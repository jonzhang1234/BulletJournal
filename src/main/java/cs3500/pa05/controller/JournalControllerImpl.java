package cs3500.pa05.controller;


import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa05.JournalLauncher;
import cs3500.pa05.model.AbstractJournalItem;
import cs3500.pa05.model.AlertType;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.ItemType;
import cs3500.pa05.model.JsonUtils;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Week;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Represents the Main Journal Screen Controller for the App
 */
public class JournalControllerImpl implements JournalScreenController {
  @FXML
  private Button addTask;
  @FXML
  private Button addEvent;
  @FXML
  private ListView<HBox> list0;
  @FXML
  private ListView<HBox> list1;
  @FXML
  private ListView<HBox> list2;
  @FXML
  private ListView<HBox> list3;
  @FXML
  private ListView<HBox> list4;
  @FXML
  private ListView<HBox> list5;
  @FXML
  private ListView<HBox> list6;
  @FXML
  private Button saveButton;
  @FXML
  private TextField maxEventInput;
  @FXML
  private Button submitMaxEvent;
  @FXML
  private Label maxEvents;
  @FXML
  private TextField maxTaskInput;
  @FXML
  private Button submitMaxTask;
  @FXML
  private Label maxTasks;
  @FXML private Label weekName;
  @FXML
  private Button home;
  @FXML
  private ListView<HBox> taskQueue;
  @FXML
  private Button shortcut;
  @FXML
  private ProgressBar pb0;
  @FXML
  private ProgressBar pb1;
  @FXML
  private ProgressBar pb2;
  @FXML
  private ProgressBar pb3;
  @FXML
  private ProgressBar pb4;
  @FXML
  private ProgressBar pb5;
  @FXML
  private ProgressBar pb6;
  @FXML
  private ProgressBar pbTask;
  @FXML
  private Button deleteSelected;
  @FXML
  private Text totalEvents;
  @FXML
  private Text totalTasks;
  @FXML
  private Text percentageTasks;
  private final List<ProgressBar> progressBars;
  private final JournalLauncher impl;
  private final boolean newFile;
  private Week week;
  private final Path path;
  private final Stage stage;
  private SaveController setPassword;
  private PopupController taskPopup;
  private PopupController eventPopup;
  private final List<ListView<HBox>> daysGui = new ArrayList<>();

  /**
   * Constructor for Journal Controller
   *
   * @param s the stage that this journal appears on
   * @param f the bujo file to open
   * @param isNew determines if this file is a new file or loaded file
   * @param impl the launcher that launched the program
   * @param weekName the name of the week
   */
  public JournalControllerImpl(Stage s, File f, boolean isNew, JournalLauncher impl,
                               String weekName, int orientation) {
    this.stage = s;
    this.week = new Week(weekName, orientation);
    this.path = f.toPath();
    this.newFile = isNew;
    week.setCommitmentLevel(-1, ItemType.TASK);
    week.setCommitmentLevel(-1, ItemType.EVENT);
    this.impl = impl;
    this.progressBars = new ArrayList<>();
  }

  /**
   * Starts the controller, initializes all handlers and elements in the GUI
   */
  public void start() {
    this.initDays();
    this.initPbs();
    taskPopup = new AddPopupController(ItemType.TASK, week, daysGui, taskQueue, this.progressBars,
        this.stage, Arrays.asList(totalEvents, totalTasks, percentageTasks));
    eventPopup = new AddPopupController(ItemType.EVENT, week, daysGui, taskQueue, this.progressBars,
        this.stage, Arrays.asList(totalEvents, totalTasks, percentageTasks));
    this.setPassword = new SaveController(this);
    taskPopup.start();
    eventPopup.start();
    this.initHandlers();
    totalEvents.setText("Total Events: ");
    totalTasks.setText("Total Tasks: ");
    percentageTasks.setText("% of Tasks Completed: ");
    if (!this.newFile) {
      this.openFile(this.path);
    }
    this.updateWeeklyStatistics();
    this.weekName.setText(this.week.getName());
  }

  /**
   * Helps initializes the keybindings in the scene
   *
   * @param sc the scene
   */
  public void initKeybindings(Scene sc) {
    // new week
    sc.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<>() {
      final KeyCombination keyComb = new KeyCodeCombination(KeyCode.N,
          KeyCombination.SHORTCUT_DOWN);
      public void handle(KeyEvent ke) {
        if (keyComb.match(ke)) {
          ke.consume();
          impl.goHome();
        }
      }
    });

    // new event
    sc.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<>() {
      final KeyCombination keyComb = new KeyCodeCombination(KeyCode.E,
          KeyCombination.SHORTCUT_DOWN);
      public void handle(KeyEvent ke) {
        if (keyComb.match(ke)) {
          ke.consume();
          eventPopup.showHandler();
        }
      }
    });

    // delete item
    sc.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<>() {
      final KeyCombination keyComb = new KeyCodeCombination(KeyCode.BACK_SPACE,
          KeyCombination.SHORTCUT_DOWN);
      public void handle(KeyEvent ke) {
        if (keyComb.match(ke)) {
          ke.consume();
          deleteSelectedItems();
        }
      }
    });

    // new task
    sc.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<>() {
      final KeyCombination keyComb = new KeyCodeCombination(KeyCode.T,
          KeyCombination.SHORTCUT_DOWN);
      public void handle(KeyEvent ke) {
        if (keyComb.match(ke)) {
          ke.consume();
          taskPopup.showHandler();
        }
      }
    });

    // save
    sc.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<>() {
      final KeyCombination keyComb = new KeyCodeCombination(KeyCode.S,
          KeyCombination.SHORTCUT_DOWN);
      public void handle(KeyEvent ke) {
        if (keyComb.match(ke)) {
          ke.consume();
          setPassword.start();
          setPassword.showHandler();
        }
      }
    });

    // open file
    sc.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<>() {
      final KeyCombination keyComb = new KeyCodeCombination(KeyCode.O,
          KeyCombination.SHORTCUT_DOWN);
      public void handle(KeyEvent ke) {
        if (keyComb.match(ke)) {
          ke.consume();
          File file = new FileChooser().showOpenDialog(stage);
          if (file != null && file.getPath().endsWith(".bujo")) {
            clear();
            openFile(Path.of(file.getPath()));
            new AlertControl(AlertType.SUCCESS, stage, "File loaded", false).start();
          } else {
            new AlertControl(AlertType.ERROR, stage, "Not a valid .bujo file", false).start();
          }
        }
      }
    });
  }

  /**
   * handler for the commitment level
   *
   * @param type the item type
   */
  private void commitmentLevelHandler(ItemType type) {
    String text;
    if (type == ItemType.TASK) {
      text = maxTaskInput.getText();
    } else {
      text = maxEventInput.getText();
    }
    if (!text.matches("[0-9]*")) {
      new AlertControl(AlertType.ERROR, this.stage, "Must input valid number", false).start();
    } else {
      try {
        int level = Integer.parseInt(text);
        week.setCommitmentLevel(level, type);
        for (Day d : week.getDays()) {
          if (d.exceedLevel(level, type)) {
            new AlertControl(AlertType.WARNING, this.stage, d.getDay()
                + " has exceeded your set level of "
                + type.toString().toLowerCase() + "s", true).start();
            break;
          }
        }
      } catch (NumberFormatException e) {
        new AlertControl(AlertType.ERROR, this.stage, "Must input valid number", false).start();
      }
    }
  }

  /**
   * initializes the handlers
   */
  private void initHandlers() {
    addEvent.setOnAction(e -> eventPopup.showHandler());
    addTask.setOnAction(e -> taskPopup.showHandler());
    saveButton.setOnAction(e -> {
      this.setPassword.start();
      this.setPassword.showHandler();
    });
    submitMaxEvent.setOnAction(e -> {
      commitmentLevelHandler(ItemType.EVENT);
      this.maxEvents.setText("Max Events: " + week.getCommitmentLevel(ItemType.EVENT));
    });
    submitMaxTask.setOnAction(e -> {
      commitmentLevelHandler(ItemType.TASK);
      this.maxTasks.setText("Max Tasks: " + week.getCommitmentLevel(ItemType.TASK));
    });
    PopupController shortcutPopup = new ShortcutController();
    shortcutPopup.start();
    shortcut.setOnAction(e -> shortcutPopup.showHandler());
    deleteSelected.setOnAction(e -> deleteSelectedItems());
    home.setOnAction(e -> impl.goHome());
  }

  /**
   * updates the weekly statistics and displays the text on the GUI
   */
  private void updateWeeklyStatistics() {
    int tasks = 0;
    int events = 0;
    for (Day d : this.week.getDays()) {
      events += d.getItems().size() - d.getTotalTasks();
      tasks += d.getTotalTasks();
    }
    // update weekly statistics
    this.totalEvents.setText(events + " total events");
    this.totalTasks.setText(tasks + " total tasks");
    this.percentageTasks.setText(Math.round(this.progressBars.get(7).getProgress() * 10000)
        / 100.0 + "% of tasks completed");
  }

  /**
   * deletes the selected items in the GUI
   */
  private void deleteSelectedItems() {
    boolean selected = false;
    for (int i = 0; i < daysGui.size(); i += 1) {
      int idx = daysGui.get(i).getSelectionModel().getSelectedIndex();
      if (idx != -1) {
        daysGui.get(i).getItems().remove(idx);
        Task t = week.getDays().get(i).removeItem(idx);
        if (t != null) {
          int index = week.removeTask(t);
          if (index != -1) {
            this.taskQueue.getItems().remove(index);
          }
        }
        this.progressBars.get(7).setProgress(week.totalTaskProgress());
        this.progressBars.get(i)
            .setProgress((double) week.getDays().get(i).getCompletedTasks()
                / week.getDays().get(i).getTotalTasks());
        selected = true;
      }
    }
    if (selected) {
      this.updateWeeklyStatistics();
      new AlertControl(AlertType.SUCCESS, this.stage,
          "Selected items have been Deleted", false).start();
    } else {
      new AlertControl(AlertType.ERROR, this.stage,
          "Please select item(s) to delete", false).start();
    }
  }

  /**
   * sets the name of the week
   *
   * @param s the name of the week to set to
   */
  public void setName(String s) {
    this.week.setName(s);
    this.weekName.setText(week.getName());
  }

  /**
   * initializes the connections between SceneBuilder and controller
   */
  private void initPbs() {
    this.progressBars.add(pb0);
    this.progressBars.add(pb1);
    this.progressBars.add(pb2);
    this.progressBars.add(pb3);
    this.progressBars.add(pb4);
    this.progressBars.add(pb5);
    this.progressBars.add(pb6);
    this.progressBars.add(pbTask);
  }

  /**
   * initializes the connections between SceneBuilder and controller
   */
  private void initDays() {
    daysGui.add(list0);
    daysGui.add(list1);
    daysGui.add(list2);
    daysGui.add(list3);
    daysGui.add(list4);
    daysGui.add(list5);
    daysGui.add(list6);
  }

  /**
   * clears the display
   */
  private void clear() {
    for (ListView<HBox> box : daysGui) {
      box.getItems().clear();
    }
    for (ProgressBar b : this.progressBars) {
      b.setProgress(0.0);
    }
    taskQueue.getItems().clear();
    this.week = new Week("", 0);
  }

  /**
   * saves the current week to a bujo file
   *
   * @param notify true to show a success message, else just save for no message
   * @param setPw true to save and set a password, else just default save
   */
  public void save(boolean notify, boolean setPw) {
    if (setPw) {
      this.setPassword.setPassword(this.week);
    }
    JsonNode weekJson = JsonUtils.serializeRecord(this.week.convertToJson(false));
    try {
      File f = new File(this.path.toString());
      f.createNewFile();
    } catch (IOException e) {
      throw new IllegalStateException("File failed to be saved");
    }
    JsonUtils.writeToFile(weekJson, this.path);
    if (notify) {
      new AlertControl(AlertType.SUCCESS, this.stage, "File saved successfully", false).start();
    }
  }

  /**
   * saves the file as a template in the template directory
   *
   * @param templateName the name that the user wants to set the week to
   */
  void saveAsTemplate(String templateName) {
    String templateFolder = "./src/main/resources/TEMPLATES/";
    JsonNode weekJson = JsonUtils.serializeRecord(this.week.convertToJson(true));
    try {
      File f = new File(templateFolder + templateName + ".bujo");
      f.createNewFile();
      JsonUtils.writeToFile(weekJson, Path.of(templateFolder + templateName + ".bujo"));
    } catch (IOException e) {
      throw new IllegalStateException("Template failed to be saved");
    }
    new AlertControl(AlertType.SUCCESS, this.stage, "Template saved successfully", false).start();
  }

  /**
   * opens the given file
   *
   * @param p the path of the file to open
   */
  public void openFile(Path p) {
    Week w = JsonUtils.parseFile(p).convertToWeek();
    this.week.combineWeek(w);
    this.updateWeeklyStatistics();
    List<Day> days = this.week.getDays();
    for (int i = 0; i < days.size(); i += 1) {
      List<AbstractJournalItem> itemsToAdd = days.get(i).getItems();
      for (AbstractJournalItem item : itemsToAdd) {
        if (item instanceof Task t) {
          TextFlow tf = new TextFlow();
          Text b = t.getTaskSummary();
          Text start = new Text(t.completeText());
          Text linebreak = new Text(System.lineSeparator());
          tf.getChildren().addAll(b, linebreak, start);
          CheckBox cb = new CheckBox();
          cb.setSelected(t.isComplete());
          checkboxHandler(t, start, cb);
          this.progressBars.get(t.getDay().dayIdx)
              .setProgress((double) week.getDays().get(t.getDay().dayIdx)
                  .getCompletedTasks() / week.getDays().get(t.getDay().dayIdx)
                      .getTotalTasks());
          HBox box = new HBox();
          box.getChildren().addAll(tf);
          HBox right = new HBox();
          right.setSpacing(10);
          TextFlow itemSummary = t.convertToTextFlow();
          right.getChildren().addAll(cb, itemSummary);
          taskQueue.getItems().add(box);
          this.daysGui.get(i).getItems().add(right);
        } else {
          HBox hb = new HBox();
          hb.getChildren().add(item.convertToTextFlow());
          this.daysGui.get(i).getItems().add(hb);
        }
      }
    }
    this.weekName.setText(week.getName());
    this.progressBars.get(7).setProgress(week.totalTaskProgress());
    this.updateWeeklyStatistics();
    this.maxEvents.setText("Max Events: " + week.getCommitmentLevel(ItemType.EVENT));
    this.maxTasks.setText("Max Tasks: " + week.getCommitmentLevel(ItemType.TASK));
  }

  /**
   * helps initialize the checkbox handler
   *
   * @param t the task next to the checkbox
   * @param start the status of the task
   * @param cb  the actual checkbox
   */
  private void checkboxHandler(Task t, Text start, CheckBox cb) {
    cb.setOnAction(e -> {
      t.flipComplete();
      cb.setSelected(t.isComplete());
      start.setText(t.completeText());
      int dayIdx = t.getDay().dayIdx;
      Day d = this.week.getDays().get(dayIdx);
      initCb(t, dayIdx, d, this.progressBars);
      this.progressBars.get(7).setProgress(this.week.totalTaskProgress());
      this.updateWeeklyStatistics();
    });
  }

  /**
   * initializes the checkboxes to update the task queue and progress bars
   *
   * @param t the singular task to put a checkbox next to
   * @param dayIdx the index of the day
   * @param d the specific day of the task
   * @param progressBars the progress bars to update
   */
  static void initCb(Task t, int dayIdx, Day d, List<ProgressBar> progressBars) {
    if (t.isComplete()) {
      d.markTask(true);
      progressBars.get(dayIdx)
          .setProgress((double) d.getCompletedTasks() / d.getTotalTasks());
    } else {
      d.markTask(false);
      progressBars.get(dayIdx)
          .setProgress((double) d.getCompletedTasks() / d.getTotalTasks());
    }
  }
}
