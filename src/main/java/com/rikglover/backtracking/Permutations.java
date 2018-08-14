package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Permutations {

  private String inputString;

  private List<String> solutions = new ArrayList<>();

  public Permutations(String inputString) {
    this.inputString = inputString;
  }

  private Map<Character, Integer> buildCountMap(String input) {
    Map<Character, Integer> frequencyMap = new HashMap<>();

    for(int i = 0; i < input.length(); i++) {
      Character character = input.charAt(i);

      Integer count = frequencyMap.get(character);

      if(count == null) {
        frequencyMap.put(character, 1);
      } else {
        frequencyMap.put(character, count + 1);
      }
    }

    return frequencyMap;
  }

  private List<Character> getValidNextCharacters(String currentString) {
    Map<Character, Integer> countMap = buildCountMap(inputString);
    List<Character> validNextCharacters = new ArrayList<>();

    for(int i = 0; i < currentString.length(); i++) {
      Character character = currentString.charAt(i);
      Integer count = countMap.get(character);

      if(count == 1) {
        countMap.remove(character);
      } else {
        countMap.put(character, count - 1);
      }
    }

    for(HashMap.Entry<Character, Integer> entry : countMap.entrySet()) {
      Character character = entry.getKey();
      Integer count = entry.getValue();

      for(int i = 0; i < count; i++) {
        validNextCharacters.add(character);
      }
    }

    return validNextCharacters;
  }

  private void findSolutions(String currentString) {
    if(currentString.length() == inputString.length()) {
        solutions.add(currentString);
    } else {
      List<Character> validNextCharacters = getValidNextCharacters(currentString);

      for(Character nextCharacter : validNextCharacters) {
        findSolutions(currentString + nextCharacter);
      }
    }
  }

  public List<String> findSolutions() {


    findSolutions("");

    return solutions;
  }
}
