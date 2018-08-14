package com.rikglover.backtracking;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Combinations4 {

  private List<String> solutions = new ArrayList<>();
  private StringBuilder out = new StringBuilder();

  private final String in;

  public Combinations4(final String str) {
    in = str;
  }

  public List<String> combine() {
    combine(0);

    return solutions;
  }

  private void combine(int start) {
    for(int i = start; i < in.length(); i++) {
      out.append(in.charAt(i));
      solutions.add(out.toString());

      if(i < in.length()) {
        combine(i + 1);
        out.setLength( out.length() - 1);
      }
    }
  }
}
