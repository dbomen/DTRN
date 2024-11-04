package main.controller.commands;

import main.ANSIColors.ANSIColorStringHandler;

public interface Command {
    void execute(String[] args) throws Exception;

    // static methods that are called to display the "border"
    final int borderWidth = 100;
    final int tabLength = 4;
    static void displayBorderTop() {

        System.out.print("+");
        System.out.print("-".repeat(borderWidth - 2));
        System.out.print("+\n");
    }

    static void displayBorderBottom() { displayBorderTop(); }

    static void displayBorderWithin(String message) { displayBorderWithin(message, false); }
    static void displayBorderWithin(String message, boolean doTabsOnMessageTooLong) { displayBorderWithin(message, doTabsOnMessageTooLong, false); }
    static void displayBorderWithin(String message, boolean doTabsOnMessageTooLong, boolean messageTooLong) {

        ANSIColorStringHandler ansiColorStringHandler = new ANSIColorStringHandler();
        
        System.out.print("| ");

        // if we are printing the same message we print the starter tab
        boolean tabbed = false;
        if (messageTooLong) {
            System.out.print(" ".repeat(tabLength));
            tabbed = true;
        }
        messageTooLong = false; // we reset the messageTooLong flag

        // the problem can occur if the message is >96 (borderWidth - 4) characters,
        // because than we will go beyond the borderWidth in our scenario.
        // SOLUTION: we have to split the message into words and then print as many words as we can
        // where we should not print >(borderWidth - 4) characters in one line. In cases where
        // there are more characters than he limit we close the current line and call the 
        // displayBorderWithin() method with the substring of the message that we have not printed yet
        // =========================================================================================
        // we split by white spaces
        String[] words = message.split("\\s+");
        
        int wordsIndex = 0;
        int printedChars = 0;
        int maxPrintedChars = borderWidth - 2 - 1; // -2 for the starting "| " and -1 for the ending "|"
        if (tabbed)  maxPrintedChars -= tabLength;

        for (; wordsIndex < words.length; wordsIndex++) {

            String newWord = words[wordsIndex];
            long newWordLength = (ansiColorStringHandler.isANSIString(newWord)) 
                ? ansiColorStringHandler.getANSIStringLength(newWord)
                : newWord.length();

            if (printedChars + newWordLength + 1 > maxPrintedChars) { // +1 to count the space

                // if we are about to print more than the limit in the same line,
                // we break of the current line and continue in the next one
                messageTooLong = true;
                break;
            }
            else {

                System.out.print(newWord + " ");

                // else we print the word in the current line
                printedChars += newWordLength + 1;
            }
        }
        // =========================================================================================

        // we add the missing white spaces
        // we calculate the number of white spaces at the end
        int numberOfWhiteSpacesToPrint = borderWidth - printedChars - 2 - 1; // -2 for the starting "| " and -1 for the ending "|"
        if (tabbed)  numberOfWhiteSpacesToPrint -= tabLength;
        System.out.print(" ".repeat(numberOfWhiteSpacesToPrint));
        
        System.out.printf("|%d\n", printedChars);

        // if we are continuing in another line, because the message is too long we call
        // the same method with:
        // - substring of the not yet printed words
        // - messageTooLong flag (that we set in the for loop above)
        if (messageTooLong) {

            StringBuilder subString = new StringBuilder();
            for (; wordsIndex < words.length; wordsIndex++) {
            
                subString.append(words[wordsIndex] + " ");
                if (wordsIndex != words.length - 1)  subString.append(" ");
            }

            displayBorderWithin(subString.toString(), doTabsOnMessageTooLong, messageTooLong);
        }
    }
}