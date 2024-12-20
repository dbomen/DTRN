package main.controller.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;
import main.Settings.SettingsParser;

public class ACommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;
    SettingsParser settingsParser;

    public ACommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
        settingsParser = new SettingsParser();
    }

    @Override
    public void execute(String[] args) throws Exception {

        if (args.length <= 0)
            throw new RuntimeException("NO ARGUMENT PROVIDED! PLEASE PROVIDE THE ARGUMENT:<API_KEY>! CORRECT USAGE: \"a <API_KEY>\"");

        try {
            settingsParser.set_NOTION_API_KEY(args[0]);
        } catch (IOException e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new IOException("[ACommand] DID NOT ADD NOTION_API_KEY | " + sw.toString());
        }

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "ADDED NOTION_API_KEY!");
        Command.displayBorderBottom();
    }
}