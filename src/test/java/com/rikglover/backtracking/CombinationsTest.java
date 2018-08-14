package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class CombinationsTest {

  private Object[][] getParameters() {
    return new Object[][] {
//        { "ab", 3 },
        { "abcd", 7 },
//        { "abcdef", 63 },
//        { "aa", 3 },
//        { "abb", 7 },
//        { "banana", 63 },
//        { "bbbbbb", 63 },
//        { "bananas", 127 }
    };
  }

  private boolean containsDistinctCharacters(String input) {
    long numberOfDistinctCharacters = input.chars()
        .mapToObj(x -> (char) x)
        .distinct()
        .count();

    return input.length() == numberOfDistinctCharacters;
  }

  private String getSortedString(String input) {
    return input.chars()
        .mapToObj(x -> (char) x)
        .sorted()
        .map(x -> "" + x)
        .collect(Collectors.joining());
  }

  private List<String> getStandardSolutionFormat(Collection<String> solutions) {
    int size = solutions.size();

    List<String> standardSolutions = new ArrayList<>(solutions);

    for(int i = 0; i < size; i++) {
      String standardSolution = getSortedString(standardSolutions.get(i));

      standardSolutions.set(i, standardSolution);
    }

    Collections.sort(standardSolutions);

    return standardSolutions;
  }

  @Test
  @Parameters(method = "getParameters")
  public void findSolutionsNoRepeatsCombinations(String testString, int solutionCount) throws Exception {
    Combinations combinations = new Combinations(testString);

    Set<String> solutions = combinations.findSolutions();
  }

  @Test
  @Parameters(method = "getParameters")
  public void findSolutionsNoRepeatsCombinations2(String testString, int solutionCount) throws Exception {
    Combinations2 combinations2 = new Combinations2(testString);
    List<String> solutions = combinations2.findSolutions();

    assertThat(solutions).hasSize(solutionCount);
  }

  @Test
  @Parameters(method = "getParameters")
  public void findSolutionsNoRepeatsCombinations3(String testString, int solutionCount) throws Exception {
    Combinations3 combinations3 = new Combinations3(testString);
    List<String> solutions = combinations3.findSolutions();

    assertThat(solutions).hasSize(solutionCount);
  }

  @Test
  @Parameters(method = "getParameters")
  public void findSolutionsTestCombinations4(String testString, int solutionCount) throws Exception {
    Combinations4 combinations4 = new Combinations4(testString);

    List<String> solutions = combinations4.combine();

    assertThat(solutions).hasSize(solutionCount);
  }

  @Test
  @Parameters(method = "getParameters")
  public void compareSolutions(String testString, int solutionCount) throws Exception {
    Combinations2 combinations2 = new Combinations2(testString);
    Combinations3 combinations3 = new Combinations3(testString);
    Combinations4 combinations4 = new Combinations4(testString);

    List<String> solutions2 = combinations2.findSolutions();
    List<String> solutions3 = combinations3.findSolutions();
    List<String> solutions4 = combinations4.combine();

    List<String> standardSolution2 = getStandardSolutionFormat(solutions2);
    List<String> standardSolution3 = getStandardSolutionFormat(solutions3);
    List<String> standardSolution4 = getStandardSolutionFormat(solutions4);

    assertThat(standardSolution2).isEqualTo(standardSolution4);
    assertThat(standardSolution3).isEqualTo(standardSolution4);
    assertThat(standardSolution4).isEqualTo(standardSolution4);
  }
}

