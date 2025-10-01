package main.controller.commands;

import main.ANSIColors.ANSIColorStringHandler;
import main.ANSIColors.ColorCodes;

public class HCommand implements Command {

    ANSIColorStringHandler ansiColorStringHandler;

    public HCommand() {

        ansiColorStringHandler = new ANSIColorStringHandler();
    }

    @Override
    public void execute(String[] args) throws Exception {

        Command.displayBorderTop();
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("NOTE:", ColorCodes.RED) + " " + ansiColorStringHandler.getInColor("This is a Contoller program that makes using the Refresher program easier. You can do all the stuff manually yourself, but I recommend you use this program if you do not know what you are doing!", ColorCodes.YELLOW));
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("HOW TO SET UP THE REFRESHER:", ColorCodes.RED));
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("1) You need to have a Notion API_KEY and the blockId (or multiple blockIds) that you want the Refresher program to refresh. Refer to the README.md file for specifics on how to get these", ColorCodes.BLUE));
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("2) You have to set your Notion API_KEY with the \"a\" command", ColorCodes.BLUE));
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("3) You have to set your Notion blockId or blockIds with the \"b\" command", ColorCodes.BLUE));
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("4) You have to activate the Refresher program, which creates 2 tasks in the Windows task scheduler (one for daily and weekly refreshed blocks) that run the Refresher program or if the cycle is missed, it does so on PC startup. You can use the \"activate\" command", ColorCodes.BLUE));
        Command.displayBorderWithin("");
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("COMMANDS:", ColorCodes.RED));
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("h", ColorCodes.RED) + " " + "shows the message you are seeing right now with all the commands");
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("a <API_KEY>", ColorCodes.RED) + " " + "sets the Notion API_KEY that you can use");
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("b <BLOCK_ID1> <TYPE1:{1,2}> <BLOCKID2> <TYPE2:{1,2}> <...>", ColorCodes.RED) + " " + "sets the Notion blockId or blockIds. 1: refresh daily, 2: refresh weekly");
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("s", ColorCodes.RED) + " " + "shows the current Notion API_KEY and the Notion blockId or blockIds");
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("u", ColorCodes.RED) + " " + "updates and switches to master branch in this git project");
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("exit", ColorCodes.RED) + " " + "exits this controller program");
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("activate", ColorCodes.RED) + " " + "activates the Refresher program, which creates 2 tasks in the Windows task scheduler (one for daily and weekly refreshed blocks) that run the Refresher program or if the cycle is missed, it does so on PC startup");
        Command.displayBorderWithin(ansiColorStringHandler.getInColor("deactivate", ColorCodes.RED) + " " + "deactivates the Refresher program, which deletes the task from the Windows task scheduler");
        Command.displayBorderBottom();
    }
}
