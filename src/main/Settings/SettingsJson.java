package main.Settings;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SettingsJson {
    
    @SerializedName("run_once")
    private int run_once;

    @SerializedName("blockIds")
    private ArrayList<BlockId> blockIds;

    @SerializedName("notionAPIKey")
    private String notionAPIKey;

    public int getRun_once() {
        return run_once;
    }

    public ArrayList<BlockId> getBlockIds() {
        return blockIds;
    }

    public String getNotionAPIKey() {
        return notionAPIKey;
    }

    @Override
    public String toString() {
        return "SettingsJson [run_once=" + run_once + ", blockIds=" + blockIds + ", notionAPIKey=" + notionAPIKey + "]";
    }
}

class BlockId {

    @SerializedName("id")
    private String id;
    
    @SerializedName("type")
    private String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BlockId [id=" + id + ", type=" + type + "]";
    }
}