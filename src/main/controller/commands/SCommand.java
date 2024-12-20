package main.controller.commands;

import java.util.ArrayList;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;
import main.Settings.SettingsJson.BlockId;
import main.Settings.SettingsParser;

public class SCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;
    SettingsParser settingsParser;

    public SCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
        settingsParser = new SettingsParser();
    }

    @Override
    public void execute(String[] args) throws Exception {

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("NOTION_API_KEY", ColorCodes.RED) + " " + settingsParser.getNotionAPIKey());
        
        ArrayList<BlockId> blockIds = settingsParser.getBlockIds();
        String messageInColor = (blockIds.size() > 1) ? "BLOCK_IDS" : "BLOCK_ID";
        
        Command.displayBorderWithin(ansiColorStringHandler.getInColor(messageInColor, ColorCodes.RED) + " " + blockIds);
        
        Command.displayBorderBottom();
    }
}