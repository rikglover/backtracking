package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class PowerSetTest {
  private Object[][] getParameters() {
    return new Object[][] {
        { "ab", 4 },
        { "abc", 8 },
        { "abcd", 16 },
        { "abcde", 32 },
        { "abcdef", 64 }
    };
  }

  @Test
  @Parameters(method = "getParameters")
  public void testPowerSet(String testString, int solutionCount) throws Exception {
    List<Character> input = testString.chars().mapToObj(x -> (char) x).collect(Collectors.toList());
    PowerSet powerSet = new PowerSet(input);
    List<List<Character>> powerSets = powerSet.findSolutions();

    assertThat(powerSets).hasSize(solutionCount);
  }

  @Test
  @Parameters(method = "getParameters")
  public void testPowerSet2(String testString, int solutionCount) throws Exception {
    Set<Character> input = testString.chars().mapToObj(x -> (char) x).collect(Collectors.toSet());
    PowerSet2 powerSet2 = new PowerSet2(input);
    Set<Set<Character>> powerSets = powerSet2.findSolutions();

    assertThat(powerSets).hasSize(solutionCount);
  }
}
