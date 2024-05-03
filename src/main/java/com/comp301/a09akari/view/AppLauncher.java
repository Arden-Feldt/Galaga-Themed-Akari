package com.comp301.a09akari.view;

import com.comp301.a09akari.LibraryFiller;
import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    stage.setTitle("Akari - Arden Feldt '24");

    LibraryFiller libraryFiller = new LibraryFiller();

    Model model = new ModelImpl(libraryFiller.fill());
    ClassicMvcController controller = new ControllerImpl(model);
    GameView view = new GameView(model, controller);

    Scene scene = view.getScene();
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.show();
  }
}
