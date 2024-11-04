package main.controller.commands;

import java.io.IOException;

import main.FileAccessor;
import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class SCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;
    FileAccessor fileAccessor;

    public SCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
        fileAccessor = new FileAccessor();
    }

    @Override
    public void execute(String[] args) throws Exception {

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("NOTION_API_KEY", ColorCodes.RED) + " " + fileAccessor.get_NOTION_API_KEY());
        
        String[] blockIds;
        try {
            blockIds = fileAccessor.get_BLOCK_IDS();
        } catch (IOException e) {

            throw new IOException("[SCommand] COULD NOT GET BLOCK_IDS | " + e.getStackTrace());
        }
        String messageInColor = (blockIds.length > 1) ? "BLOCK_IDS" : "BLOCK_ID";
        
        Command.displayBorderWithin(ansiColorStringHandler.getInColor(messageInColor, ColorCodes.RED) + " " + String.join(",", fileAccessor.get_BLOCK_IDS()));
        
        Command.displayBorderBottom();
    }
}