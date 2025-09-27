package com.ratracejoe.sportsday.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Command(String opcode, String operand) {
  public static Command fromString(String input) throws InvalidCommandException {
    Matcher matcher = Pattern.compile("^\\s*(\"[^\"]*\"|\\S+)\\s*(.*)$").matcher(input);
    if (!matcher.matches()) {
      throw new InvalidCommandException();
    }

    String first = matcher.group(1);
    String rest = matcher.group(2);
    return new Command(stripQuotes(first), rest);
  }

  public static String stripQuotes(String input) {
    if (input.startsWith("\"") && input.endsWith("\"")) {
      return input.substring(1, input.length() - 1);
    }
    return input;
  }

  public static List<String> splitRespectingQuotes(String input) {
    List<String> tokens = new ArrayList<>();
    Matcher matcher = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);
    while (matcher.find()) {
      if (matcher.group(1) != null) {
        tokens.add(matcher.group(1)); // quoted token
      } else {
        tokens.add(matcher.group(2)); // unquoted token
      }
    }
    return tokens;
  }
}
