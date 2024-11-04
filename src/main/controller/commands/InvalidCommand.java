package main.controller.commands;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class InvalidCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;

    public InvalidCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
    }

    @Override
    public void execute(String[] args) throws Exception {
        
        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "INVALID COMMAND!");
        Command.displayBorderBottom();
    }
    
}
