package com.abhinav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {

  public static void main(String[] args) throws IOException {
    List<List<String>> input = new ArrayList<>();
    readInput(input);
    part1Solution(input);
    part2Solution(input);
  }

  private static void readInput(List<List<String>> input) throws IOException {
    InputStream inputStream = Day2.class.getResourceAsStream("/day4Input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      String[] splitLine = line.split("(?!^)");
      input.add(Arrays.asList(splitLine));
    }
  }

  private static void part1Solution(List<List<String>> input) {

    int sum = 0;

    for (int i = 0; i < input.size(); i++) {
      int rowLen = input.get(i).size();
      for (int j = 0; j < rowLen; j++) {
        if (input.get(i).get(j).equals("X")) {
          String matcher = "XMAS";
          List<String> possibleMatches =
              List.of(
                  getRight(input, i, j, 4),
                  getBottomRight(input, i, j, 4),
                  getBottom(input, i, j, 4),
                  getBottomLeft(input, i, j, 4),
                  getLeft(input, i, j, 4),
                  getTopLeft(input, i, j, 4),
                  getTop(input, i, j, 4),
                  getTopRight(input, i, j, 4));

          for (String str : possibleMatches) {
            if (str.matches(matcher)) {
              sum++;
            }
          }
        }
      }
    }

    System.out.println("Part 1 - " + sum);
  }

  private static void part2Solution(List<List<String>> input) {

    int sum = 0;

    for (int i = 0; i < input.size(); i++) {
      int rowLen = input.get(i).size();
      for (int j = 0; j < rowLen; j++) {
        if (input.get(i).get(j).equals("M")) {
          String matcher = "MAS";
          List<String> option1 =
              List.of(getBottomRight(input, i, j, 3), getBottomLeft(input, i, j + 2, 3));
          List<String> option2 =
              List.of(getBottomLeft(input, i, j, 3), getBottomRight(input, i, j - 2, 3));
          List<String> option3 =
              List.of(getTopRight(input, i, j, 3), getTopLeft(input, i, j + 2, 3));
          List<String> option4 =
              List.of(getTopLeft(input, i, j, 3), getTopRight(input, i, j - 2, 3));

          if (option1.get(0).equals(matcher)
              && (option1.get(1).equals(matcher)
                  || new StringBuilder(option1.get(1)).reverse().toString().equals(matcher))) {
            sum++;
          }

          if (option2.get(0).equals(matcher)
              && (option2.get(1).equals(matcher)
                  || new StringBuilder(option2.get(1)).reverse().toString().equals(matcher))) {
            sum++;
          }

          if (option3.get(0).equals(matcher)
              && (option3.get(1).equals(matcher)
                  || new StringBuilder(option3.get(1)).reverse().toString().equals(matcher))) {
            sum++;
          }

          if (option4.get(0).equals(matcher)
              && (option4.get(1).equals(matcher)
                  || new StringBuilder(option4.get(1)).reverse().toString().equals(matcher))) {
            sum++;
          }
        }
      }
    }

    System.out.println("Part 2 - " + sum / 2);
  }

  private static String getRight(List<List<String>> input, int i, int j, int k) {
    String str = "";
    while (i < input.size() && j < input.get(i).size() && k-- > 0) {
      str += input.get(i).get(j);
      j++;
    }
    return str;
  }

  private static String getBottomRight(List<List<String>> input, int i, int j, int k) {
    String str = "";
    while (i < input.size() && j >= 0 && j < input.get(i).size() && k-- > 0) {
      str += input.get(i).get(j);
      i++;
      j++;
    }
    return str;
  }

  private static String getBottom(List<List<String>> input, int i, int j, int k) {
    String str = "";
    while (i < input.size() && j < input.get(i).size() && k-- > 0) {
      str += input.get(i).get(j);
      i++;
    }
    return str;
  }

  private static String getBottomLeft(List<List<String>> input, int i, int j, int k) {
    String str = "";
    while (i < input.size() && j >= 0 && j < input.get(i).size() && k-- > 0) {
      str += input.get(i).get(j);
      i++;
      j--;
    }
    return str;
  }

  private static String getLeft(List<List<String>> input, int i, int j, int k) {
    String str = "";
    while (i < input.size() && j >= 0 && k-- > 0) {
      str += input.get(i).get(j);
      j--;
    }
    return str;
  }

  private static String getTopLeft(List<List<String>> input, int i, int j, int k) {
    String str = "";
    while (i >= 0 && j >= 0 && j < input.get(i).size() && k-- > 0) {
      str += input.get(i).get(j);
      i--;
      j--;
    }
    return str;
  }

  private static String getTop(List<List<String>> input, int i, int j, int k) {
    String str = "";
    while (i >= 0 && j >= 0 && k-- > 0) {
      str += input.get(i).get(j);
      i--;
    }
    return str;
  }

  private static String getTopRight(List<List<String>> input, int i, int j, int k) {
    String str = "";
    while (i >= 0 && j >= 0 && j < input.get(i).size() && k-- > 0) {
      str += input.get(i).get(j);
      i--;
      j++;
    }
    return str;
  }
}
