package com.abhinav.day_9;

import com.abhinav.Day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9Part1 {

  public static void main(String[] args) throws IOException {

    List<String> input = new ArrayList<>();
    readInput(input);

    String[] diskMap = input.toArray(String[]::new);
    solve(diskMap);
  }

  private static void readInput(List<String> input) throws IOException {
    InputStream inputStream = Day2.class.getResourceAsStream("/day_9/day_9_input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      String[] split = line.split("(?!^)");
      for (String s : split) {
        input.add(s);
      }
      return;
    }
  }

  private static void solve(String[] diskMap) {

    List<String> blockMap = new ArrayList<>();

    int fileId = 0;
    for (int i = 0; i < diskMap.length; i++) {
      int numOfBlocks = Integer.parseInt(diskMap[i]);
      boolean isEmpty = !(i % 2 == 0);

      for (int j = 0; j < numOfBlocks; j++) {
        if (isEmpty) {
          blockMap.add(".");
        } else {
          blockMap.add(String.valueOf(fileId));
        }
      }

      if (!isEmpty) {
        fileId++;
      }
    }

    //print(blockMap);
    compact(blockMap);
    //print(blockMap);
    System.out.println("Part 1 - " + checkSum(blockMap));
  }

  private static void compact(List<String> list) {

    for (int i = 0; i < list.size(); i++) {
      String s = list.get(i);
      if (s.equals(".")) {
        int lastIndex = list.size() - 1;
        while (list.get(lastIndex).equals(".")) {
          if (lastIndex == i) {
            return;
          }
          lastIndex--;
        }
        swap(list, i, lastIndex);
      }
    }
  }

  private static void swap(List<String> list, int i, int j) {
    String s1 = list.get(i);
    String s2 = list.get(j);
    list.remove(i);
    list.add(i, s2);
    list.remove(j);
    list.add(j, s1);
  }

  private static String checkSum(List<String> list) {
    double checksum = 0;
    for (int i = 0; i < list.size(); i++) {
      if (!list.get(i).equals(".")) {
      checksum += i * Double.parseDouble(list.get(i));
      }
    }

    return String.format("%.1f", checksum);
  }

  private static void print(List<String> list) {
    for (String s : list) {
      System.out.print(s);
    }

    System.out.println();
  }
}
