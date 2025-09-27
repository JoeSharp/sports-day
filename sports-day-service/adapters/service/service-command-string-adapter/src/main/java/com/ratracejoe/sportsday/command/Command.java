package com.ratracejoe.sportsday.command;

public record Command(String opcode, String operand) {
  public static Command fromString(String input) throws InvalidCommandException {
    String[] parts = input.split(" ", 2);

    if (parts.length != 2) {
      throw new InvalidCommandException();
    }

    return new Command(parts[0], parts[1]);
  }
}
