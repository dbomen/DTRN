package main.controller.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class ActivateCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;

    public ActivateCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
    }

    @Override
    public void execute(String[] args) throws IOException {

        try {
            new ProcessBuilder("./helperBatFiles/createTask.bat", "DTRN_SUPER_SECRET").start();
        } catch (IOException e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new IOException("[ActivateCommand] DID NOT ACTIVATE REFRESHER | " + sw.toString());
        }

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "REFRESHER ACTIVATING! The script will run shortly, after that check the Windows Task Scheduler to make sure. The name of the tasks are DTRN Refresher_DAILY and DTRN Refresher_WEEKLY");
        Command.displayBorderBottom();
    }
}