package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SudokuSolver {
  private static final int NUMBER_OF_ROWS = 9;
  private static final int NUMBER_OF_COLUMNS = 9;
  private static final int REGIONS_PER_ROW = 3;
  private static final int REGIONS_PER_COLUMN = 3;
  private static final int ROWS_PER_REGION = 3;
  private static final int COLUMNS_PER_REGION = 3;

  private static final Character[] ALPHABET = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

  private static final Character UNDEFINED = ' ';

  private SudokuGridCell[][] grid = new SudokuGridCell[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

  private List<SudokuGridCell[][]> solutions = new ArrayList<>();

  public SudokuSolver(Character[][] characterGrid) {
    if(characterGrid.length != NUMBER_OF_ROWS || characterGrid[0].length != NUMBER_OF_COLUMNS) {
      throw new IllegalArgumentException("Grid must be a 9x9 grid to be a valid Sudoku grid");
    }

    for(int i = 0; i < NUMBER_OF_ROWS; i++) {
      for(int j = 0; j < NUMBER_OF_COLUMNS; j++) {
        Character value = characterGrid[i][j];

        SudokuGridCell.SudokuGridCellType cellType = value != ' ' ? SudokuGridCell.SudokuGridCellType.PREDEFINED : SudokuGridCell.SudokuGridCellType.USER_DEFINED;

        this.grid[i][j] = new SudokuGridCell(i, j, cellType, value);
      }
    }
  }

  public List<SudokuGridCell[][]> solvePuzzle() {
    solvePuzzle(0, 0);

    return solutions;
  }

  private void solvePuzzle(int row, int column) {
    if(row == NUMBER_OF_ROWS - 1 && column == NUMBER_OF_COLUMNS - 1) {
      SudokuGridCell[][] copyOfGrid = copyGrid(grid);

      solutions.add(copyOfGrid);
    } else {
      int nextColumn = column == NUMBER_OF_COLUMNS - 1 ? 0 : column + 1;
      int nextRow = column == NUMBER_OF_COLUMNS - 1 ?  row + 1 : row;

      if(SudokuGridCell.SudokuGridCellType.PREDEFINED.equals(grid[row][column].getCellType())) {
        solvePuzzle(nextRow, nextColumn);
      } else {
        SudokuGridCell originalGridCell = grid[row][column];

        for(int i = 0; i < ALPHABET.length; i++) {
          Character value = ALPHABET[i];

          SudokuGridCell gridCellToTry = new SudokuGridCell(row, column, SudokuGridCell.SudokuGridCellType.USER_DEFINED, value);

          if(positionValidWithNewCell(gridCellToTry)) {
            grid[row][column] = gridCellToTry;
            solvePuzzle(nextRow, nextColumn);
            grid[row][column] = originalGridCell;
          }
        }
      }
    }
  }

  private SudokuGridCell[][] copyGrid(SudokuGridCell[][] gridToCopy) {
    SudokuGridCell[][] gridCopy = new SudokuGridCell[NUMBER_OF_ROWS][];

    for(int i = 0; i < NUMBER_OF_ROWS; i++) {
      gridCopy[i] = Arrays.copyOf(gridToCopy[i], NUMBER_OF_COLUMNS);
    }

    return gridCopy;
  }

  private boolean positionValidWithNewCell(SudokuGridCell newCell) {
    return rowValidWithNewCell(newCell) && columnValidWithNewCell(newCell) && regionValidWithNewCell(newCell);
  }

  private boolean rowValidWithNewCell(SudokuGridCell newCell) {
    int cellsWithValueCount = 1;
    int row = newCell.getRow();

    Set<Character> gridCellValues = new HashSet<>();

    gridCellValues.add(newCell.getValue());

    for(int column = 0; column < NUMBER_OF_COLUMNS; column++) {
      SudokuGridCell currentGridCell = grid[row][column];

      if(currentGridCell.getValue() != UNDEFINED) {
        cellsWithValueCount += 1;
        gridCellValues.add(currentGridCell.getValue());
      }
    }

    return gridCellValues.size() == cellsWithValueCount;
  }

  private boolean columnValidWithNewCell(SudokuGridCell newCell) {
    int cellsWithValueCount = 1;
    int column = newCell.getColumn();

    Set<Character> gridCellValues = new HashSet<>();

    gridCellValues.add(newCell.getValue());

    for(int row = 0; row < NUMBER_OF_ROWS; row++) {
      SudokuGridCell currentGridCell = grid[row][column];

      if(currentGridCell.getValue() != UNDEFINED) {
        cellsWithValueCount += 1;
        gridCellValues.add(currentGridCell.getValue());
      }
    }

    return gridCellValues.size() == cellsWithValueCount;
  }

  private boolean regionValidWithNewCell(SudokuGridCell newCell) {
    int cellsWithValueCount = 1;
    int startingRow = getStartingRowForRegion(newCell.getRow());
    int startingColumn = getStartingColumnForRegion(newCell.getColumn());

    Set<Character> gridCellValues = new HashSet<>();

    gridCellValues.add(newCell.getValue());

    for(int row = startingRow; row < startingRow + ROWS_PER_REGION; row++) {
      for(int column = startingColumn; column < startingColumn + COLUMNS_PER_REGION; column++) {
        SudokuGridCell currentGridCell = grid[row][column];

        if(currentGridCell.getValue() != UNDEFINED) {
          cellsWithValueCount += 1;
          gridCellValues.add(currentGridCell.getValue());
        }
      }
    }

    return gridCellValues.size() == cellsWithValueCount;
  }

  private int getStartingRowForRegion(int row) {
    return ROWS_PER_REGION * (row / REGIONS_PER_ROW);
  }

  private int getStartingColumnForRegion(int column) {
    return COLUMNS_PER_REGION * (column / REGIONS_PER_COLUMN);
  }

  @Getter
  @Setter
  @AllArgsConstructor(access = AccessLevel.PUBLIC)
  public static class SudokuGridCell {
    int row;
    int column;

    SudokuGridCellType cellType;

    Character value;

    public enum SudokuGridCellType {
      PREDEFINED,
      USER_DEFINED
    }
  }
}
