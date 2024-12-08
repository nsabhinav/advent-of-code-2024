package com.abhinav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Day7 {

  public static void main(String[] args) throws IOException {
    List<String> equations = new ArrayList<>();
    readInput(equations);
    part1Solution(equations);
  }

  private static void readInput(List<String> equations) throws IOException {
    InputStream inputStream = Day2.class.getResourceAsStream("/day_7/day_7_input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      equations.add(line);
    }
  }

  private static void part1Solution(List<String> equations) {

    double sum = 0.0;

    for (String equation : equations) {
      String[] splitEquation = equation.split(":");
      double sumToGet = Double.parseDouble(splitEquation[0]);
      String[] numbers = splitEquation[1].trim().split(" ");
      if (check(numbers, 0, "+", 0, sumToGet)) {
        sum += sumToGet;
      }
    }

    System.out.printf("Part 1: %.1f", sum);
  }

  private static boolean check(
      String[] numbers, int index, String operation, double sum, double sumToGet) {
    if (sum > sumToGet) {
      return false;
    }
    if (index == numbers.length - 1) {
      sum = getSum(sum, operation, numbers[index]);
      if (sum == sumToGet) {
        return true;
      } else {
        return false;
      }
    } else {
      sum = getSum(sum, operation, numbers[index]);
      return check(numbers, index + 1, "+", sum, sumToGet)
          || check(numbers, index + 1, "*", sum, sumToGet)
          || check(numbers, index + 1, "||", sum, sumToGet);
    }
  }

  private static double getSum(double curSum, String operation, String number) {
    double sum = curSum;
    switch (operation) {
      case "*":
        {
          sum *= Double.parseDouble(number);
        }
        break;
      case "+":
        {
          sum += Double.parseDouble(number);
        }
        break;
      case "||":
        {
          String numWihoutDecimal = String.format("%.1f", sum).split("\\.")[0];
          numWihoutDecimal += number;
          sum = Double.parseDouble(numWihoutDecimal);
        }
    }
    return sum;
  }
}
