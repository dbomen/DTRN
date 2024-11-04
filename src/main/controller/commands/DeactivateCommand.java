package main.controller.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class DeactivateCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;

    public DeactivateCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
    }

    @Override
    public void execute(String[] args) throws IOException {

        try {
            new ProcessBuilder("./helperBatFiles/deleteTask.bat", "DTRN_SUPER_SECRET").start();
        } catch (IOException e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new IOException("[Deactivate] DID NOT DE-ACTIVATE REFRESHER | " + sw.toString());
        }

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "REFRESHER DE-ACTIVATING! The script will run shortly, after that check the Windows Task Scheduler to make sure. The task with the name DTRN Refresher should not exist anymore");
        Command.displayBorderBottom();
    }
}