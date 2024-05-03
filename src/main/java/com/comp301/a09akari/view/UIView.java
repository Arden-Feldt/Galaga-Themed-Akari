package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public UIView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }

    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox buttonBox = new VBox();
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().clear();

    Label intro = new Label("~=~=~ Ready to play Akari ~=~=~");
    intro.setTextFill(Color.WHITE);
    intro.setFont(Font.font("Comic Sans MS", 24));
    buttonBox.getChildren().add(intro);

    Label puzzleLabel =
        new Label(
            "Puzzle " + (model.getActivePuzzleIndex() + 1) + " of " + model.getPuzzleLibrarySize());
    puzzleLabel.setTextFill(Color.WHITE);
    puzzleLabel.setFont(Font.font("Comic Sans MS", 18));
    buttonBox.getChildren().add(puzzleLabel);

    Button previousPuzzle = new Button("Previous Puzzle");
    previousPuzzle.setFont(Font.font("Comic Sans MS"));
    previousPuzzle.setStyle("-fx-text-fill: white;");
    previousPuzzle.setId("UIButton");
    previousPuzzle.setOnAction((ActionEvent event) -> controller.clickPrevPuzzle());

    Button shuffle = new Button("Shuffle");
    shuffle.setFont(Font.font("Comic Sans MS"));
    shuffle.setStyle("-fx-background-radius: 0;");
    shuffle.setId("UIButton");
    shuffle.setOnAction((ActionEvent event) -> controller.clickRandPuzzle());

    Button reset = new Button("Reset");
    reset.setFont(Font.font("Comic Sans MS"));
    reset.setStyle("-fx-background-radius: 0;");
    reset.setId("UIButton");
    reset.setOnAction((ActionEvent event) -> controller.clickResetPuzzle());

    Button nextPuzzle = new Button("Next Puzzle");
    nextPuzzle.setFont(Font.font("Comic Sans MS"));
    nextPuzzle.setStyle("-fx-background-radius: 0;");
    nextPuzzle.setId("UIButton");
    nextPuzzle.setOnAction((ActionEvent event) -> controller.clickNextPuzzle());

    HBox buttons = new HBox();
    buttons.setAlignment(Pos.CENTER);
    buttons.getStyleClass().add("Header");
    buttons.getChildren().addAll(previousPuzzle, shuffle, reset, nextPuzzle);
    buttonBox.getChildren().add(buttons);

    Label solve = new Label("");
    if (model.isSolved()) {
      solve.setText("You Solved The Puzzle! Try the next one!");
      solve.setTextFill(Color.WHITE);
      solve.setFont(Font.font("Comic Sans MS", 24));
    }
    buttonBox.getChildren().add(solve);

    return buttonBox;
  }
}
