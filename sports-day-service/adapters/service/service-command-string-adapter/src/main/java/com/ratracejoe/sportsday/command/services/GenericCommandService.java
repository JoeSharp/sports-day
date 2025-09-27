package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.Command;
import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class GenericCommandService<T> implements ICommandHandler {
  protected final IResponseListener responseListener;
  private final Map<String, ICommandHandler> commandHandlers;

  protected GenericCommandService(IResponseListener responseListener) {
    this.responseListener = responseListener;
    this.commandHandlers = new HashMap<>();
  }

  abstract String domainToLogString(T domain);

  void logObject(T domain) {
    responseListener.handleResponse(domainToLogString(domain));
  }

  protected void registerHandler(String operand, ICommandHandler command) {
    this.commandHandlers.put(operand, command);
  }

  public void handleCommand(String commandStr) throws InvalidCommandException {
    Command command = Command.fromString(commandStr);

    if (!commandHandlers.containsKey(command.opcode())) {
      throw new InvalidCommandException();
    }

    commandHandlers.get(command.opcode()).handleCommand(command.operand());
  }

  protected ICommandHandler getAllHandler(Supplier<List<T>> getter) {
    return input -> {
      if (!input.isEmpty()) {
        throw new InvalidCommandException();
      }
      getter.get().forEach(this::logObject);
    };
  }

  protected ICommandHandler getByIdHandler(Function<UUID, T> getter) {
    return input -> {
      try {
        UUID id = UUID.fromString(input);
        T domain = getter.apply(id);
        this.logObject(domain);
      } catch (Exception e) {
        throw new InvalidCommandException();
      }
    };
  }

  protected ICommandHandler deleteHandler(Consumer<UUID> deleter) {
    return input -> {
      try {
        UUID id = UUID.fromString(input);
        deleter.accept(id);
      } catch (Exception e) {
        throw new InvalidCommandException();
      }
    };
  }
}
