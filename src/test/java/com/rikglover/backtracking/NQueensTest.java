package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import java.util.HashSet;
import java.util.List;

public class NQueensTest {

  @Test
  public void findSolutions() throws Exception {
    NQueens nQueens = new NQueens();

    List<List<Integer>> solutions = nQueens.findSolutions();

    for(List<Integer> solution : solutions) {
      boolean isValidSolution = isValidSolution(solution);

      assertThat(isValidSolution).isTrue();
    }
  }

  private boolean isValidSolution(List<Integer> solution) {
    boolean safeByRow = new HashSet<>(solution).size() == solution.size();

    if(!safeByRow) {
      return false;
    }

    for(int column = 0; column < solution.size() - 1; column++) {
      int queensIndex = solution.get(column);

      for(int otherColumn = 0; otherColumn < solution.size() - 1; otherColumn++) {
        if(column != otherColumn) {
          int otherQueensIndex = solution.get(otherColumn);
          int queensIndexDifference = Math.abs(otherQueensIndex - queensIndex);
          int columnDifference = Math.abs(otherColumn - column);

          if(queensIndexDifference == columnDifference) {
            return false;
          }
        }
      }
    }

    return true;
  }
}
