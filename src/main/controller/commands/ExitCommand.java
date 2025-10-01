package main.controller.commands;

import main.ANSIColors.ANSIColorStringHandler;

public class ExitCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;

    public ExitCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
    }

    @Override
    public void execute(String[] args) throws Exception {
        System.exit(0);
    }
}
