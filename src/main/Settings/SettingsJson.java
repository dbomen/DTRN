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

    public void setRun_once(int run_once) {
        this.run_once = run_once;
    }

    public void setBlockIds(ArrayList<BlockId> blockIds) {
        this.blockIds = blockIds;
    }

    public void setNotionAPIKey(String notionAPIKey) {
        this.notionAPIKey = notionAPIKey;
    }

    @Override
    public String toString() {
        return "{\"run_once\":" + run_once + ", \"blockIds\":" + blockIds + ", \"notionAPIKey\":" + notionAPIKey + "}";
    }

    public static class BlockId {

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
    
        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "{\"id\":" + id + ", \"type\":" + type + "}";
        }
    }
}

