package main.controller.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class UCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;

    public UCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
    }

    @Override
    public void execute(String[] args) throws Exception {

        try {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.indexOf("win") >= 0) {
                if (new ProcessBuilder("./helperBatFiles/updateGit.bat", "DTRN_SUPER_SECRET")
                .inheritIO()
                .start()
                .waitFor() != 0)  throw new RuntimeException("SCRIPT ERROR");
            }
            else {
                if (new ProcessBuilder("bash", "./helperBatFiles/updateGit.sh", "DTRN_SUPER_SECRET")
                .inheritIO()
                .start()
                .waitFor() != 0)  throw new RuntimeException("SCRIPT ERROR");
            }
        } catch (Exception e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new Exception("[UCommand] DID NOT UPDATE master branch | " + sw.toString());
        }

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "UPDATED and SWITCHED to master branch! For more specific use git yourself. Exiting...");
        Command.displayBorderBottom();
        System.exit(0);
    }
}
