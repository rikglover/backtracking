package com.rikglover.backtracking;

import static org.fest.assertions.Assertions.assertThat;

import com.rikglover.backtracking.MazeSolver.MazeCell;
import com.rikglover.backtracking.MazeSolver.MazeCell.MazeCellType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

public class MazeSolverTest {

  private static Character[][] maze = {
      { 'E', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W' },
      { 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'W' },
      { 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'W' },
      { 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'W', 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'W' },
      { 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'T', 'T', 'T', 'T', 'W' },
      { 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W' },
      { 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W' },
      { 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W' },
      { 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'T', 'T', 'T', 'T', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W' },
      { 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'W', 'W' },
      { 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'W', 'W', 'T', 'W', 'W' },
      { 'W', 'W', 'T', 'T', 'T', 'T', 'T', 'T', 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'W', 'W' },
      { 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'W', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'W', 'T', 'W', 'W' },
      { 'W', 'W', 'W', 'W', 'T', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'T', 'W', 'T', 'T', 'T' },
      { 'W', 'W', 'W', 'W', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'W', 'W', 'W', 'T' },
      { 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'X' }
  };

  private static int MAZE_ROWS = maze.length;
  private static int MAZE_COLUMNS = maze[0].length;

  private boolean isValidSolution(List<MazeCell> solution) {
    boolean startsWithTheEntrance = MazeCellType.ENTRANCE.equals(solution.get(0).getMazeCellType());
    boolean endsWithTheExit = MazeCellType.EXIT.equals(solution.get(solution.size() - 1).getMazeCellType());

    long moveCount = solution.stream()
        .distinct()
        .count();

    MazeCell previousMazeCell = solution.get(0);

    if(!startsWithTheEntrance || !endsWithTheExit) {
      return false;
    }

    if(moveCount != solution.size()) {
      return false;
    }

    for(int i = 1; i < solution.size(); i++) {
      MazeCell nextMazeCell = solution.get(i);

      int rowDifference = Math.abs(nextMazeCell.getRow() - previousMazeCell.getRow());
      int columnDifference = Math.abs(nextMazeCell.getColumn() - previousMazeCell.getColumn());

      boolean isRowInBounds = nextMazeCell.getRow() >= 0 && nextMazeCell.getRow() < MAZE_ROWS;
      boolean isColumnInBounds = nextMazeCell.getColumn() >= 0 && nextMazeCell.getColumn() < MAZE_COLUMNS;
      boolean isInBounds = isRowInBounds && isColumnInBounds;
      boolean isLegalMove = rowDifference == 1 && columnDifference == 0 || rowDifference == 0 && columnDifference == 1;

      if(!isInBounds || !isLegalMove) {
        return false;
      }

      previousMazeCell = nextMazeCell;
    }

    return true;
  }

  private boolean isSolutionsDistinct(List<List<MazeCell>> solutions) {
    Set<String> solutionStrings = new HashSet<>();

    for(List<MazeCell> solution : solutions) {
      String solutionString = solution.stream()
          .map(MazeCell::toString)
          .collect(Collectors.joining(", "));

      solutionStrings.add(solutionString);
    }

    return solutions.size() == solutionStrings.size();
  }

  @Test
  public void testMazeCellEquality() {
    MazeCell mazeCell1 = new MazeCell(1, 1, MazeCellType.ENTRANCE);
    MazeCell mazeCell2 = new MazeCell(1, 1, MazeCellType.ENTRANCE);
    MazeCell mazeCell3 = new MazeCell(1, 2, MazeCellType.ENTRANCE);
    MazeCell mazeCell4 = new MazeCell(1, 3, MazeCellType.ENTRANCE);
    MazeCell mazeCell5 = new MazeCell(1, 4, MazeCellType.ENTRANCE);
    MazeCell mazeCell6 = new MazeCell(1, 1, MazeCellType.EXIT);


    assertThat(mazeCell1).isEqualTo(mazeCell2);
    assertThat(mazeCell2).isNotEqualTo(mazeCell3);
    assertThat(mazeCell1).isNotEqualTo(mazeCell5);
  }

  @Test
  public void testMazeCellSetEquality() {
    MazeCell mazeCell1 = new MazeCell(1, 1, MazeCellType.ENTRANCE);
    MazeCell mazeCell2 = new MazeCell(1, 1, MazeCellType.ENTRANCE);
    MazeCell mazeCell3 = new MazeCell(1, 2, MazeCellType.ENTRANCE);
    MazeCell mazeCell4 = new MazeCell(1, 3, MazeCellType.ENTRANCE);
    MazeCell mazeCell5 = new MazeCell(1, 4, MazeCellType.ENTRANCE);
    MazeCell mazeCell6 = new MazeCell(1, 1, MazeCellType.EXIT);

    Set<MazeCell> mazeCellSet1 = new HashSet<>(Arrays.asList(mazeCell1, mazeCell3, mazeCell4));
    Set<MazeCell> mazeCellSet2 = new HashSet<>(Arrays.asList(mazeCell2, mazeCell3, mazeCell4));
    Set<MazeCell> mazeCellSet3 = new HashSet<>(Arrays.asList(mazeCell1, mazeCell3, mazeCell5));
    Set<MazeCell> mazeCellSet4 = new HashSet<>(Arrays.asList(mazeCell1, mazeCell3, mazeCell6));
    Set<MazeCell> mazeCellSet5 = new HashSet<>(Arrays.asList(mazeCell1, mazeCell3, mazeCell4, mazeCell6));

    assertThat(mazeCellSet1).isEqualTo(mazeCellSet2);
    assertThat(mazeCellSet1).isNotEqualTo(mazeCellSet3);
    assertThat(mazeCellSet1).isNotEqualTo(mazeCellSet4);
    assertThat(mazeCellSet1).isNotEqualTo(mazeCellSet5);
  }

  @Test
  public void findSolutionsTest() {
    MazeSolver mazeSolver = new MazeSolver(maze);

    List<List<MazeCell>> solutions = mazeSolver.findSolution();

    assertThat(solutions).hasSize(12);
    assertThat(isSolutionsDistinct(solutions)).isTrue();

    for(List<MazeCell> solution : solutions) {
      assertThat(isValidSolution(solution)).isTrue();
    }

    List<MazeCell> shortestPath = solutions.stream()
        .min(Comparator.comparing(Collection::size))
        .orElse(null);

    String pathString = shortestPath.stream()
        .map(MazeCell::toString)
        .collect(Collectors.joining(","));

    System.out.println("Shortest path has " + shortestPath.size() + " cells");
    System.out.println("Path is " + pathString);
  }

  @Test
  public void findFirstSolutionTest() {
    MazeSolver2 mazeSolver2 = new MazeSolver2(maze);

    List<MazeSolver2.MazeCell> solution = mazeSolver2.findFirstSolution();

    Set<MazeSolver2.MazeCell> solutionSet = new HashSet<>(solution);

    assertThat(solution).hasSize(solutionSet.size());
    assertThat(solution).isNotEmpty();
  }
}
