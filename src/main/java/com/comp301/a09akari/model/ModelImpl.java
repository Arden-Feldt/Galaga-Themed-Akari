package com.comp301.a09akari.model;

import com.comp301.a09akari.view.GameView;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

  private final PuzzleLibrary library;
  private int index;
  private final ArrayList<boolean[][]> lampLibrary;
  private final List<ModelObserver> observers;
  private boolean[][] lamps;

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    this.observers = new ArrayList<>();
    this.index = 0;

    this.lampLibrary = new ArrayList<>();

    for (int p = 0; p < library.size(); p++) {
      Puzzle tempPuzzle = library.getPuzzle(p);
      boolean[][] tempLamp = new boolean[tempPuzzle.getHeight()][tempPuzzle.getWidth()];
      for (int i = 0; i < tempPuzzle.getHeight(); i++) {
        for (int j = 0; j < tempPuzzle.getWidth(); j++) {
          tempLamp[i][j] = false;
        }
      }
      lampLibrary.add(tempLamp);
    }
    lamps = lampLibrary.get(index);
  }

  @Override
  public void addLamp(int r, int c) {
    checkBounds(r, c);
    checkType(r, c, CellType.CORRIDOR);

    lamps[r][c] = true;
    update();
  }

  @Override
  public void removeLamp(int r, int c) {
    checkBounds(r, c);
    checkType(r, c, CellType.CORRIDOR);

    lamps[r][c] = false;
    update();
  }

  @Override
  public boolean isLit(int r, int c) {
    checkBounds(r, c);
    checkType(r, c, CellType.CORRIDOR);

    if (isLamp(r, c)) {
      return true;
    }

    return lampInSight(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    checkBounds(r, c);
    checkType(r, c, CellType.CORRIDOR);
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (!lamps[r][c]) {
      throw new IllegalArgumentException();
    }
    return lampInSight(r, c);
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  public void updatePuzzle() {
    library.getPuzzle(index);
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= getPuzzleLibrarySize() || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    this.index = index;
    lamps = lampLibrary.get(index);
    update();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    for (int i = 0; i < library.getPuzzle(index).getHeight(); i++) {
      for (int j = 0; j < library.getPuzzle(index).getWidth(); j++) {
        lamps[i][j] = false;
      }
    }
    update();
  }

  @Override
  public boolean isSolved() {
    // every clue is satisfied AND every corridor is
    // illuminated
    for (int row = 0; row < library.getPuzzle(index).getHeight(); row++) {
      for (int column = 0; column < library.getPuzzle(index).getWidth(); column++) {
        CellType cellType = library.getPuzzle(index).getCellType(row, column);
        if (cellType == CellType.CORRIDOR && !isLit(row, column)) { // all corridors must be lit
          return false;
        } else if (cellType == CellType.CLUE
            && !isClueSatisfied(row, column)) { // all clues must be satisfied
          return false;
        } else if (lamps[row][column] && isLampIllegal(row, column)) { // all lamps must be legal
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    checkBounds(r, c);
    checkType(r, c, CellType.CLUE);

    int clueCount = 0;
    int clue = library.getPuzzle(index).getClue(r, c);

    if (r > 0 && library.getPuzzle(index).getCellType(r - 1, c) == CellType.CORRIDOR) {
      if (isLamp(r - 1, c)) {
        clueCount++;
      }
    }

    if (c > 0 && library.getPuzzle(index).getCellType(r, c - 1) == CellType.CORRIDOR) {
      if (isLamp(r, c - 1)) {
        clueCount++;
      }
    }

    if (r < library.getPuzzle(index).getHeight() - 1
        && library.getPuzzle(index).getCellType(r + 1, c) == CellType.CORRIDOR) {
      if (isLamp(r + 1, c)) {
        clueCount++;
      }
    }

    if (c < library.getPuzzle(index).getWidth() - 1
        && library.getPuzzle(index).getCellType(r, c + 1) == CellType.CORRIDOR) {
      if (isLamp(r, c + 1)) {
        clueCount++;
      }
    }

    return clueCount == clue;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void checkBounds(int r, int c) {
    if (r > library.getPuzzle(index).getHeight() || c > library.getPuzzle(index).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
  }

  private void checkType(int r, int c, CellType cellType) {
    if (library.getPuzzle(index).getCellType(r, c) != cellType) {
      throw new IllegalArgumentException();
    }
  }

  private void update() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  private boolean lampInSight(int r, int c) {
    for (int direction : new int[] {1, -1}) {
      int column = c + direction;
      while (column >= 0
          && column < library.getPuzzle(index).getWidth()
          && library.getPuzzle(index).getCellType(r, column) == CellType.CORRIDOR) {
        if (lamps[r][column]) {
          return true;
        }
        column += direction;
      }
    }
    for (int direction : new int[] {1, -1}) {
      int row = r + direction;
      while (row >= 0
          && row < library.getPuzzle(index).getHeight()
          && library.getPuzzle(index).getCellType(row, c) == CellType.CORRIDOR) {
        if (lamps[row][c]) {
          return true;
        }
        row += direction;
      }
    }
    return false;
  }
}
