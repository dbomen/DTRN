package main.ANSIColors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ANSIColorStringHandler {

    /**
     * returns if string is ANSIString
     */
    public boolean isANSIString(String string) {

        return string.contains("\u001b[");
    }

    /**
     * returns the real length of an ANSIString
     */
    public int getANSIStringLength(String string) {

        if (!isANSIString(string))  
            throw new RuntimeException("[ANSIColorString] Tried to call ANSIStringLength on a non-ANSIString!");

        Pattern pattern = Pattern.compile("\\u001b\\[\\d{1,2}m"); // mathces for \u001b[(whatever digit from 00 - 99)m
        Matcher matcher = pattern.matcher(string);

        // we search for the pattern to create the substring and get its lenght()
        int substringStartIndex = -1;
        int substringEndIndex = -1;
        for (int i = 0; matcher.find(); i++) {

            if      (i == 0)  substringStartIndex = matcher.end();
            else if (i == 1)  substringEndIndex = matcher.start();
            else
                throw new RuntimeException("[ANSIColorString] Incorrect ANSIString");
        }

        // if there was a regex problem we throw an error
        if (substringStartIndex == -1 || substringEndIndex == -1)  throw new RuntimeException("[ANSIColorString] Regex error");

        // we return the real string length
        return string.substring(substringStartIndex, substringEndIndex).length();
    }
    
    /**
     * prints message in color and resets colors 
     */
    public String getInColor(String message, ColorCodes code) {
        
        // we want to split the message to get individual words and color them individually
        // to make our lifes easier in the Command.displayBorderWithin() method

        String[] words = message.split("\\s+");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {

            stringBuilder.append(startColor(code));
            stringBuilder.append(words[i]);
            stringBuilder.append(resetColors());
            
            if (i != words.length - 1) stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private String startColor(ColorCodes code) {

        return "\u001b[" + code.getCode() + 'm';
    }

    private String resetColors() {

        return startColor(ColorCodes.RESET);
    }
}
