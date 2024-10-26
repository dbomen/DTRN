package main;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.NotionResponse.BlockListResponse.Block;

public class DTRN {

    public static void main(String[] args) {

        NotionAPI notionAPI = new NotionAPI();

        // we get the child_page blocks
        List<Block> child_pageBlocks;
        try {
            child_pageBlocks = notionAPI.retrieveChild_PageBlocks();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // we iterate the child_page blocks
        for (Block child_pageBlock : child_pageBlocks) {
            
            // we setup the control variables
            boolean underLine = false; // keeps track of if we are under the "divider" block
            int numberOfUnchecks = 0; // keeps track of how many unchecks we have, to then update the counter number

            // we get the childrenBlocks
            List<Block> childrenBlocks;
            try {
                childrenBlocks = notionAPI.retrieveChildrenBlocks(child_pageBlock.getId());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            // 1) HANDLING THE CHILDREN BLOCKS
            // we iterate the childrenBlocks
            for (Block childrenBlock : childrenBlocks) {

                // if the block is a Todo & isChecked
                if (childrenBlock.getTodo() != null && childrenBlock.getTodo().isChecked()) {

                    // we uncheck the block
                    try {
                        notionAPI.uncheckBlock(childrenBlock.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    // if we are under the line we delete the block 
                    if (underLine) {
                        try {
                            notionAPI.deleteBlock(childrenBlock.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }

                    // we update the numberOfUnchecks counter
                    numberOfUnchecks++;
                }
                // if the block is a Divider
                else if ("divider".equals(childrenBlock.getType())) {

                    underLine = true;
                }
            }

            // 2) UPDATING THE COUNTER (Valid title: "TITLE_NAME (COUNTER_NUMBER)", 
            //                                       " (COUNTER_NUMBER)" is appended if it does not exist)
            // we get the child_pageBlock title
            String currentTitle = child_pageBlock.getTitle().getTitleText();

            // we parse the currentCounter if it exists, otherwise we add the counter
            // and then we update the title
            try {
                String newTitle = getNewTitle(currentTitle, numberOfUnchecks);
                notionAPI.updateTitle(child_pageBlock.getId(), newTitle);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("[DTRN] PROBLEM WITH GETTING NEW TITLE. WILL NOT CHANGE TITLE AND MOVING ON");
            }
        }
    }

    public static String getNewTitle(String title, int numberOfUnchecks) {

        Pattern pattern = Pattern.compile(" \\((0|[1-9]\\d*)\\)"); // matches if string CONTAINS: " (NUMBER)", where NUMBER=[0, inf). Not really inf, but you get it
        Matcher matcher = pattern.matcher(title);

        // we want to get the last match in case there is a match in the TITLE_NAME
        // If you have any matches inside the TITLE_NAME and that is the last pattern match, then it will
        // update the counter and delete anything that was after.
        int startingIndex = -1;
        int lastIndex = -1;

        while (matcher.find()) {

            startingIndex = matcher.start();
            lastIndex = matcher.end();
        }
        
        // if there was no match we return the default title
        if (startingIndex == -1 || lastIndex == -1)  return title + " (" + numberOfUnchecks + ")";
        else {

            int startingIndexForLong = startingIndex + 2;
            int lastIndexForLong = lastIndex - 1;

            // else we parse the currentCounter and construct the new title
            long currentCounter = Long.parseLong(title.substring(startingIndexForLong, lastIndexForLong));
            long newCounter = currentCounter + numberOfUnchecks;
            return title.substring(0, startingIndex) + " (" + newCounter + ")";
        }
    }
}
