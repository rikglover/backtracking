package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Combinations3 {
  private String alphabet;

  private List<String> solutions = new ArrayList<>();

  public Combinations3(String inputString) {
    this.alphabet = inputString;
  }

  private void findSolutions(int index) {
    if(index < alphabet.length()) {
      Character nextCharacter = alphabet.charAt(index);

      final int size = solutions.size();

      for (int i = 0; i < size; i++) {
        solutions.add(solutions.get(i) + nextCharacter);
      }

      findSolutions(index + 1);
    }
  }

  public List<String> findSolutions() {
    solutions.add("");
    findSolutions(0);
    solutions.remove("");

    return solutions;
  }
}
