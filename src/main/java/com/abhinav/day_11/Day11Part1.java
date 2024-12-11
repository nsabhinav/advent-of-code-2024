package com.abhinav.day_11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day11Part1 {

  public static void main(String[] args) throws IOException {
    List<String> input = new ArrayList<>();
    readInput(input);
    solveV3(input);
  }

  private static void readInput(List<String> input) throws IOException {
    InputStream inputStream = Day11Part1.class.getResourceAsStream("/day_11/day_11_input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      String[] split = line.split(" ");
      List<String> list = List.of(split);
      input.addAll(list);
    }
  }

  private static void solve(List<String> list) {

    LinkedList<String> stones = new LinkedList<>(list);
    Map<String, List<String>> visited = new HashMap<>();
    for (int i = 0; i < 75; i++) {
      System.out.print(i + "...");
      stones = blinkV2(stones, visited);
    }

    System.out.println("Part 1 - " + stones.size());
  }

  private static void solveV2(List<String> list) {

    LinkedList<String> stones = new LinkedList<>(list);
    Map<String, List<String>> visited = new HashMap<>();
    int sum = 0;
    for (int i = 0; i < stones.size(); i++) {
      LinkedList<String> newStones = new LinkedList<>(List.of(stones.get(i)));
      System.out.println("1");
      for (int j = 0; j < 75; j++) {
        System.out.print(j + "...");
        newStones = blinkV2(newStones, visited);
      }
      System.out.println();
      sum += newStones.size();
    }

    System.out.println("Part 1 - " + sum);
  }

  private static void solveV3(List<String> list) {

    LinkedList<String> stones = new LinkedList<>(list);
    Map<String, Double> visited = new HashMap<>();
    double sum = 0;
    for (int i = 0; i < stones.size(); i++) {
      sum += blinkV4(stones.get(i), 75, visited);
    }

    System.out.println("Part 1 - " + String.format("%.0f", sum));
  }

  private static void blink(LinkedList<String> stones, Map<String, List<String>> visited) {

    for (int i = 0; i < stones.size(); i++) {
      String stone = stones.get(i);
      if (visited.containsKey(stone)) {
        stones.remove(i);
        stones.addAll(i, visited.get(stone));
        if (visited.get(stone).size() > 1) {
          i++;
        }
      } else if (stone.equals("0")) {
        stones.remove(i);
        stones.add(i, "1");
      } else if (stone.length() % 2 == 0) {
        String[] splitStones = stone.split("");
        String first = "", second = "";
        for (int j = 0; j < splitStones.length; j++) {
          if (j < splitStones.length / 2) {
            first += splitStones[j];
          } else {
            second += splitStones[j];
          }
        }
        stones.remove(i);
        stones.add(i, String.format("%.0f", Double.parseDouble(second)));
        stones.add(i, String.format("%.0f", Double.parseDouble(first)));
        visited.put(
            stone,
            List.of(
                String.format("%.0f", Double.parseDouble(first)),
                String.format("%.0f", Double.parseDouble(second))));
        i++;
      } else {
        double dStone = Double.parseDouble(stone) * 2024;
        stones.remove(i);
        stones.add(i, String.format("%.0f", dStone));
        visited.put(stone, List.of(String.format("%.0f", dStone)));
      }
    }
  }

  private static LinkedList<String> blinkV2(
      LinkedList<String> stones, Map<String, List<String>> visited) {

    LinkedList<String> newStones = new LinkedList<>();
    for (int i = 0; i < stones.size(); i++) {
      String stone = stones.get(i);
      if (visited.containsKey(stone)) {
        newStones.addAll(visited.get(stone));
      } else if (stone.equals("0")) {
        newStones.add("1");
      } else if (stone.length() % 2 == 0) {
        String[] splitStones = stone.split("");
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        for (int j = 0; j < splitStones.length; j++) {
          if (j < splitStones.length / 2) {
            first.append(splitStones[j]);
          } else {
            second.append(splitStones[j]);
          }
        }
        String formatFirst = String.format("%.0f", Double.parseDouble(first.toString()));
        newStones.add(formatFirst);
        String formatSecond = String.format("%.0f", Double.parseDouble(second.toString()));
        newStones.add(formatSecond);
        visited.put(stone, List.of(formatFirst, formatSecond));
      } else {
        double dStone = Double.parseDouble(stone) * 2024;
        newStones.add(String.format("%.0f", dStone));
        visited.put(stone, List.of(String.format("%.0f", dStone)));
      }
    }

    return newStones;
  }

  private static List<String> blinkV3(String stone, Map<String, List<String>> visited) {
    List<String> newStones = new ArrayList<>();
    if (visited.containsKey(stone)) {
      newStones.addAll(visited.get(stone));
    } else if (stone.equals("0")) {
      newStones.add("1");
    } else if (stone.length() % 2 == 0) {
      String[] splitStones = stone.split("");
      StringBuilder first = new StringBuilder();
      StringBuilder second = new StringBuilder();
      for (int j = 0; j < splitStones.length; j++) {
        if (j < splitStones.length / 2) {
          first.append(splitStones[j]);
        } else {
          second.append(splitStones[j]);
        }
      }
      String formatFirst = String.format("%.0f", Double.parseDouble(first.toString()));
      newStones.add(formatFirst);
      String formatSecond = String.format("%.0f", Double.parseDouble(second.toString()));
      newStones.add(formatSecond);
      visited.put(stone, List.of(formatFirst, formatSecond));
    } else {
      double dStone = Double.parseDouble(stone) * 2024;
      newStones.add(String.format("%.0f", dStone));
      visited.put(stone, List.of(String.format("%.0f", dStone)));
    }
    return newStones;
  }

  private static double blinkV4(String stone, int numOfTimes, Map<String, Double> visited) {

    if (numOfTimes == 0) {
      return 1;
    }

    String key = stone + "," + numOfTimes;

    if (visited.containsKey(key)) {
      return visited.get(key);
    }

    List<String> newStones = new ArrayList<>();
    if (stone.equals("0")) {
      newStones.add("1");
    } else if (stone.length() % 2 == 0) {
      String[] splitStones = stone.split("");
      StringBuilder first = new StringBuilder();
      StringBuilder second = new StringBuilder();
      for (int j = 0; j < splitStones.length; j++) {
        if (j < splitStones.length / 2) {
          first.append(splitStones[j]);
        } else {
          second.append(splitStones[j]);
        }
      }
      String formatFirst = String.format("%.0f", Double.parseDouble(first.toString()));
      newStones.add(formatFirst);
      String formatSecond = String.format("%.0f", Double.parseDouble(second.toString()));
      newStones.add(formatSecond);
    } else {
      double dStone = Double.parseDouble(stone) * 2024;
      newStones.add(String.format("%.0f", dStone));
    }

    numOfTimes--;

    double sum = 0;

    for (String s : newStones) {
      sum += blinkV4(s, numOfTimes, visited);
    }

    visited.put(key, sum);

    return sum;
  }
}
