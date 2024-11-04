package main.controller.commands;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class InvalidCommand implements Command {
    @Override
    public void execute(String[] args) {
        
        ANSIColorStringHandler ansiColorStringHandler = new ANSIColorStringHandler();

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "INVALID COMMAND!");
        Command.displayBorderBottom();
    }
    
}
