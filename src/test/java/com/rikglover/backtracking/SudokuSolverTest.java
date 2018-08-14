package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import com.rikglover.backtracking.SudokuSolver.SudokuGridCell;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class SudokuSolverTest {
  private static final int NUMBER_OF_ROWS = 9;
  private static final int NUMBER_OF_COLUMNS = 9;
  private static final int REGIONS_PER_ROW = 3;
  private static final int REGIONS_PER_COLUMN = 3;
  private static final int ROWS_PER_REGION = 3;
  private static final int COLUMNS_PER_REGION = 3;
  private static final int CELLS_PER_REGION = ROWS_PER_REGION * COLUMNS_PER_REGION;

  private static final int[] REGION_STARTING_INDICES = { 0, 3, 6};

  private static final Character[] ALPHABET_ARRAY = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
  private static final List<Character> ALPHABET_LIST = Arrays.asList(ALPHABET_ARRAY);
  private static final Set<Character> ALPHABET_SET = new HashSet<>(ALPHABET_LIST);

  private static final Character UNDEFINED = ' ';

  Character[][] puzzle = {
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

  @Test
  public void solvePuzzleTest() {
    SudokuSolver sudokuSolver = new SudokuSolver(puzzle);

    List<SudokuGridCell[][]> solutions = sudokuSolver.solvePuzzle();

    SudokuGridCell[][] solution = solutions.get(0);

    for(int i = 0; i < NUMBER_OF_ROWS; i++) {
      for(int j = 0; j < NUMBER_OF_COLUMNS; j++) {
        System.out.print(solution[i][j].getValue() + " ");
      }

      System.out.println();
    }

    for(SudokuGridCell[][] currentSolution : solutions) {
      boolean puzzleSolved = puzzleSolved(currentSolution);

      assertThat(puzzleSolved).isEqualTo(true);
    }
  }

  private boolean puzzleSolved(SudokuGridCell[][] grid) {
    for(int row = 0; row < NUMBER_OF_ROWS; row++) {
      for(int column = 0; column < NUMBER_OF_COLUMNS; column++) {
        boolean rowSolved = rowSolved(row, grid);
        boolean columnSolved = columnSolved(column, grid);

        if(!rowSolved || !columnSolved) {
          return false;
        }
      }
    }

    for(int i = 0; i < REGION_STARTING_INDICES.length; i++) {
      int startingRowIndex = REGION_STARTING_INDICES[i];

      for(int j = 0; j < REGION_STARTING_INDICES.length; j++) {
        int startingColumnIndex = REGION_STARTING_INDICES[j];

        boolean regionSolved = regionSolved(startingRowIndex, startingColumnIndex, grid);

        if(!regionSolved) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean rowSolved(int row, SudokuGridCell[][] grid) {
    Set<Character> gridCellValues = new HashSet<>();

    for(int column = 0; column < NUMBER_OF_COLUMNS; column++) {
      SudokuGridCell currentGridCell = grid[row][column];

      if(ALPHABET_SET.contains(currentGridCell.getValue())) {
        gridCellValues.add(currentGridCell.getValue());
      }
    }

    return gridCellValues.size() == NUMBER_OF_ROWS;
  }

  private boolean columnSolved(int column, SudokuGridCell[][] grid) {
    Set<Character> gridCellValues = new HashSet<>();

    for(int row = 0; row < NUMBER_OF_ROWS; row++) {
      SudokuGridCell currentGridCell = grid[row][column];

      if(ALPHABET_SET.contains(currentGridCell.getValue())) {
        gridCellValues.add(currentGridCell.getValue());
      }
    }

    return gridCellValues.size() == NUMBER_OF_COLUMNS;
  }

  private boolean regionSolved(int row, int column, SudokuGridCell[][] grid) {
    int startingRow = getStartingRowForRegion(row);
    int startingColumn = getStartingColumnForRegion(column);

    Set<Character> gridCellValues = new HashSet<>();

    for(int i = startingRow; i < startingRow + ROWS_PER_REGION; i++) {
      for(int j = startingColumn; j < startingColumn + COLUMNS_PER_REGION; j++) {
        SudokuGridCell currentGridCell = grid[i][j];

        if(ALPHABET_SET.contains(currentGridCell.getValue())) {
          gridCellValues.add(currentGridCell.getValue());
        }
      }
    }

    return gridCellValues.size() == CELLS_PER_REGION;
  }

  private int getStartingRowForRegion(int row) {
    return ROWS_PER_REGION * (row / REGIONS_PER_ROW);
  }

  private int getStartingColumnForRegion(int column) {
    return COLUMNS_PER_REGION * (column / REGIONS_PER_COLUMN);
  }
}
