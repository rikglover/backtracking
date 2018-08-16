package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import com.rikglover.backtracking.KnightsTour.ChessBoardPosition;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnitParamsRunner.class)
public class KnightsTourTest {

  // Values from the following url:
  // https://www.programming-algorithms.net/article/39907/Knight's-tour
  private static Object[] getParameters() {
    return new Integer[][] {
        { 3, 1, 0 },
        { 3, 2, 0 },
        { 3, 3, 0 },
        { 3, 4, 16 },
        { 3, 5, 0 },
        { 3, 6, 0 },
        { 3, 7, 104 },
        { 3, 8, 792 },
        { 3, 9, 1120 },
        { 3, 10, 6096 }
    };
  }

  @Test
  @Parameters(method = "getParameters")
  public void testFindSolutions3By7(int rows, int columns, int numSolutions) throws Exception {
    KnightsTour knightsTour = new KnightsTour(rows, columns);

    List<List<ChessBoardPosition>> solutions = knightsTour.findSolutions();

    boolean solutionsDistinct = solutionsDistinct(solutions);

    assertThat(solutionsDistinct).isTrue();
    assertThat(solutions).hasSize(numSolutions);

    for(List<ChessBoardPosition> solution : solutions) {
      boolean solutionValid = isSolutionValid(solution, rows, columns);

      assertThat(solutionValid).isTrue();
    }
  }

  private boolean isSolutionValid(List<ChessBoardPosition> solution, int totalRows, int totalColumns) {
    Set<ChessBoardPosition> solutionSet = new HashSet<>(solution);
    int expectedSolutionSize = totalRows * totalColumns;

    return solutionSet.size() == expectedSolutionSize && solution.size() == expectedSolutionSize;
  }

  private String toSolutionString(List<ChessBoardPosition> solution) {
    return solution.stream()
        .map(ChessBoardPosition::toString)
        .collect(Collectors.joining(", "));
  }

  private boolean solutionsDistinct(List<List<ChessBoardPosition>> solutions) {
    Set<String> solutionStrings = solutions.stream()
        .map(this::toSolutionString)
        .collect(Collectors.toSet());

    return solutions.size() == solutionStrings.size();
  }
}