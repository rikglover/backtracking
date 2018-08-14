package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class KnightsTour {

  private static final int DEFAULT_NUMBER_OF_ROWS = 8;
  private static final int DEFAULT_NUMBER_OF_COLUMNS = 8;

  private int numberOfRows = DEFAULT_NUMBER_OF_ROWS;
  private int numberOfColumns = DEFAULT_NUMBER_OF_COLUMNS;

  private List<List<ChessBoardPosition>> solutions = new ArrayList<>();

  public KnightsTour(int numberOfRows, int numberOfColumns) {
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
  }

  private Set<ChessBoardPosition> getLegalNextPositions(List<ChessBoardPosition> previousPositions, ChessBoardPosition currentPosition) {
    int row = currentPosition.getRow();
    int column = currentPosition.getColumn();

    ChessBoardPosition forward1Left2 = new ChessBoardPosition(row + 1, column - 2);
    ChessBoardPosition forward1Right2 = new ChessBoardPosition(row + 1, column + 2);
    ChessBoardPosition forward2Left1 = new ChessBoardPosition(row + 2, column - 1);
    ChessBoardPosition forward2Right1 = new ChessBoardPosition(row + 2, column + 1);
    ChessBoardPosition backward1Left2 = new ChessBoardPosition(row - 1, column - 2);
    ChessBoardPosition backward1Right2 = new ChessBoardPosition(row - 1, column + 2);
    ChessBoardPosition backward2Left1 = new ChessBoardPosition(row - 2, column - 1);
    ChessBoardPosition backward2Right1 = new ChessBoardPosition(row - 2, column + 1);

    List<ChessBoardPosition> possiblePositions = Arrays.asList(forward1Left2,
        forward1Right2,
        forward2Left1,
        forward2Right1,
        backward1Left2,
        backward1Right2,
        backward2Left1,
        backward2Right1
    );

    Set<ChessBoardPosition> legalPositions = new HashSet<>();

    for (ChessBoardPosition position : possiblePositions) {
      boolean rowIsOutOfBounds = position.getRow() < 0 || position.getRow() > numberOfRows - 1;
      boolean columnIsOutOfBounds = position.getColumn() < 0 || position.getColumn() > numberOfColumns - 1;
      boolean positionAlreadyUsed = previousPositions.indexOf(position) != -1;

      if (!rowIsOutOfBounds && !columnIsOutOfBounds && !positionAlreadyUsed) {
        legalPositions.add(position);
      }
    }

    return legalPositions;
  }

  private void findSolutions(List<ChessBoardPosition> previousPositions, ChessBoardPosition currentPosition) {
    if (previousPositions.size() == numberOfRows * numberOfColumns ) {
      solutions.add(previousPositions);
    } else {
      Set<ChessBoardPosition> legalNextPositions = getLegalNextPositions(previousPositions, currentPosition);

      for (ChessBoardPosition legalNextPosition : legalNextPositions) {
        List<ChessBoardPosition> partialSolution = new ArrayList<>(previousPositions);

        partialSolution.add(legalNextPosition);
        findSolutions(partialSolution, legalNextPosition);
      }
    }
  }

  public List<List<ChessBoardPosition>> findSolutions() {
    for (int row = 0; row < numberOfRows; row++) {
      for (int column = 0; column < numberOfColumns; column++) {
        ChessBoardPosition firstPosition = new ChessBoardPosition(row, column);

        List<ChessBoardPosition> positions = Arrays.asList(firstPosition);

        findSolutions(positions, firstPosition);
      }
    }

    return solutions;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class ChessBoardPosition {

    private int row;
    private int column;

    @Override
    public String toString() {
      return "(" + row + ", " + column + ")";
    }
  }
}