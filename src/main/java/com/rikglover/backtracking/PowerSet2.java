package com.rikglover.backtracking;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PowerSet2 {
  private Set<Character> originalList;

  private Set<Set<Character>> solutions = new HashSet<>();

  public PowerSet2(Set<Character> originalSet) {
    this.originalList = new HashSet<>(originalSet);
  }

  public Set<Set<Character>> findSolutions() {
    solutions.add(Collections.emptySet());

    for(Character character : originalList) {
      Set<Set<Character>> solutionsForThisCharacter = new HashSet<>();

      for(Set<Character> solution : solutions) {
        Set<Character> newSolution = new HashSet<>(solution);

        newSolution.add(character);
        solutionsForThisCharacter.add(newSolution);
      }

      solutions.addAll(solutionsForThisCharacter);
    }

    return solutions;
  }
}
