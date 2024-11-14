package main.Settings;

public class Test {
    
    public static void main(String[] args) {
        
        SettingsParser settingsParser = new SettingsParser();

        System.out.println(settingsParser.runOnce());
        System.out.println(settingsParser.getNotionAPIKey());
        System.out.println(settingsParser.getBlockIds());
    }
}
