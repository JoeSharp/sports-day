package com.ratracejoe.sportsday.command;

public interface ICommandHandler {
    void handleCommand() throws InvalidCommandException;
}
