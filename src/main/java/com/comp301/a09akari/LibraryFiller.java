package com.comp301.a09akari;

import com.comp301.a09akari.model.PuzzleImpl;
import com.comp301.a09akari.model.PuzzleLibraryImpl;

public class LibraryFiller {
  public LibraryFiller() {}

  public PuzzleLibraryImpl fill() {
    PuzzleLibraryImpl puzzleLibrary = new PuzzleLibraryImpl();

    PuzzleImpl puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    puzzleLibrary.addPuzzle(puzzle1);

    PuzzleImpl puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    puzzleLibrary.addPuzzle(puzzle2);

    PuzzleImpl puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    puzzleLibrary.addPuzzle(puzzle3);

    PuzzleImpl puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    puzzleLibrary.addPuzzle(puzzle4);

    PuzzleImpl puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
    puzzleLibrary.addPuzzle(puzzle5);

    return puzzleLibrary;
  }
}
