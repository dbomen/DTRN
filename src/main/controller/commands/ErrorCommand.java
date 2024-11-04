package main.controller.commands;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class ErrorCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;

    public ErrorCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
    }

    @Override
    public void execute(String[] args) {

        if (args.length <= 0)  throw new RuntimeException("[CONTROLLER] Implementation error. Invalid usage of ErrorCommand class");

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[ERROR] do \"h\" for help | ERROR STACKTRACE:", ColorCodes.RED));
        Command.displayBorderWithin(ansiColorStringHandler.getInColor(args[0], ColorCodes.YELLOW));
        Command.displayBorderBottom();
    }
     
}
