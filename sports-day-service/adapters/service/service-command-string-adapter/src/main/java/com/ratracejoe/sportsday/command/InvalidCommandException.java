package com.ratracejoe.sportsday.command;

public class InvalidCommandException extends Exception {

  public interface ParseFn<V> {
    V parse(String input) throws Exception;
  }

  public static <V> V parse(String input, ParseFn<V> parseFn) throws InvalidCommandException {
    try {
      return parseFn.parse(input);
    } catch (Exception e) {
      throw new InvalidCommandException();
    }
  }
}
