package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import com.rikglover.backtracking.KnightsTour.ChessBoardPosition;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnitParamsRunner.class)
public class KnightsTourTest {

  private static final Object[] getParameters() {
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
        { 3, 10, 6096 },
        { 3, 11, 21344 },
        { 3, 12, 114496 },
        { 3, 13, 257728 }
    };
  }

  @Test
  @Parameters(method = "getParameters")
  public void testFindSolutions3By7(int rows, int columns, int numSolutions) throws Exception {
    KnightsTour knightsTour = new KnightsTour(rows, columns);

    List<List<ChessBoardPosition>> solutions = knightsTour.findSolutions();

    assertThat(solutions).hasSize(numSolutions);
  }
}