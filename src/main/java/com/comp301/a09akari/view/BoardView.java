package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BoardView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public BoardView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.getChildren().clear();

    int rowsBoundary = model.getActivePuzzle().getHeight();
    int columnsBoundary = model.getActivePuzzle().getWidth();

    for (int rows = 0; rows < rowsBoundary; rows++) {
      for (int columns = 0; columns < columnsBoundary; columns++) {
        StackPane cell = new StackPane();
        cell.setAlignment(Pos.CENTER);
        cell.getStyleClass().add("cell");
        Rectangle cellDisplay = new Rectangle(60, 60);

        if (model.getActivePuzzle().getCellType(rows, columns) == CellType.CORRIDOR) {
          if (model.isLit(rows, columns)) {
            if (model.isLamp(rows, columns) && model.isLampIllegal(rows, columns)) {
              cellDisplay.setFill(Color.RED);
              cell.getChildren().add(cellDisplay);
              ImageView lamp = lampImage();
              cell.getChildren().add(lamp);
            } else if (model.isLamp(rows, columns)) {
              cellDisplay.setFill(Color.MEDIUMPURPLE);
              cell.getChildren().add(cellDisplay);
              ImageView lamp = lampImage();
              cell.getChildren().add(lamp);
            } else {
              cellDisplay.setFill(Color.MEDIUMPURPLE);
              cell.getChildren().add(cellDisplay);
            }
          } else {
            cellDisplay.setFill(Color.WHITE);
            cell.getChildren().add(cellDisplay);
          }

          int currentRow = rows;
          int currentColumn = columns;

          Button lampControl = new Button();
          lampControl.setPrefHeight(60);
          lampControl.setPrefWidth(60);
          lampControl.getStyleClass().add("lampControl");
          lampControl.setOnAction(
              (ActionEvent event) -> controller.clickCell(currentRow, currentColumn));
          cell.getChildren().add(lampControl);
        } else if (model.getActivePuzzle().getCellType(rows, columns) == CellType.CLUE) {
          if (model.isClueSatisfied(rows, columns)) {
            cellDisplay.setFill(Color.DARKORCHID);
          } else {
            cellDisplay.setFill(Color.DARKORANGE);
          }
          Label clueNumber = new Label("" + model.getActivePuzzle().getClue(rows, columns));
          clueNumber.setTextFill(Color.WHITE);
          clueNumber.getStyleClass().add("clueNumber");
          clueNumber.setFont(Font.font("Comic Sans MS"));
          cell.getChildren().addAll(cellDisplay, clueNumber);
        } else if (model.getActivePuzzle().getCellType(rows, columns) == CellType.WALL) {
          ImageView wall = wallImage();
          cellDisplay.setFill(Color.BLACK);
          cell.getChildren().add(cellDisplay);
          cell.getChildren().add(wall);

        } else {
          throw new IllegalStateException();
        }
        grid.add(cell, columns, rows);
      }
    }
    return grid;
  }

  private ImageView lampImage() {
    Image image = new Image("galaga ship.png");
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    imageView.setPreserveRatio(true);
    return imageView;
  }

  private ImageView wallImage() {
    Image image = new Image("wall.jpg");
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    imageView.setPreserveRatio(true);
    return imageView;
  }

  private ImageView treasureImage() {
    Image image = new Image("treasure_wall.png");
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    imageView.setPreserveRatio(true);
    return imageView;
  }
}
