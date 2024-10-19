package main;
import java.util.List;

import main.NotionResponse.BlockListResponse.Block;

public class Test {
    
    public static void main(String[] args) {
        
        NotionAPI notionAPI = new NotionAPI();

        List<Block> blocks;
        try {

            blocks = notionAPI.retrieveBlocks().get(0);
        } catch (Exception e) {

            e.printStackTrace();
            return;
        }

        for (Block block : blocks) {

            System.out.println("------------------------------");
            System.out.println(block.getId());
            System.out.println(block.getType());
            
            if (block.getTodo() != null) {

                System.out.println("HAS TODO!");
                System.out.println(block.getTodo().isChecked());
            }
            System.out.println("------------------------------");
        }
    }
}
