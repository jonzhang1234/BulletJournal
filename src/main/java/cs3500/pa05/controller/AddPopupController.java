package cs3500.pa05.controller;

import cs3500.pa05.model.AbstractJournalItem;
import cs3500.pa05.model.AlertType;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.DayOfTheWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.ItemType;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Week;
import cs3500.pa05.view.AddPopup;
import cs3500.pa05.view.GuiView;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * The Controller for the Add Task/Event Popup
 */
public class AddPopupController implements PopupController {
  @FXML
  private Button closeButton;
  @FXML
  private Button submitButton;
  @FXML
  private TextField itemName;
  @FXML
  private TextField itemDescription;
  @FXML
  private ChoiceBox<DayOfTheWeek> itemDay;
  @FXML
  private TextField eventStart;
  @FXML
  private TextField eventDuration;
  private final Week week;
  private final List<ListView<HBox>> days;
  private final ListView<HBox> taskQueue;
  private final List<ProgressBar> progressBars;
  private final ItemType type;
  private final Stage stage;
  private final Stage dialog;
  private final List<Text> weeklyStatus;

  /**
   * Constructor for AddPopupController
   *
   * @param type the type of popup (Task or Event)
   * @param week the week model
   * @param days the days of the gui to update
   * @param taskQueue the task queue
   * @param progressBars all the progress bars
   * @param s the original stage to update
   * @param weeklyStatus the stats of the week
   */
  public AddPopupController(ItemType type, Week week, List<ListView<HBox>> days,
                            ListView<HBox> taskQueue,
                            List<ProgressBar> progressBars, Stage s,
                            List<Text> weeklyStatus) {
    this.days = days;
    this.week = week;
    this.stage = s;
    this.weeklyStatus = weeklyStatus;
    this.taskQueue = taskQueue;
    this.type = type;
    this.progressBars = progressBars;
    this.dialog = new Stage();
    GuiView popupView = new AddPopup(type, this);
    this.dialog.setScene(popupView.load());
  }

  /**
   * initializes the dropdown menu, automatically run
   */
  @FXML
  private void initialize() {
    this.itemDay.getItems().addAll(DayOfTheWeek.SUNDAY, DayOfTheWeek.MONDAY,
        DayOfTheWeek.TUESDAY, DayOfTheWeek.WEDNESDAY,
        DayOfTheWeek.THURSDAY, DayOfTheWeek.FRIDAY,
        DayOfTheWeek.SATURDAY);
  }

  /**
   * starts the popup controller
   */
  @Override
  public void start() {
    closeButton.setOnAction(e -> this.dialog.hide());
    submitButton.setOnAction(e -> {
      if (this.addItem()) {
        this.dialog.close();
      }
    });
  }

  /**
   * updates the weekly statistics
   */
  private void updateWeeklyStatistics() {
    int tasks = 0;
    int events = 0;
    for (Day d : this.week.getDays()) {
      events += d.getItems().size() - d.getTotalTasks();
      tasks += d.getTotalTasks();
    }
    weeklyStatus.get(0).setText(events + " total events");
    weeklyStatus.get(1).setText(tasks + " total tasks");
    weeklyStatus.get(2).setText(Math.round(this.progressBars.get(7)
        .getProgress() * 10000) / 100.0 + "% of tasks completed");
  }

