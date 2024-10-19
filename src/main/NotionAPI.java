package main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class NotionAPI {

    private final String NOTION_API_PREFIX = "https://api.notion.com/v1/blocks/";

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
    public List<List<NotionResponse.BlockListResponse.Block>> retrieveBlocks() throws Exception {

        List<List<NotionResponse.BlockListResponse.Block>> responsesBlocks = new ArrayList<>();

        // we iterate through all the BLOCK_IDS
        for (String BLOCK_ID : this.BLOCK_IDS) {

            // sending the REQUEST and getting the RESPONSE
            HttpResponse<String> response;
            try {
                String url = this.NOTION_API_PREFIX + BLOCK_ID + "/children?page_size=100";

                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Authorization", "Bearer " + this.NOTION_API_KEY)
                    .header("Notion-Version", "2022-06-28")
                    .GET()
                    .build();

                response = client.send(request, BodyHandlers.ofString());
            } catch (Exception e) {

                this.doOnErrorGeneral("COULD NOT SEND THE HTTP GET REQUEST!");
                throw e;
            }

            // parsing the RESPONSE
            Gson gson = new Gson();
            NotionResponse notionResponse = gson.fromJson(response.body(), NotionResponse.class);

            // we check for errors from the NOTION API
            if (response.statusCode() >= 400 || "error".equals(notionResponse.getObject())) {

                this.doOnErrorResponse(gson.fromJson(response.body(), NotionResponse.ErrorResponse.class));
                throw new RuntimeException(response.statusCode() + " CODE FROM NOTION API");
            }

            // we get the blocks from the response and add them
            List<NotionResponse.BlockListResponse.Block> responseBlocks = gson.fromJson(response.body(), NotionResponse.BlockListResponse.class).getBlocks();
            responsesBlocks.add(responseBlocks);
        }

        return responsesBlocks;
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

    // ON ERROR METHODS
    private void doOnErrorResponse(NotionResponse.ErrorResponse errorResponse) {

        System.out.println("[NOTION API] Recieved ERROR response!");
        System.out.println("[NOTION API] Error Status: " + errorResponse.getStatus());
        System.out.println("[NOTION API] Error Code: " + errorResponse.getCode());
        System.out.println("[NOTION API] Error Message: " + errorResponse.getMessage());
    }

    private void doOnErrorGeneral() { this.doOnErrorGeneral("AN ERROR OCCURRED"); }
    private void doOnErrorGeneral(String customMessage) {

        System.out.println("[NOTION API]" + customMessage);
    }
}