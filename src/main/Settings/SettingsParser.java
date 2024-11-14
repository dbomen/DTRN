package main.Settings;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

import main.Reader;

public class SettingsParser {

    private SettingsJson settingsJson;

    public SettingsParser() {

        Reader reader = new Reader();
        String settingsJsonBody;

        try {
            settingsJsonBody = reader.readJson("files/DTRNSettings.json");
        } catch (IOException e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new RuntimeException("READING DTRNSettings.json ERROR | ERROR MESSAGE: " + sw.toString());
        }

        Gson gson = new Gson();
        this.settingsJson = gson.fromJson(settingsJsonBody, SettingsJson.class);
    }

    public boolean runOnce() {

        return this.settingsJson.getRun_once() == 0 ? false : true;
    }

    public ArrayList<BlockId> getBlockIds() {

        return this.settingsJson.getBlockIds();
    }

    public String getNotionAPIKey() {

        return this.settingsJson.getNotionAPIKey();
    }
}