package main.controller.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;
import main.Settings.SettingsJson.BlockId;
import main.Settings.SettingsParser;

public class BCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;
    SettingsParser settingsParser;

    public BCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
        settingsParser = new SettingsParser();
    }

    @Override
    public void execute(String[] args) throws Exception {

        if (args.length <= 0)
            throw new RuntimeException("NO ARGUMENT PROVIDED! PLEASE PROVIDE THE ARGUMENT:<BLOCK_IDS_SEPERATED_BY_COMMAS>! CORRECT USAGE: \"b <BLOCK_IDS_SEPERATED_BY_COMMAS>\"");
    
        if (args.length % 2 != 0)
            throw new RuntimeException("ARGUMENT NUMBER IS NOT A EVEN NUMBER, PLEASE ADD TYPES TO BLOCK_IDS. VALID INPUT: \"b <BLOCK_ID1> <TYPE1> <BLOCKID2> <TYPE2> <...>\"");

        try {

            ArrayList<BlockId> newBlockIds = new ArrayList<>();
            for (int i = 0; i < args.length; i += 2) {

                BlockId newBlockId = new BlockId();
                newBlockId.setId(args[i + 1]);
                newBlockId.setType(args[i]);

                newBlockIds.add(newBlockId);
            }
            
            settingsParser.set_BLOCK_IDS(newBlockIds);
        } catch (IOException e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new IOException("[BCommand] DID NOT ADD BLOCK_IDS | " + sw.toString());
        }

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "ADDED BLOCK_IDS!");
        Command.displayBorderBottom();
    }
}