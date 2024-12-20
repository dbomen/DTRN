package main.Settings;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

import main.Reader;
import main.Writer;
import main.Settings.SettingsJson.BlockId;


public class SettingsParser {

    private static String DTRNSettings_FILE_PATH = "files/DTRNSettings.json";
    
    private Writer writer;
    private SettingsJson settingsJson;

    public SettingsParser() {

        this.writer = new Writer();

        Reader reader = new Reader();
        String settingsJsonBody;

        try {
            settingsJsonBody = reader.readJson(DTRNSettings_FILE_PATH);
        } catch (IOException e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            throw new RuntimeException("READING DTRNSettings.json ERROR | ERROR MESSAGE: " + sw.toString());
        }

        Gson gson = new Gson();
        this.settingsJson = gson.fromJson(settingsJsonBody, SettingsJson.class);
    }

    // GET

    public boolean runOnce() {

        return this.settingsJson.getRun_once() == 0 ? false : true;
    }

    public ArrayList<BlockId> getBlockIds() {

        return this.settingsJson.getBlockIds();
    }

    public String getNotionAPIKey() {

        return this.settingsJson.getNotionAPIKey();
    }

    // SET

    private void writeSettingsJson() throws IOException {

        writer.write(DTRNSettings_FILE_PATH, this.settingsJson.toString());
    }

    public void set_BLOCK_IDS(ArrayList<BlockId> BLOCK_IDS) throws IOException {

        // sets the SettingsJson
        this.settingsJson.setBlockIds(BLOCK_IDS);

        // uses writeJson
        writeSettingsJson();
    }

    public void set_NOTION_API_KEY(String NOTION_API) throws IOException {

        // sets the SettingsJson
        this.settingsJson.setNotionAPIKey(NOTION_API);

        // uses writeJson
        writeSettingsJson();
    }
}