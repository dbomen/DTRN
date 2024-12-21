package main;
import java.util.List;

import main.NotionResponse.BlockListResponse.Block;

public class Test {
    
    public static void main(String[] args) {
        
        NotionAPI notionAPI = new NotionAPI();
        
        // we get the child_page blocks
        List<Block> childPageBlocks;
        try {
            childPageBlocks = notionAPI.retrieveChild_PageBlocks(1);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // we go through the child_page blocks
        for (Block childPageBlock : childPageBlocks) {

            // we get the childrenBlocks
            List<Block> childrenBlocks;
            try {
                childrenBlocks = notionAPI.retrieveChildrenBlocks(childPageBlock.getId());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            // print out the childrenBlocks
            for (Block childrenBlock : childrenBlocks) {

                System.out.print(childrenBlock.getType() + " | ");
                if (childrenBlock.getTodo() != null) System.out.print("IS CHECKED:" + childrenBlock.getTodo().isChecked() + " | ");
                System.out.println();
            }
        }

    }
}
