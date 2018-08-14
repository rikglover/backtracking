package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Combinations2 {
  private String alphabet;

  private List<String> solutions = new ArrayList<>(Collections.singletonList(""));

  public Combinations2(String inputString) {
    this.alphabet = inputString;
  }

  public List<String> findSolutions() {
    for(int i = 0; i < alphabet.length(); i++) {
      Character nextCharacter = alphabet.charAt(i);

      int size = solutions.size();

      for(int j = 0; j < size; j++) {
        String solution = solutions.get(j) + nextCharacter;

        solutions.add(solution);
      }
    }

    solutions.remove("");

    return solutions;
  }
}
