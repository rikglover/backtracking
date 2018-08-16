package com.rikglover.backtracking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
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

  private List<ChessBoardPosition> getPossibleNextPositions(Deque<ChessBoardPosition> previousPositions) {
    ChessBoardPosition currentPosition = previousPositions.getLast();

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

    return Arrays.asList(forward1Left2,
        forward1Right2,
        forward2Left1,
        forward2Right1,
        backward1Left2,
        backward1Right2,
        backward2Left1,
        backward2Right1
    );
  }

  private boolean positionIsLegal(ChessBoardPosition position, Deque<ChessBoardPosition> previousPositions) {
    boolean rowIsOutOfBounds = position.getRow() < 0 || position.getRow() > numberOfRows - 1;
    boolean columnIsOutOfBounds = position.getColumn() < 0 || position.getColumn() > numberOfColumns - 1;
    boolean positionAlreadyUsed = previousPositions.contains(position);

    return !rowIsOutOfBounds && !columnIsOutOfBounds && !positionAlreadyUsed;
  }

  private void findSolutions(Deque<ChessBoardPosition> previousPositions) {
    if (previousPositions.size() == numberOfRows * numberOfColumns ) {
      List<ChessBoardPosition> solution = new ArrayList<>(previousPositions);

      solutions.add(solution);
    } else {
      List<ChessBoardPosition> legalNextPositions = getPossibleNextPositions(previousPositions);

      for (ChessBoardPosition position : legalNextPositions) {
        if(positionIsLegal(position, previousPositions)) {
          previousPositions.addLast(position);
          findSolutions(previousPositions);
          previousPositions.removeLast();
        }
      }
    }
  }

  public List<List<ChessBoardPosition>> findSolutions() {
    for (int row = 0; row < numberOfRows; row++) {
      for (int column = 0; column < numberOfColumns; column++) {
        Deque<ChessBoardPosition> positions = new ArrayDeque<>();
        ChessBoardPosition firstPosition = new ChessBoardPosition(row, column);

        positions.addLast(firstPosition);
        findSolutions(positions);
        positions.removeLast();
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