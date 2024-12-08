package com.abhinav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day8 {

  public static void main(String[] args) throws IOException {

    List<List<String>> input = new ArrayList<>();
    readInput(input);

    String[][] map =
        input.stream().map(l -> l.stream().toArray(String[]::new)).toArray(String[][]::new);

    part1Solution(map);
  }

  private static void readInput(List<List<String>> input) throws IOException {
    InputStream inputStream = Day2.class.getResourceAsStream("/day_8/day_8_input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      String[] split = line.split("(?!^)");
      List<String> list = List.of(split);
      input.add(new ArrayList<>(list));
    }
  }

  private static void part1Solution(String[][] map) {

    // Create map of antenna location
    Map<String, List<String>> antennas = new HashMap<>();
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        String s = map[i][j];
        if (!s.equals(".")) {
          antennas.putIfAbsent(s, new ArrayList<>());
          int finalI = i;
          int finalJ = j;
          antennas.computeIfPresent(
              s,
              (k, v) -> {
                v.add(finalI + "," + finalJ);
                return v;
              });
        }
      }
    }

    Set<String> visited = new HashSet<>();
    int sum = 0;
    for (String antenna : antennas.keySet()) {
      List<String> coordinates = antennas.get(antenna);
      for (int i = 0; i < coordinates.size(); i++) {
        for (int j = i + 1; j < coordinates.size(); j++) {
          Integer[] first = new Integer[2];
          first =
              Arrays.stream(coordinates.get(i).split(","))
                  .map(s -> Integer.parseInt(s))
                  .toArray(Integer[]::new);
          Integer[] second = new Integer[2];
          second =
              Arrays.stream(coordinates.get(j).split(","))
                  .map(s -> Integer.parseInt(s))
                  .toArray(Integer[]::new);
          int xDiff = first[0] - second[0];
          int yDiff = first[1] - second[1];

          {
            int x = first[0] + xDiff;
            int y = first[1] + yDiff;

            while (isValid(map, x, y)) {
              String antiNodeCoordinate = x + "," + y;
              if (!visited.contains(antiNodeCoordinate)) {
                sum++;
                visited.add(antiNodeCoordinate);
              }

              x += xDiff;
              y += yDiff;
            }
          }

          {
            int x = first[0] - xDiff;
            int y = first[1] - yDiff;

            while (isValid(map, x, y)) {
              String antiNodeCoordinate = x + "," + y;
              if (!visited.contains(antiNodeCoordinate)) {
                sum++;
                visited.add(antiNodeCoordinate);
              }

              x -= xDiff;
              y -= yDiff;
            }
          }

          {
            int x = second[0] - xDiff;
            int y = second[1] - yDiff;
            while (isValid(map, x, y)) {
              String antiNodeCoordinate = x + "," + y;
              if (!visited.contains(antiNodeCoordinate)) {
                sum++;
                visited.add(antiNodeCoordinate);
              }

              x -= xDiff;
              y -= yDiff;
            }
          }

          {
            int x = second[0] + xDiff;
            int y = second[1] + yDiff;
            while (isValid(map, x, y)) {
              String antiNodeCoordinate = x + "," + y;
              if (!visited.contains(antiNodeCoordinate)) {
                sum++;
                visited.add(antiNodeCoordinate);
              }

              x += xDiff;
              y += yDiff;
            }
          }
        }
      }
    }

    System.out.println("Part 1 - " + sum);
  }

  private static boolean isValid(String[][] map, int x, int y) {
    if (x < 0 || x >= map.length) {
      return false;
    }

    if (y < 0 || y >= map.length) {
      return false;
    }

    return true;
  }
}
