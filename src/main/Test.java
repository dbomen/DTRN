package main;
import java.util.List;

import main.NotionResponse.BlockListResponse.Block;

public class Test {
    
    public static void main(String[] args) {
        
        NotionAPI notionAPI = new NotionAPI();

        // List<Block> blocks;
        // try {

        //     blocks = notionAPI.retrieveBlocks().get(0);
        // } catch (Exception e) {

        //     e.printStackTrace();
        //     return;
        // }

        // for (Block block : blocks) {

        //     System.out.print("| type: " + block.getType() + " | ");
        //     if (block.getTodo() != null) { // if the block is a todo

        //         System.out.print("IS BLOCK");
        //         try {
        //             notionAPI.uncheckBlock(block.getId());
        //         } catch (Exception e) {
        //             // TODO Auto-generated catch block
        //             e.printStackTrace();
        //         }
        //     }
        //     System.out.println();
        // }

        Block blocks;
        try {

            blocks = notionAPI.retrieveBlock().get(0);
        } catch (Exception e) {

            e.printStackTrace();
            return;
        }
        System.out.println(blocks.getId());
        System.out.println(blocks.getTitle().getTitleText());
    }
}
