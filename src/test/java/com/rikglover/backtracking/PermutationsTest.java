package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class PermutationsTest {

  private Object[][] getParameters() {
    return new Object[][] {
        { "ab", 2 },
        { "abc", 6 },
        { "abcdef", 720 },
        { "aa", 2 },
        { "abb", 6 },
        { "banana", 720 }
    };
  }

  @Test
  @Parameters(method = "getParameters")
  public void findSolutionsNoRepeats(String testString, int solutionCount) throws Exception {
    Permutations2 permutations = new Permutations2(testString);
    List<String> solutions = permutations.findSolutions();

    assertThat(solutions).hasSize(solutionCount);
  }

  @Test
  public void findSolutionsTest() throws Exception {
    Permutations2 permutations = new Permutations2("aa");

    List<String> solutions = permutations.findSolutions();

    assertThat(solutions).hasSize(2);
  }
}

