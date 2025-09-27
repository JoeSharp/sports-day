package com.ratracejoe.sportsday.command;

public interface ICommandHandler {
  void handleCommand(String commandStr) throws InvalidCommandException;
}
