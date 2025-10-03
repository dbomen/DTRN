package main.controller.commands;

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
    public void execute(String[] args) throws Exception {

        try {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.indexOf("win") >= 0) {
                if (new ProcessBuilder("./helperBatFiles/deleteTask.bat", "DTRN_SUPER_SECRET")
                .inheritIO()
                .start()
                .waitFor() != 0)  throw new RuntimeException("SCRIPT ERROR");
            }
            else {
                if (new ProcessBuilder("sudo", "bash", "./helperBatFiles/deleteTask.sh", "DTRN_SUPER_SECRET")
                .inheritIO()
                .start()
                .waitFor() != 0)  throw new RuntimeException("SCRIPT ERROR");
            }
        } catch (Exception e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new Exception("[Deactivate] DID NOT DE-ACTIVATE REFRESHER | " + sw.toString());
        }

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("[CONTROLLER]", ColorCodes.RED) + " " + "REFRESHER DE-ACTIVATING! The script will run shortly. Check the Windows Task Scheduler or systemd timers (systemctl list-timers | grep DTRN)");
        Command.displayBorderBottom();
    }
}
