package com.abhinav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

  public static void main(String[] args) throws IOException {
    List<String> input = new ArrayList<>();
    readInput(input);

    part1Solution(input);
    part2Solution(input);
  }

  private static void readInput(List<String> input) throws IOException {
    InputStream inputStream = Day3.class.getResourceAsStream("/day3Input.txt");
    InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    for (String line; (line = reader.readLine()) != null; ) {
      input.add(line);
    }
  }

  private static void part1Solution(List<String> input) {
    String regex = "mul\\([0-9]*,[0-9]*\\)";

    int sum = 0;
    for (String str : input) {
      Matcher matcher = Pattern.compile(regex).matcher(str);
      while (matcher.find()) {
        for (int j = 0; j <= matcher.groupCount(); j++) {
          String group = matcher.group(j);
          group = group.replace("mul(", "");
          group = group.replace(")", "");
          String[] strNums = group.split(",");
          sum += Integer.parseInt(strNums[0]) * Integer.parseInt(strNums[1]);
        }
      }
    }

    System.out.println("Part 1 - " +sum);
  }

  private static void part2Solution(List<String> input) {
    String mulRegex = "mul\\([0-9]*,[0-9]*\\)";
    String doRegex = "do\\(\\)";
    String dontRegex = "don't\\(\\)";

    int sum = 0;
    boolean shouldDo = true;
    for (String str : input) {
      Matcher matcher = Pattern.compile(mulRegex + "|" + doRegex + "|" + dontRegex).matcher(str);
      while (matcher.find()) {
        for (int j = 0; j <= matcher.groupCount(); j++) {
          String group = matcher.group(j);
          if (group.matches(mulRegex) && shouldDo) {
            group = group.replace("mul(", "");
            group = group.replace(")", "");
            String[] strNums = group.split(",");
            sum += Integer.parseInt(strNums[0]) * Integer.parseInt(strNums[1]);
          } else if (group.matches(doRegex)) {
            shouldDo = true;
          } else if (group.matches(dontRegex)) {
            shouldDo = false;
          }
        }
      }
    }

    System.out.println("Part 2 - " +sum);
  }
}
