package main;

public class NotionAPI {

    private FileAccessor fileAccessor;
    private final String NOTION_API_KEY;
    private final String[] BLOCK_IDS;

    public NotionAPI() {

        this.fileAccessor = new FileAccessor();

        // we get the NOTION_API_KEY
        this.NOTION_API_KEY = this.fileAccessor.get_NOTION_API_KEY();
        
        // we get the BLOCK_IDS
        this.BLOCK_IDS = this.fileAccessor.get_BLOCK_IDS();
    }
    // ==================================================
    // GET
    public boolean retrieveBlock() {


    }
    // ==================================================
    // PATCH
    public boolean updateCounter() {


    }
    public boolean uncheckBlock() {


    }
    // ==================================================
    // DELETE
    public boolean deleteBlock() {


    }
    // ==================================================

    // ON ERROR METHOD
    private void doOnError(NotionResponse.ErrorResponse errorResponse) {
        
        System.out.println("Error Status: " + errorResponse.getStatus());
        System.out.println("Error Code: " + errorResponse.getCode());
        System.out.println("Error Message: " + errorResponse.getMessage());
    }
}