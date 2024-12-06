package com.abhinav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day6 {

  public static void main(String[] args) throws IOException {
    List<List<String>> map = new ArrayList<>();
    readInput(map);

    String[][] array =
        map.stream().map(l -> l.stream().toArray(String[]::new)).toArray(String[][]::new);
    // part1Solution(array);
    part2Solution(array);
  }

  private static void readInput(List<List<String>> map) throws IOException {
    InputStream inputStream = Day2.class.getResourceAsStream("/day_6_input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      String[] split = line.split("(?!^)");
      List<String> list = List.of(split);
      map.add(new ArrayList<>(list));
    }
  }

  private static void part1Solution(String[][] map) {
    int x = 0;
    int y = 0;

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j].equals("^")) {
          x = i;
          y = j;
        }
      }
    }

    move(x, y, map);

    int sum = 0;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j].equals("X")) {
          sum++;
        }
      }
    }
    System.out.println("Part 1 - " + sum);
  }

  private static void part2Solution(String[][] map) {
    int x = 0;
    int y = 0;

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j].equals("^")) {
          x = i;
          y = j;
        }
      }
    }

    String[][] markedMap = deepCopy(map);
    move(x, y, markedMap);

    int sum = 0;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (markedMap[i][j].equals("X") && !(i==x && j==y)) {
          String[][] obstructionMap = deepCopy(map);
          obstructionMap[i][j] = "O";
          if (moveWithLoop(x, y, obstructionMap, new ArrayList<>())) {
            //print2D(obstructionMap);
            //System.out.println();
            sum++;
          }
        }
      }
    }

    System.out.println("Part 2 - " + sum);
  }

  private static void move(int x, int y, String[][] map) {

    int i = x, j = y;
    if (map[x][y].equals("^")) {
      i--;
    } else if (map[x][y].equals(">")) {
      j++;
    } else if (map[x][y].equals("v")) {
      i++;
    } else {
      j--;
    }

    String cur = map[x][y];
    map[x][y] = "X";

    if (i < 0 || j < 0 || i >= map.length || j >= map[x].length) {
      return;
    }

    if (map[i][j].equals("#")) {
      String temp = cur;
      while (map[i][j].equals("#")) {
        if (cur.equals("^")) {
          i++;
          j++;
          temp = ">";
        } else if (cur.equals(">")) {
          i++;
          j--;
          temp = "v";
        } else if (cur.equals("v")) {
          i--;
          j--;
          temp = "<";
        } else {
          i--;
          j++;
          temp = "^";
        }
      }
      map[i][j] = temp;
    } else {
      map[i][j] = cur;
    }

    move(i, j, map);
  }

  private static boolean moveWithLoop(int x, int y, String[][] map, List<String> traversedPath) {

    int i = x, j = y;

    switch (map[x][y]) {
      case "^": {
        i--;
      }
      break;
      case ">": {
        j++;
      }
      break;
      case "v": {
        i++;
      }
      break;
      default: {
        j--;
      }
    }

    String cur = map[x][y];

    String path = cur + "," + x + "," + y;

    if (traversedPath.contains(path)) {
      return true;
    } else {
      traversedPath.add(path);
    }

    map[x][y] = "X";

    if (i < 0 || j < 0 || i >= map.length || j >= map[x].length) {
      return false;
    }

    if (map[i][j].equals("#") || map[i][j].equals("O")) {
      String temp = cur;
      while (i >= 0 && j >= 0 && i < map.length && j < map[x].length && (map[i][j].equals("#") || map[i][j].equals("O"))) {
        if (temp.equals("^")) {
          i++;
          j++;
          temp = ">";
        } else if (temp.equals(">")) {
          i++;
          j--;
          temp = "v";
        } else if (temp.equals("v")) {
          i--;
          j--;
          temp = "<";
        } else {
          i--;
          j++;
          temp = "^";
        }
      }
      if (i < 0 || j < 0 || i >= map.length || j >= map[x].length) {
        return false;
      } else {
        map[i][j] = temp;
      }
    } else {
      map[i][j] = cur;
    }

    return moveWithLoop(i, j, map, traversedPath);
  }

  public static void print2D(String mat[][]) {
    // Loop through all rows
    for (int i = 0; i < mat.length; i++) {

      // Loop through all elements of current row
      for (int j = 0; j < mat[i].length; j++) System.out.print(mat[i][j] + " ");

      System.out.println();
    }
  }

  public static String[][] deepCopy(String[][] original) {
    if (original == null) {
      return null;
    }

    final String[][] result = new String[original.length][];
    for (int i = 0; i < original.length; i++) {
      result[i] = Arrays.copyOf(original[i], original[i].length);
      // For Java versions prior to Java 6 use the next:
      // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
    }
    return result;
  }
}
