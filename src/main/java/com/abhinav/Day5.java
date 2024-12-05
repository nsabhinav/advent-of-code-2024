package com.abhinav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

  public static void main(String[] args) throws IOException {
    Map<Integer, Set<Integer>> rules = new HashMap<>();
    List<Integer> order = new ArrayList<>();
    List<List<Integer>> updates = new ArrayList<>();
    readInput(rules, updates);
    part1Solution(rules, updates);
    part2Solution(rules, updates);
  }

  private static void readInput(Map<Integer, Set<Integer>> rules, List<List<Integer>> updates)
      throws IOException {
    InputStream inputStream = Day2.class.getResourceAsStream("/day_5_input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    boolean isRules = true;
    for (String line; (line = reader.readLine()) != null; ) {
      if (line.isEmpty()) {
        isRules = false;
        continue;
      }

      if (isRules) {
        String[] split = line.split("\\|");
        int v = Integer.parseInt(split[0]);
        int k = Integer.parseInt(split[1]);
        rules.putIfAbsent(k, new HashSet<>());
        rules.computeIfPresent(
            k,
            (key, val) -> {
              val.add(v);
              return val;
            });

      } else {
        List<Integer> update = new ArrayList<>();
        String[] split = line.split(",");
        for (String str : split) {
          update.add(Integer.parseInt(str));
        }

        updates.add(update);
      }
    }
  }

  private static void part1Solution(Map<Integer, Set<Integer>> rules, List<List<Integer>> updates) {

    int sum = 0;
    for (List<Integer> update : updates) {
      boolean isSafeUpdate = true;
      for (int i = 0; i < update.size(); i++) {
        if (rules.containsKey(update.get(i))) {
          int k = update.get(i);
          Set<Integer> numsThatShouldBeBeforeK = new HashSet<>(rules.get(k));
          Set<Integer> numsThatAreActuallyAfterK =
              new HashSet<>(update.subList(i + 1, update.size()));
          if (numsThatShouldBeBeforeK.stream().anyMatch(numsThatAreActuallyAfterK::contains)) {
            isSafeUpdate = false;
            break;
          }
        }
      }

      if (isSafeUpdate) {
        if (update.size() % 2 == 0) {
          sum += update.get(update.size() / 2 - 1);
        } else {
          sum += update.get(update.size() / 2);
        }
      }
    }

    System.out.println("Part 1 - " + sum);
  }

  private static void part2Solution(Map<Integer, Set<Integer>> rules, List<List<Integer>> updates) {

    int sum = 0;
    for (List<Integer> update : updates) {
      boolean isSafeUpdate = true;
      for (int i = 0; i < update.size(); i++) {
        if (rules.containsKey(update.get(i))) {
          int k = update.get(i);
          Set<Integer> numsThatShouldBeBeforeK = new HashSet<>(rules.get(k));
          Set<Integer> numsThatAreActuallyAfterK =
              new HashSet<>(update.subList(i + 1, update.size()));
          if (numsThatShouldBeBeforeK.stream().anyMatch(numsThatAreActuallyAfterK::contains)) {
            isSafeUpdate = false;
            break;
          }
        }
      }

      if (!isSafeUpdate) {
        Integer[] reUpdate = new Integer[update.size()];
        reUpdate = update.toArray(reUpdate);
        int i = 0;
        while (i < reUpdate.length) {
          int x = reUpdate[i];
          int j = i + 1;
          boolean didBreak = false;
          while (j < reUpdate.length) {
            int y = reUpdate[j];
            if (rules.get(x).contains(y)) {
              reUpdate[j] = x;
              reUpdate[i] = y;
              i = 0;
              didBreak = true;
              break;
            } else {
              j++;
            }
          }

          if (!didBreak) {
            i++;
          }
        }

        sum += reUpdate[update.size() / 2];
      }
    }

    System.out.println("Part 2 - " + sum);
  }
}
