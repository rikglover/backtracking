package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PowerSet {
  private List<Character> originalList;

  private List<List<Character>> solutions = new ArrayList<>();

  public PowerSet(List<Character> originalSet) {
    this.originalList = new ArrayList<>(originalSet);
  }

  private void findSolutions(int index) {
    if(index < originalList.size()) {
      int size = solutions.size();
      Character nextCharacter = originalList.get(index);

      for (int i = 0; i < size; i++) {
        List<Character> newList = new ArrayList<>(solutions.get(i));

        newList.add(nextCharacter);
        solutions.add(newList);
      }

      findSolutions(index + 1);
    }
  }

  public List<List<Character>> findSolutions() {

    solutions.add(Collections.emptyList());

    findSolutions(0);

    return solutions;
  }
}
