package main.controller;

import java.util.HashMap;
import java.util.Map;

import main.controller.commands.ACommand;
import main.controller.commands.ActivateCommand;
import main.controller.commands.BCommand;
import main.controller.commands.Command;
import main.controller.commands.DeactivateCommand;
import main.controller.commands.HCommand;
import main.controller.commands.InvalidCommand;
import main.controller.commands.SCommand;
import main.controller.commands.UCommand;

public class CommandRegistry {
    
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandRegistry() {

        commandMap.put("h", new HCommand());
        commandMap.put("a", new ACommand());
        commandMap.put("b", new BCommand());
        commandMap.put("s", new SCommand());
        commandMap.put("u", new UCommand());
        commandMap.put("activate", new ActivateCommand());
        commandMap.put("deactivate", new DeactivateCommand());
    }

    public Command getCommand(String commandKey) {

        Command command =  this.commandMap.get(commandKey);
        if (command == null)  return new InvalidCommand();
        return command;
    }
}
