package cs3500.pa05.controller;

import cs3500.pa05.JournalLauncher;
import cs3500.pa05.view.GuiView;
import cs3500.pa05.view.LoadingScreenView;
import javafx.animation.PauseTransition;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the Splash screen
 */
public class LoadingScreenController implements JournalScreenController {
  private final Popup popup;
  private final Stage stage;
  private final JournalLauncher impl;

  /**
   * The LoadingScreenController constructor
   *
   * @param s the stage to display the loading screen on
   * @param impl the launcher that launched the controller
   */
  public LoadingScreenController(Stage s, JournalLauncher impl) {
    this.impl = impl;
    this.popup = new Popup();
    GuiView loadingScreen = new LoadingScreenView(this);
    this.popup.getContent().add(loadingScreen.load().getRoot());
    this.stage = s;
  }

  /**
   * Starts the LoadingScreenController
   */
  @Override
  public void start() {
    PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
    delay.setOnFinished(e -> {
      popup.hide();
      impl.loadWelcomeScreen();
    });
    this.popup.show(stage);
    delay.play();
    this.popup.setX(stage.getX() + (stage.getWidth() - this.popup.getWidth()) / 2);
    this.popup.setY(stage.getY());
  }
}
