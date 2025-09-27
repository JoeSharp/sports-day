package com.ratracejoe.sportsday.command;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommandTest {
  @ParameterizedTest
  @MethodSource("testCases")
  void simpleCommand(String commandStr, Command expected) throws InvalidCommandException {
    // When
    Command command = Command.fromString(commandStr);

    // Then
    assertThat(command).isEqualTo(expected);
  }

  static Stream<Arguments> testCases() {
    return Stream.of(
        Arguments.of("activity add", new Command("activity", "add")),
        Arguments.of("activity add swimming", new Command("activity", "add swimming")),
        Arguments.of(
            "activity add \"quarry swimming\" \"propelling oneself in water\"",
            new Command("activity", "add \"quarry swimming\" \"propelling oneself in water\"")),
        Arguments.of(
            "add \"quarry swimming\" \"propelling oneself in water\"",
            new Command("add", "\"quarry swimming\" \"propelling oneself in water\"")),
        Arguments.of(
            "\"quarry swimming\" \"propelling oneself in water\"",
            new Command("quarry swimming", "\"propelling oneself in water\"")));
  }
}
