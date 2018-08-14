package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueens {

  private List<List<Integer>> solutions = new ArrayList<>();

  private boolean positionIsSafe(int row, int column, List<Integer> board) {
    boolean positionIsSafeByRow = board.subList(0, column + 1).stream()
        .filter(x -> x == row)
        .count() == 1;

    if(!positionIsSafeByRow) {
      return false;
    }

    for(int i = 0; i < column; i++) {
      int columnDifference = Math.abs(column - i);
      int otherQueenRow = board.get(i);
      int rowDifference = Math.abs(row - otherQueenRow);

      if(columnDifference == rowDifference) {
        return false;
      }
    }

    return true;
  }

  private void findSolutions(List<Integer> board, int column) {
    if(column >= board.size()) {
      List<Integer> validBoard = new ArrayList<>(board);

      solutions.add(validBoard);
    } else {
      for (int row = 0; row < board.size(); row++) {
        board.set(column, row);

        if (positionIsSafe(row, column, board)) {
          findSolutions(board, column + 1);
        }
      }
    }
  }

  public List<List<Integer>> findSolutions() {
    List<Integer> board = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0);

    findSolutions(board, 0);

    return solutions;
  }

  public void displaySolutions() {
    System.out.println("Found " + solutions.size() + " solutions");

    for(List<Integer> solution : solutions) {
      for(int i = 0 ; i < solution.size(); i++) {
        int columnOfQueen = solution.indexOf(i);

        for(int j = 0; j < solution.size(); j++) {
          char character = (j == columnOfQueen) ? 'Q' : '-';

          System.out.print(character + " ");
        }

        System.out.println();
      }

      System.out.println("\n");
    }
  }
}
