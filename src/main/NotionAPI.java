package main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import main.Settings.SettingsJson.BlockId;
import main.Settings.SettingsParser;

public class NotionAPI {

    private final String NOTION_API_PREFIX_BLOCKS = "https://api.notion.com/v1/blocks/";
    private final String NOTION_API_PREFIX_PAGES = "https://api.notion.com/v1/pages/";

    private final SettingsParser settingsParser;

    public NotionAPI() {

        this.settingsParser = new SettingsParser();
    }

    // ==================================================
    // GET
    /*
     * for all the BLOCKIDS - we get the individual block. We get the ones matching the refresherType
     */
    public List<NotionResponse.BlockListResponse.Block> retrieveChild_PageBlocks(int refresherType) throws Exception {

        List<NotionResponse.BlockListResponse.Block> responsesBlock = new ArrayList<>();

        // we iterate through all the BLOCK_IDS
        for (BlockId BLOCK_ID : this.settingsParser.getBlockIds()) {

            // we check for matching refresherType
            if (BLOCK_ID.getType() != refresherType)  continue;

            // sending the REQUEST and getting the RESPONSE
            HttpResponse<String> response;
            try {
                String url = this.NOTION_API_PREFIX_BLOCKS + BLOCK_ID.getId();

                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Authorization", "Bearer " + this.settingsParser.getNotionAPIKey())
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
            NotionResponse.BlockListResponse.Block responseBlock = gson.fromJson(response.body(), NotionResponse.BlockListResponse.Block.class);
            responsesBlock.add(responseBlock);
        }

        return responsesBlock;
    }
    /*
     * for all the BLOCKIDS - we get the sub-blocks
     */
    public List<NotionResponse.BlockListResponse.Block> retrieveChildrenBlocks(String childPageBlockId) throws Exception {

        // sending the REQUEST and getting the RESPONSE
        HttpResponse<String> response;
        try {
            String url = this.NOTION_API_PREFIX_BLOCKS + childPageBlockId + "/children?page_size=100";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Authorization", "Bearer " + this.settingsParser.getNotionAPIKey())
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
        return responseBlocks;
    }
    // ==================================================
    // PATCH
    public void updateTitle(String childPageBlockId, String title) throws Exception {

        // sending the REQUEST and getting the RESPONSE
        HttpResponse<String> response;
        try {
            String url = this.NOTION_API_PREFIX_PAGES + childPageBlockId;
            //System.out.println(url);

            HttpClient client = HttpClient.newHttpClient();

            // we build the jsonBody, to tell the API to change the title
            Gson gson = new Gson();
            String safeTitle = gson.toJson(title); // escaped string
            String jsonBody = "{ \"properties\": { \"Name\": {\"title\": [{ \"text\": { \"content\": " + safeTitle + " } }] } } }";
            //System.out.println(jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Authorization", "Bearer " + this.settingsParser.getNotionAPIKey())
                .header("Content-Type", "application/json")
                .header("Notion-Version", "2022-06-28")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            response = client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {

            this.doOnErrorGeneral("COULD NOT SEND THE HTTP PATCH REQUEST!");
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
    }
    public void uncheckBlock(String todoBlockId) throws Exception {

        // sending the REQUEST and getting the RESPONSE
        HttpResponse<String> response;
        try {
            String url = this.NOTION_API_PREFIX_BLOCKS + todoBlockId;

            HttpClient client = HttpClient.newHttpClient();

            // we build the jsonBody, to tell the API to uncheck the todoBlock
            String jsonBody = "{ \"to_do\": {\"checked\": false} }";

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Authorization", "Bearer " + this.settingsParser.getNotionAPIKey())
                .header("Content-Type", "application/json")
                .header("Notion-Version", "2022-06-28")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            response = client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {

            this.doOnErrorGeneral("COULD NOT SEND THE HTTP PATCH REQUEST!");
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
    }
    // ==================================================
    // DELETE
    public void deleteBlock(String todoBlockId) throws Exception {

        // sending the REQUEST and getting the RESPONSE
        HttpResponse<String> response;
        try {
            String url = this.NOTION_API_PREFIX_BLOCKS + todoBlockId;

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Authorization", "Bearer " + this.settingsParser.getNotionAPIKey())
                .header("Notion-Version", "2022-06-28")
                .DELETE()
                .build();

            response = client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {

            this.doOnErrorGeneral("COULD NOT SEND THE HTTP DELETE REQUEST!");
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
