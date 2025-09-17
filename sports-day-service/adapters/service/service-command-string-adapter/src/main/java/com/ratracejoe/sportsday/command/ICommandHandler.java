package com.ratracejoe.sportsday.command;

public interface ICommandHandler {
  void handleCommand(String command) throws InvalidCommandException;
}
