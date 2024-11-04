package main.controller;

import java.util.Arrays;
import java.util.Scanner;

import main.controller.commands.Command;

public class Controller {
    
    CommandRegistry commandRegistry;

    public Controller() {

        this.commandRegistry = new CommandRegistry();
    }

    public void processInput(String input) {

        String[] parts = input.split("\\s+"); // splits by whitespace

        // we get the command name
        String commandName = parts[0];
        // we get the command args
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        // we execute the command with its arguments
        Command command;
        command = this.commandRegistry.getCommand(commandName);
        command.execute(args);
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
                controller.processInput(parts);
            }
        } catch (Exception e) {

            e.printStackTrace();
            sc.close();
            System.exit(0);
        }
    }
}
