package main.ANSIColors;

public class ANSIColorPrinter {
    
    /**
     * prints message in color and resets colors 
    */
    public static void printInColor(String message, ColorCodes code) {

        startColor(code);
        System.out.print(message);
        resetColors();
    }

    private static void startColor(ColorCodes code) {

        System.out.print("\u001b[" + code.getCode() + 'm');
    }

    private static void resetColors() {

        startColor(ColorCodes.RESET);
    }
}
