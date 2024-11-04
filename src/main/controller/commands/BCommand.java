package main.controller.commands;

import java.io.IOException;

import main.FileAccessor;
import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class BCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;
    FileAccessor fileAccessor;

    public BCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
        fileAccessor = new FileAccessor();
    }

    @Override
    public void execute(String[] args) throws Exception {

        if (args.length <= 0)
            throw new RuntimeException("NO ARGUMENT PROVIDED! PLEASE PROVIDE THE ARGUMENT:<BLOCK_IDS_SEPERATED_BY_COMMAS>! CORRECT USAGE: \"b <BLOCK_IDS_SEPERATED_BY_COMMAS>\"");
    
        try {
            fileAccessor.set_BLOCK_IDS(args[0]);
        } catch (IOException e) {

            throw new IOException("[BCommand] DID NOT ADD BLOCK_IDS | " + e.getStackTrace());
        }

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "ADDED BLOCK_IDS!");
        Command.displayBorderBottom();
    }
}