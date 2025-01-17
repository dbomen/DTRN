package main.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Scanner;

import main.controller.commands.Command;
import main.controller.commands.ErrorCommand;

public class Controller {

    public void processInput(String input) {

        String[] parts = input.split("\\s+"); // splits by whitespace

        if (parts.length <= 0)  return;

        // we get the command name
        String commandName = parts[0];
        // we get the command args
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        // we execute the command with its arguments
        CommandRegistry commandRegistry = new CommandRegistry();
        Command command = commandRegistry.getCommand(commandName);
        
        try {
            command.execute(args);
        } catch (Exception e) {
            // if there was an error command we execute the ErrorCommand() class exectue method with the message
            // the stackTrace as String is the argument

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            new ErrorCommand().execute(new String[]{sw.toString()});
        }
    }

    public static void main(String[] args) {
        
        Controller controller = new Controller();

        Scanner sc = new Scanner(System.in);
        
        // we simulate the 'h' command for the user
        controller.processInput("h");

        // we process inputs coming from stdin
        try {
            while (true) {
                
                // we show the ">" to indicate to the user to write
                System.out.print(">");

                String parts = sc.nextLine();
                if (parts.length() > 0)
                    controller.processInput(parts);
            }
        } catch (Exception e) {

            e.printStackTrace();
            sc.close();
            System.exit(0);
        }
    }
}
