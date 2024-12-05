package com.abhinav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day1 {

  public static void main(String[] args) throws IOException {

    // Read Input
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    readInput(list1, list2);

    //part1Solution(list1, list2);
    part2Solution(list1, list2);
  }

  private static void readInput(List<Integer> list1, List<Integer> list2) throws IOException {
    InputStream inputStream = Day1.class.getResourceAsStream("/day1Input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      String[] splitLine = line.split("   ");
      list1.add(Integer.parseInt(splitLine[0].trim()));
      list2.add(Integer.parseInt(splitLine[1].trim()));
    }
  }

  private static void part1Solution(List<Integer> list1, List<Integer> list2) {
    // Sort Arrays
    list1 = list1.stream().sorted().collect(Collectors.toList());
    list2 = list2.stream().sorted().collect(Collectors.toList());

    // Display Sum of array
    int sum = 0;
    for (int i = 0; i < list1.size(); i++) {
      sum += Math.abs(list1.get(i) - list2.get(i));
    }

    System.out.println("Part 1 - "+sum);
  }

  public static void part2Solution(List<Integer> list1, List<Integer> list2) {
    Map<Integer, Integer> occurrencesInList2 = new HashMap<>();
    for (int num: list2) {
      occurrencesInList2.computeIfPresent(num, (key, val) -> val+1);
      occurrencesInList2.putIfAbsent(num, 1);
    }

    int sum = 0;
    for (int num: list1) {
      if (occurrencesInList2.containsKey(num)) {
        sum += num * occurrencesInList2.get(num);
      }
    }

    System.out.println("Part 2 - "+sum);
  }
}
