package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MazeSolver {

  private MazeCell[][] mazeCells;

  private List<List<MazeCell>> solutions = new ArrayList<>();

  public MazeSolver(Character[][] gridCells) {
    this.mazeCells = new MazeCell[gridCells.length][gridCells[0].length];

    for(int row = 0; row < gridCells.length; row++) {
      for(int column = 0; column < gridCells[0].length; column++) {
        Character mazeCellTypeCode = gridCells[row][column];
        MazeCell.MazeCellType mazeCellType = MazeCell.MazeCellType.fromCode(mazeCellTypeCode);

        mazeCells[row][column] = new MazeCell(row, column, mazeCellType);
      }
    }
  }

  public List<List<MazeCell>> findSolution() {
    List<MazeCell> path = new ArrayList<>();
    MazeCell startingCell = getMazeCell(0, 0);

    path.add(startingCell);
    findSolution(path);

    return solutions;
  }

  private void findSolution(List<MazeCell> mazeCellsVisited) {
    MazeCell currentCell = mazeCellsVisited.get(mazeCellsVisited.size() - 1);

    if(MazeCell.MazeCellType.EXIT.equals(currentCell.getMazeCellType())) {
      solutions.add(mazeCellsVisited);
    } else {
      List<MazeCell> validNextMazeCells = getValidNextMazeCells(currentCell, mazeCellsVisited);

      for(MazeCell validNextMazeCell : validNextMazeCells) {
        List<MazeCell> currentPath = new ArrayList<>(mazeCellsVisited);

        currentPath.add(validNextMazeCell);
        findSolution(currentPath);
      }
    }
  }

  private void addMazeCellIfValid(int row, int column, List<MazeCell> validNextMazeCells, Set<MazeCell> previousCells) {
    MazeCell mazeCell = getMazeCell(row, column);
    MazeCell.MazeCellType mazeCellType = mazeCell.getMazeCellType();

    if(!MazeCell.MazeCellType.WALL.equals(mazeCellType) && !previousCells.contains(mazeCell)) {
      validNextMazeCells.add(mazeCell);
    }
  }

  private List<MazeCell> getValidNextMazeCells(MazeCell currentMazeCell, List<MazeCell> currentPath) {
    List<MazeCell> validNextMazeCells = new ArrayList<>();

    int currentRow = currentMazeCell.getRow();
    int currentColumn = currentMazeCell.getColumn();
    int totalRows = getTotalRows();
    int totalColumns = getTotalColumns();

    Set<MazeCell> previousCells = new HashSet<>(currentPath);

    if(currentRow >= 1) {
      addMazeCellIfValid(currentRow - 1, currentColumn, validNextMazeCells, previousCells);
    }

    if(currentRow < totalRows - 1) {
      addMazeCellIfValid(currentRow + 1, currentColumn, validNextMazeCells, previousCells);
    }

    if(currentColumn >= 1) {
      addMazeCellIfValid(currentRow, currentColumn - 1, validNextMazeCells, previousCells);
    }

    if(currentColumn < totalColumns - 1) {
      addMazeCellIfValid(currentRow, currentColumn + 1, validNextMazeCells, previousCells);
    }

    return validNextMazeCells;
  }

  private MazeCell getMazeCell(int row, int column) {
    return mazeCells[row][column];
  }

  private int getTotalRows() {
    return mazeCells.length;
  }

  private int getTotalColumns() {
    return mazeCells[0].length;
  }

  @Setter
  @Getter
  @EqualsAndHashCode
  @AllArgsConstructor
  public static class MazeCell {

    private int row;
    private int column;
    private MazeCellType mazeCellType;

    @Override
    public String toString() {
      return "(" + row + ", " + column + ")";
    }

    @AllArgsConstructor
    public enum MazeCellType {
      ENTRANCE('E'),
      EXIT('X'),
      WALL('W'),
      TUNNEL('T');

      Character code;

      public static MazeCellType fromCode(Character code) {
        if(code == ENTRANCE.code) {
          return ENTRANCE;
        } else if(code == EXIT.code) {
          return EXIT;
        } else if(code == WALL.code) {
          return WALL;
        } else if(code == TUNNEL.code) {
          return TUNNEL;
        } else {
          throw new IllegalArgumentException("Invalid code");
        }
      }
    }
  }
}
