package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import com.rikglover.backtracking.SudokuSolver.SudokuGridCell;
import java.util.List;
import org.junit.Test;

public class SudokuSolverTest {
  private static final int NUMBER_OF_ROWS = 9;
  private static final int NUMBER_OF_COLUMNS = 9;

  private static final Character[][] ORIGINAL_PUZZLE = {
      { '5', '3', ' ', ' ', '7', ' ', ' ', ' ', ' ' },
      { '6', ' ', ' ', '1', '9', '5', ' ', ' ', ' ' },
      { ' ', '9', '8', ' ', ' ', ' ', ' ', '6', ' ' },
      { '8', ' ', ' ', ' ', '6', ' ', ' ', ' ', '3' },
      { '4', ' ', ' ', '8', ' ', '3', ' ', ' ', '1' },
      { '7', ' ', ' ', ' ', '2', ' ', ' ', ' ', '6' },
      { ' ', '6', ' ', ' ', ' ', ' ', '2', '8', ' ' },
      { ' ', ' ', ' ', '4', '1', '9', ' ', ' ', ' ' },
      { ' ', ' ', ' ', ' ', '8', ' ', ' ', '7', '9' }
  };

  private static final Character[][] SOLVED_PUZZLE = {
      { '5', '3', '4', '6', '7', '8', '9', '1', '2' },
      { '6', '7', '2', '1', '9', '5', '3', '4', '8' },
      { '1', '9', '8', '3', '4', '2', '5', '6', '7' },
      { '8', '5', '9', '7', '6', '1', '4', '2', '3' },
      { '4', '2', '6', '8', '5', '3', '7', '9', '1' },
      { '7', '1', '3', '9', '2', '4', '8', '5', '6' },
      { '9', '6', '1', '5', '3', '7', '2', '8', '4' },
      { '2', '8', '7', '4', '1', '9', '6', '3', '5' },
      { '3', '4', '5', '2', '8', '6', '1', '7', '9' }
  };

  @Test
  public void solvePuzzleTest() {
    SudokuSolver sudokuSolver = new SudokuSolver(ORIGINAL_PUZZLE);
    List<SudokuGridCell[][]> solutions = sudokuSolver.solvePuzzle();
    SudokuGridCell[][] solution = solutions.get(0);
    boolean puzzleSolved = puzzleSolved(solution);

    assertThat(solutions).hasSize(1);
    assertThat(puzzleSolved).isTrue();
  }

  private boolean puzzleSolved(SudokuGridCell[][] actualSolution) {
    for(int row = 0; row < NUMBER_OF_ROWS; row++) {
      for(int column = 0; column < NUMBER_OF_COLUMNS; column++) {
        Character actualValue = actualSolution[row][column].getValue();
        Character expectedValue = SOLVED_PUZZLE[row][column];

        if(!actualValue.equals(expectedValue)) {
          return false;
        }
      }
    }

    return true;
  }
}
