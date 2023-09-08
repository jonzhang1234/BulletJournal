package cs3500.pa05;

import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.controller.LoadingScreenController;
import cs3500.pa05.controller.PrivacyController;
import cs3500.pa05.controller.WelcomeScreenController;
import cs3500.pa05.view.LoadingScreenView;
import cs3500.pa05.view.PrivacyView;
import cs3500.pa05.view.TaskView;
import cs3500.pa05.view.WelcomeScreenView;
import java.io.File;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * the FX entry point launcher
 */
public class JournalLauncher extends Application {
  private Stage stage;

  /**
   * starts the Application
   *
   * @param stage the primary stage for this application, onto which
   *              the application scene can be set.
   *              Applications may create other stages, if needed, but they will not be
   *              primary stages.
   */
  @Override
  public void start(Stage stage) {
    this.stage = stage;
    LoadingScreenController lsc = new LoadingScreenController(stage, this);
    LoadingScreenView lsv = new LoadingScreenView(lsc);
    stage.setScene(lsv.load());
    stage.show();
    lsc.start();
  }

  /**
   * loads the welcome screen
   */
  public void loadWelcomeScreen() {
    WelcomeScreenController welcome = new WelcomeScreenController(stage, this);
    WelcomeScreenView wsv = new WelcomeScreenView(welcome);
    stage.setScene(wsv.load());
    welcome.start();
    stage.centerOnScreen();
  }

  /**
   * notifies the launcher to launch the app
   *
   * @param f          the file to launch
   * @param isNewFile  whether the file has been seen before
   * @param weekName   the name of the week
   * @param horizontal the orientation of the layout
   * @param template   the template to apply
   */
  public void notifyLauncher(File f, boolean isNewFile, String weekName, int horizontal,
                             File template) {
    JournalControllerImpl control =
        new JournalControllerImpl(stage, f, isNewFile, this, weekName, horizontal);
    TaskView tv = new TaskView(control, horizontal);
    stage.setTitle("Journal");
    Scene weekView = tv.load();
    stage.setScene(weekView);
    weekView.setOnKeyPressed(new EventHandler<>() {
      final KeyCombination keyComb = new KeyCodeCombination(KeyCode.L,
          KeyCombination.SHORTCUT_DOWN);

      @Override
      public void handle(KeyEvent event) {
        if (keyComb.match(event)) {
          event.consume();

          control.save(false, false);
          notifyLauncher(f, false, weekName, horizontal + 1, null);
        }
      }
    });
    control.initKeybindings(weekView);
    control.start();
    if (template != null) {
      control.openFile(template.toPath());
      control.setName(weekName);
    }
  }

  /**
   * goes back to the home screen
   */
  public void goHome() {
    this.loadWelcomeScreen();
  }

  /**
   * initializes the password
   *
   * @param pw the password
   * @param f  the file to set the password to
   */
  public void initPassword(String pw, File f, int orientation) {
    PrivacyController pc = new PrivacyController(this.stage, pw, f, this, orientation);
    PrivacyView pv = new PrivacyView(pc);
    Scene weekView = pv.load();
    stage.setScene(weekView);
    pc.start();
  }
}
