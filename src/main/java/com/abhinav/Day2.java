package com.abhinav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Day2 {

  public static void main(String[] args) throws IOException {

    List<List<Integer>> input = new ArrayList<>();
    readInput(input);

    part1Solution(input);
    part2Solution(input);
  }

  private static void readInput(List<List<Integer>> input) throws IOException {
    InputStream inputStream = Day2.class.getResourceAsStream("/day2Input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      String[] splitLine = line.split(" ");
      List<Integer> inputLine = new ArrayList<>();
      for (String str : splitLine) {
        inputLine.add(Integer.parseInt(str));
      }
      input.add(inputLine);
    }
  }

  private static void part1Solution(List<List<Integer>> input) {

    int numOfSafeReports = 0;
    for (List<Integer> report : input) {
      boolean isSafeReport = isSafeReport(report, false);

      if (isSafeReport) {
        numOfSafeReports++;
      }
    }

    System.out.println("Part 1 - " + numOfSafeReports);
  }

  private static void part2Solution(List<List<Integer>> input) {

    int numOfSafeReports = 0;
    for (List<Integer> report : input) {
      boolean isSafeReport = isSafeReport(report, true);

      if (isSafeReport) {
        numOfSafeReports++;
      }
    }

    System.out.println("Part 2 - " + numOfSafeReports);
  }

  private static boolean isSafeReport(List<Integer> report, boolean callRecursively) {
    boolean isSafeReport = true;
    int prevDiff = report.get(1) - report.get(0);
    for (int j = 0; j < report.size() - 1; j++) {
      int diff = report.get(j + 1) - report.get(j);

      if (Math.abs(diff) > 3
          || Math.abs(diff) < 1
          || (prevDiff < 0 && diff > 0)
          || (prevDiff > 0 && diff < 0)) {
        if (callRecursively) {
          isSafeReport =
              isSafeReport(remove(report, j), false)
                  || isSafeReport(remove(report, j + 1), false)
                  || isSafeReport(remove(report, 0), false);
        } else {
          isSafeReport = false;
        }
        break;
      }
      prevDiff = diff;
    }
    return isSafeReport;
  }

  private static List<Integer> remove(List<Integer> report, int index) {
    List<Integer> updatedReport = new ArrayList<>();
    for (int i = 0; i < report.size(); i++) {
      if (i != index) {
        updatedReport.add(report.get(i));
      }
    }
    return updatedReport;
  }
}
