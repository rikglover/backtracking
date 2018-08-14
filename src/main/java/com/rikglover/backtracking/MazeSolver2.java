package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class MazeSolver2 {

  private MazeCell[][] mazeCells;

  private List<MazeCell> solutionInReverse = new ArrayList<>();

  public MazeSolver2(Character[][] gridCells) {
    this.mazeCells = new MazeSolver2.MazeCell[gridCells.length][gridCells[0].length];

    for(int row = 0; row < gridCells.length; row++) {
      for(int column = 0; column < gridCells[0].length; column++) {
        Character mazeCellTypeCode = gridCells[row][column];
        MazeCell.MazeCellType mazeCellType = MazeCell.MazeCellType.fromCode(mazeCellTypeCode);

        mazeCells[row][column] = new MazeSolver2.MazeCell(row, column, mazeCellType);
      }
    }
  }

  public List<MazeCell> findFirstSolution() {
    boolean solutionExists = findFirstSolution(0, 0);

    Collections.reverse(solutionInReverse);

    return solutionInReverse;
  }

  private boolean findFirstSolution(int row, int column) {
    if(row < 0 || row >= mazeCells.length || column < 0 || column >= mazeCells[0].length) {
      return false;
    } else {
      MazeCell mazeCell = mazeCells[row][column];

      if(mazeCell.isVisited() || MazeCell.MazeCellType.WALL.equals(mazeCell.getMazeCellType())) {
        return false;
      } else {
        mazeCell.setVisited(true);

        if (MazeCell.MazeCellType.EXIT.equals(mazeCell.getMazeCellType())
            || findFirstSolution(row - 1, column)
            || findFirstSolution(row + 1, column)
            || findFirstSolution(row, column - 1)
            || findFirstSolution(row, column + 1)) {
          solutionInReverse.add(mazeCell);

          return true;
        }

        return false;
      }
    }
  }

  @Setter
  @Getter
  @EqualsAndHashCode
  @AllArgsConstructor
  public static class MazeCell {

    private int row;
    private int column;
    private MazeCellType mazeCellType;
    private boolean visited;

    public MazeCell(int row, int column, MazeCellType mazeCellType) {
      this(row, column, mazeCellType, false);
    }

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
          throw new IllegalArgumentException("Invalid code for MazeCellType");
        }
      }
    }
  }
}
