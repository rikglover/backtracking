package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Permutations2 {

  private String inputString;
  private Map<Character, Long> countMap;

  private List<String> solutions = new ArrayList<>();

  public Permutations2(String inputString) {
    this.inputString = inputString;
  }

  private void findSolutions(String currentString, String alphabet) {
    if(currentString.length() == inputString.length()) {
      solutions.add(currentString);
    } else {
      for(int i = 0; i < alphabet.length(); i++) {
        Character nextCharacter = alphabet.charAt(i);

        findSolutions(currentString + nextCharacter, alphabet.substring(0, i) + alphabet.substring(i + 1, alphabet.length()));
      }
    }
  }

  public List<String> findSolutions() {

    findSolutions("", inputString);

    return solutions;
  }
}
