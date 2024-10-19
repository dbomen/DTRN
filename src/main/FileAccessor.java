package main;

public class FileAccessor {

    private static String NOTION_API_KEY_FILE_PATH = "../../files/notionAPIkey.txt";
    private static String BLOCK_ID_FILE_PATH = "../../files/blockId.txt";

    private Writer writer;
    private Reader reader;

    public FileAccessor() {

        writer = new Writer();
        reader = new Reader();
    }
    // ==================================================
    // BLOCK IDS
    public void set_BLOCK_IDS(String BLOCK_IDS) {

        writer.write(BLOCK_ID_FILE_PATH, BLOCK_IDS);
    }

    public String[] get_BLOCK_IDS() {

        String blockIds = reader.read(BLOCK_ID_FILE_PATH);
        return blockIds.split(",");
    } 
    // ==================================================
    // NOTION API KEY
    public void set_NOTION_API_KEY(String NOTION_API) {

        writer.write(NOTION_API_KEY_FILE_PATH, NOTION_API);
    }

    public String get_NOTION_API_KEY() {

        return reader.read(NOTION_API_KEY_FILE_PATH);
    }
    // ==================================================
}