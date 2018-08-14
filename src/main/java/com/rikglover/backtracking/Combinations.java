package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Combinations {
  private String inputString;

  private Set<String> solutions = new HashSet<>();

  public Combinations(String inputString) {
    this.inputString = inputString;
  }

  private String getSortedString(String input) {
    char[] proposedSolutionAsArray = input.toCharArray();

    Arrays.sort(proposedSolutionAsArray);

    return String.valueOf(proposedSolutionAsArray);
  }

  private void findSolutions(String proposedSolution, String alphabet) {
    String sortedProposedSolution = getSortedString(proposedSolution);

    solutions.add(sortedProposedSolution);

    if (alphabet.length() > 0) {
      Character next = alphabet.charAt(0);

      List<String> solutionList = new ArrayList<>(solutions);

      for (String solution : solutionList) {
        if(solution.indexOf(next) == -1) {
          findSolutions(solution + next, alphabet.substring(1));
        }
      }
    }
  }

  public Set<String> findSolutions() {
    findSolutions("", inputString);
    solutions.remove("");

    return solutions;
  }
}
