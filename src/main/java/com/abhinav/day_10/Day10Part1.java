package com.abhinav.day_10;

import com.abhinav.Day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.rmi.MarshalledObject;
import java.util.*;

public class Day10Part1 {

  public static void main(String[] args) throws IOException {
    List<List<String>> map = new ArrayList<>();
    readInput(map);

    Integer[][] array =
        map.stream()
            .map(l -> l.stream().map(s -> Integer.parseInt(s)).toArray(Integer[]::new))
            .toArray(Integer[][]::new);

    solve(array);
  }

  private static void readInput(List<List<String>> map) throws IOException {
    InputStream inputStream = Day2.class.getResourceAsStream("/day_10/day_10_input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      String[] split = line.split("(?!^)");
      List<String> list = List.of(split);
      map.add(new ArrayList<>(list));
    }
  }

  private static void solve(Integer[][] map) {

    List<String> trailHeads = new ArrayList<>();
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j] == 0) {
          trailHeads.add(i + "," + j);
        }
      }
    }

    int sum = 0;
    for (String trailHead : trailHeads) {
      String[] coordinate = trailHead.split(",");
      int x = Integer.parseInt(coordinate[0]);
      int y = Integer.parseInt(coordinate[1]);

      Map<String, Integer> visited = new HashMap<>();
      Set<String> vistedNines = new HashSet<>();
      sum +=
          traverse(map, x, y + 1, 0, visited, vistedNines, ">")
              + traverse(map, x, y - 1, 0, visited, vistedNines, "<")
              + traverse(map, x + 1, y, 0, visited, vistedNines, "v")
              + traverse(map, x - 1, y, 0, visited, vistedNines, "^");
    }

    System.out.println("Part 1 - " + sum);
  }

  private static int traverse(
      Integer[][] map,
      int i,
      int j,
      int prevHeight,
      Map<String, Integer> visited,
      Set<String> visitedNines,
      String direction) {
    if (i < 0 || j < 0 || i >= map.length || j >= map[i].length) {
      return 0;
    }

    String coordinate = i + "," + j;
    if (visited.containsKey(coordinate)) {
      return 0;
    }

    if (visitedNines.contains(coordinate)) {
      return 0;
    }

    if (map[i][j] - prevHeight != 1) {
      return 0;
    }
    visited.put(coordinate, 0);

    int curHeight = map[i][j];
    if (curHeight == 9) {
      visitedNines.add(coordinate);
      return 1;
    }
    int sum = 0;
    switch (direction) {
      case "^":
        {
          sum =
              traverse(map, i, j + 1, curHeight, visited, visitedNines, ">")
                  + traverse(map, i - 1, j, curHeight, visited, visitedNines, "^")
                  + traverse(map, i, j - 1, curHeight, visited, visitedNines, "<");
        }
        break;
      case ">":
        {
          sum =
              traverse(map, i, j + 1, curHeight, visited, visitedNines, ">")
                  + traverse(map, i - 1, j, curHeight, visited, visitedNines, "^")
                  + traverse(map, i + 1, j, curHeight, visited, visitedNines, "v");
        }
        break;
      case "v":
        {
          sum =
              traverse(map, i, j + 1, curHeight, visited, visitedNines, ">")
                  + traverse(map, i, j - 1, curHeight, visited, visitedNines, "<")
                  + traverse(map, i + 1, j, curHeight, visited, visitedNines, "v");
        }
        break;
      case "<":
        {
          sum =
              traverse(map, i - 1, j, curHeight, visited, visitedNines, "^")
                  + traverse(map, i, j - 1, curHeight, visited, visitedNines, "<")
                  + traverse(map, i + 1, j, curHeight, visited, visitedNines, "v");
        }
        break;
    }

    return sum;
  }
}
