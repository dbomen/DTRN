package main.controller.commands;

import java.io.IOException;

import main.FileAccessor;
import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class ACommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;
    FileAccessor fileAccessor;

    public ACommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
        fileAccessor = new FileAccessor();
    }

    @Override
    public void execute(String[] args) throws Exception {

        if (args.length <= 0)
            throw new RuntimeException("NO ARGUMENT PROVIDED! PLEASE PROVIDE THE ARGUMENT:<API_KEY>! CORRECT USAGE: \"a <API_KEY>\"");

        try {
            fileAccessor.set_NOTION_API_KEY(args[0]);
        } catch (IOException e) {

            throw new IOException("[ACommand] DID NOT ADD NOTION_API_KEY | " + e.getStackTrace());
        }

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "ADDED NOTION_API_KEY!");
        Command.displayBorderBottom();
    }
}