  /**
   * adds either a Task or an Event and updates the gui to reflect it
   *
   * @return true if the item was successfully added
   */
  @FXML
  private boolean addItem() {
    AbstractJournalItem item = null;
    if (this.type == ItemType.TASK) {
      if (itemName.getText().isEmpty() || itemDay.getSelectionModel().isEmpty()) {
        new AlertControl(AlertType.ERROR, this.dialog,
            "Fill in the required inputs (name, day)", false).start();
      } else {
        Task t =  new Task(itemName.getText(), itemDescription.getText(),
            DayOfTheWeek.valueOf(itemDay.getSelectionModel().getSelectedItem().toString()));
        item =  t;
        week.addTask(t);
        TextFlow itemSummary = t.convertToTextFlow();
        Text start = new Text(t.completeText());
        itemSummary.getChildren().add(start);
        Text b = t.getTaskSummary();
        TextFlow tf = new TextFlow();
        tf.getChildren().addAll(b, new Text(System.lineSeparator()), start);
        HBox box = new HBox();
        CheckBox cb = new CheckBox();
        cb.setSelected(t.isComplete());
        checkboxHandler(t, start, cb);
        box.getChildren().addAll(tf);
        HBox hb = new HBox();
        hb.getChildren().addAll(cb, itemSummary);
        hb.setSpacing(10);
        taskQueue.getItems().add(box);
        this.days.get(item.getDay().dayIdx).getItems().add(hb);
        showWarning(item, ItemType.TASK);
        updateSingleProgress(t);
      }
    } else {
      if (itemName.getText().isEmpty() || itemDay.getSelectionModel().isEmpty()
          || eventStart.getText().isEmpty() || eventDuration.getText().isEmpty()) {
        new AlertControl(AlertType.ERROR, this.dialog,
            "Fill in the required inputs (name, day, start time, duration)", false).start();
      } else {
        try {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
          LocalTime duration = LocalTime.parse(eventDuration.getText(), formatter);
          if (duration.toString().equals("00:00")) {
            throw new IllegalArgumentException("Invalid Duration");
          }
          item =  new Event(itemName.getText(), itemDescription.getText(),
              DayOfTheWeek.valueOf(itemDay.getSelectionModel().getSelectedItem().toString()),
              LocalTime.parse(eventStart.getText(), formatter),
              duration);
          HBox hb = new HBox();
          hb.getChildren().add(item.convertToTextFlow());
          this.days.get(item.getDay().dayIdx).getItems().add(hb);
          showWarning(item, this.type);
        } catch (DateTimeParseException | IllegalArgumentException e) {
          new AlertControl(AlertType.ERROR, this.dialog,
              "Provide a valid start time and duration in the format H:mm", false).start();
        }
      }
    }
    if (item != null) {
      this.progressBars.get(7).setProgress(week.totalTaskProgress());
      this.updateWeeklyStatistics();
      this.clearFields();
      return true;
    }
    return false;
  }

  /**
   * shows the exceeded warning if valid
   *
   * @param item the item that may exceed
   * @param task the type of item
   */
  private void showWarning(AbstractJournalItem item, ItemType task) {
    if (week.addItem(item, task)) {
      new AlertControl(AlertType.WARNING, this.stage, item.getDay()
          + " HAS EXCEEDED YOUR SET LEVEL OF "
          + this.type.toString().toUpperCase() + "S", true).start();
    }
  }

  /**
   * updates the progress bar for a task
   *
   * @param t the task for progress
   */
  private void updateSingleProgress(Task t) {
    this.progressBars.get(t.getDay().dayIdx)
        .setProgress((double) week.getDays().get(t.getDay().dayIdx)
            .getCompletedTasks()
            / week.getDays().get(t.getDay().dayIdx).getTotalTasks());
  }

  /**
   * helper for the checkbox handler
   *
   * @param t the specific task's checkbox
   * @param start the completion status of the task
   * @param cb the actual checkbox
   */
  private void checkboxHandler(Task t, Text start, CheckBox cb) {
    cb.setOnAction(e -> {
      t.flipComplete();
      cb.setSelected(t.isComplete());
      start.setText(t.completeText());
      int dayIdx = t.getDay().dayIdx;
      JournalControllerImpl.initCb(t, dayIdx, week.getDays().get(dayIdx), this.progressBars);
      this.progressBars.get(7).setProgress(week.totalTaskProgress());
      this.updateWeeklyStatistics(); // checkbox updates the statistics
    });
  }

  /**
   * Clears the input fields after form submission
   */
  private void clearFields() {
    this.itemName.clear();
    this.itemDescription.clear();
    this.itemDay.setValue(null);
    if (this.type == ItemType.EVENT) {
      this.eventStart.clear();
      this.eventDuration.clear();
    }
  }

  /**
   * shows the Popup
   */
  @FXML
  public void showHandler() {
    this.dialog.show();
  }
}
