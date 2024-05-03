package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameView implements FXComponent, ModelObserver {
  private final Scene scene;
  private final BoardView puzzle;
  private final UIView header;

  public GameView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }

    this.puzzle = new BoardView(model, controller);
    this.header = new UIView(model, controller);
    this.scene = new Scene(render(), 700, 750);
    model.addObserver(this);
  }

  @Override
  public Parent render() {
    BorderPane rootPane = new BorderPane();
    rootPane.setTop(header.render());
    rootPane.setCenter(puzzle.render());
    rootPane.setBackground(new Background(backgroundImage()));

    // rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    return rootPane;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }

  public Scene getScene() {
    return scene;
  }

  private BackgroundImage backgroundImage() {
    Image backgroundImage = new Image("galaga_backgroud.jpg");
    return new BackgroundImage(
        backgroundImage,
        BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT,
        BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
  }
}
