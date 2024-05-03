package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {

  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r > getHeight() || c > getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("getCellType broke as hell");
    }
    int cell = board[r][c];
    if (cell > 6 || cell < 0) {
      throw new IllegalArgumentException("Whats going on here");
    } else if (cell == 6) {
      return CellType.CORRIDOR;
    } else if (cell == 5) {
      return CellType.WALL;
    }
    return CellType.CLUE;
  }

  @Override
  public int getClue(int r, int c) {
    if (r > getHeight() || c > getWidth()) {
      throw new IndexOutOfBoundsException();
    } else if (getCellType(r, c) == CellType.CLUE) {
      return board[r][c];
    }
    throw new IllegalArgumentException();
  }
}
