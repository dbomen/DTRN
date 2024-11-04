package main.controller.commands;

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
    public void execute(String[] args) {

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("NOTION_API_KEY", ColorCodes.RED) + " " + fileAccessor.get_NOTION_API_KEY());
        
        String[] blockIds = fileAccessor.get_BLOCK_IDS();
        String messageInColor = (blockIds.length > 1) ? "BLOCK_IDS" : "BLOCK_ID";
        Command.displayBorderWithin(ansiColorStringHandler.getInColor(messageInColor, ColorCodes.RED) + " " + String.join(",", fileAccessor.get_BLOCK_IDS()));
        
        Command.displayBorderBottom();
    }
}