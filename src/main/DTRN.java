package main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.NotionResponse.BlockListResponse.Block;
import main.Settings.SettingsParser;

public class DTRN {

    public static class PageBlock_ChildrenBlockHandler {

        private NotionAPI notionAPI;
        
        private Block pageBlock;
        private List<Block> pageBlock_childrenBlocks;
        private boolean aboveLine;
        private int numberOfUnchecks;

        public PageBlock_ChildrenBlockHandler(Block pageBlock, List<Block> pageBlock_childrenBlocks, NotionAPI notionAPI) {

            this.notionAPI = notionAPI;

            this.pageBlock = pageBlock;
            this.pageBlock_childrenBlocks = pageBlock_childrenBlocks;

            // control variables
            this.aboveLine = true; // keeps track of if we are above the "divider" block
            this.numberOfUnchecks = 0; // keeps track of how many unchecks we have, to then update the counter number
        }

        /*
         * main handler
         */
        public void handlePageBlock() {

            handleChildrenBlocks(this.pageBlock_childrenBlocks);
            
            // 2) UPDATING THE COUNTER (Valid title: "TITLE_NAME (COUNTER_NUMBER)", 
            //                                       " (COUNTER_NUMBER)" is appended if it does not exist)
            // we get the child_pageBlock title
            String currentTitle = this.pageBlock.getTitle().getTitleText();

            // we parse the currentCounter if it exists, otherwise we add the counter
            // and then we update the title
            try {
                String newTitle = getNewTitle(currentTitle, numberOfUnchecks);
                notionAPI.updateTitle(this.pageBlock.getId(), newTitle);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("[DTRN] PROBLEM WITH GETTING NEW TITLE. WILL NOT CHANGE TITLE AND MOVING ON");
            }
        }

        /*
         * iterates the childrenBlocks and correctly handles them with the NotionAPI calls
         */
        public void handleChildrenBlocks(List<Block> childrenBlocks) {

            // 1) HANDLING THE CHILDREN BLOCKS
            // we iterate the childrenBlocks
            for (Block childrenBlock : childrenBlocks) {

                // Ce ima childBlock "children" potem gremo rekurzivno handlat se njih
                // ko handlamo njih se handlamo "parent" block do konca
                // ======================
                if (childrenBlock.isHasChildren()) {

                    List<Block> test;
                    try {
                        test = notionAPI.retrieveChildrenBlocks(childrenBlock.getId());
                    } catch (Exception e) {

                        e.printStackTrace();
                        return;
                    }

                    this.handleChildrenBlocks(test);
                }
                // ======================

                // if the block is a Todo & isChecked
                if (childrenBlock.getTodo() != null && childrenBlock.getTodo().isChecked()) {

                    // we uncheck the block
                    try {
                        notionAPI.uncheckBlock(childrenBlock.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    // if we are above the line we delete the block 
                    if (aboveLine) {
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

                    aboveLine = false;
                }
            }
        }
    }


    public static void main(String[] args) {

        NotionAPI notionAPI = new NotionAPI();
        boolean run_once = new SettingsParser().runOnce();

        int refresherType;
        try {

            refresherType = Integer.parseInt(args[0]); // 1 - daily, 2 - weekly
        } catch (Exception e) {

            throw new RuntimeException("[DTRN] NO REFRESHER TYPE GIVEN!" + e);
        }

        // we get the child_page blocks
        List<Block> child_pageBlocks;
        try {
            child_pageBlocks = notionAPI.retrieveChild_PageBlocks(refresherType);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // we iterate the child_page blocks
        blocks: for (Block child_pageBlock : child_pageBlocks) {
            
            // if we have run_once enabled => we check if this block was already refreshed
            if (run_once) {

                String title = child_pageBlock.getTitle().getTitleText();

                int lastUpdatedDayOfMonth = title.charAt(title.length() - 1) - 'A'; // the # of the day is stored in printable-ascii
                int todayDayOfMonth = LocalDate.now().getDayOfMonth();

                // if they are the same days it has already been refreshed
                // NOTE: This should work for both the daily and weekly refreshers.
                // Edge case: the PC has not been opened in like 1 month, the days could be the same
                // but im fine with that, it will refresh the next day / week
                if (lastUpdatedDayOfMonth == todayDayOfMonth)  continue blocks;

                // edge case: lets say we run the weekly refresher on Monday on PC1,
                // we dont open PC2 on Monday. Then on Tuesday we open PC2, it runs the missed
                // weekly refresher that should have been run on Monday, it will check if
                // Monday == Tuesday => its not so it will refresh.
                // We have 2x refreshing of the weekly refresher, but we only want 1x.
                // This should not be a problem with daily refreshers
                // ---
                // FIX: If the refresherType==2, we check if the weekly refresher already refreshed
                // the child_page block this week
                if (refresherType == 2) {

                    DayOfWeek todayDayOfWeek = LocalDate.now().getDayOfWeek();
                    Month todayMonth = LocalDate.now().getMonth();
    
                    // we check if we already refreshed this week
                    for (int sameWeekDay = DayOfWeek.MONDAY.getValue(); sameWeekDay < todayDayOfWeek.getValue(); sameWeekDay++) {
    
                        // we get the day of month for this day
                        int sameWeekDay_DayOfMonth = todayDayOfMonth - (todayDayOfWeek.getValue() - sameWeekDay);

                        // in case that day was in the previous month
                        if (sameWeekDay_DayOfMonth < 1) {

                            // we have to check which day that is in the previous month
                            Month previousMonth = todayMonth.minus(1);

                            sameWeekDay_DayOfMonth = previousMonth.length(LocalDate.now().isLeapYear()) + sameWeekDay_DayOfMonth;
                        }

                        // we check if we last updated on that day of month, if yes we skip the child_page
                        if (lastUpdatedDayOfMonth == sameWeekDay_DayOfMonth)  continue blocks;
                    }
                }
            }
            
            // we get the childrenBlocks
            List<Block> childrenBlocks;
            try {
                childrenBlocks = notionAPI.retrieveChildrenBlocks(child_pageBlock.getId());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            PageBlock_ChildrenBlockHandler pageBlock_ChildrenBlockHandler = new PageBlock_ChildrenBlockHandler(
                child_pageBlock, childrenBlocks, notionAPI);
            pageBlock_ChildrenBlockHandler.handlePageBlock();
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
        if (startingIndex == -1 || lastIndex == -1)  return title + " (" + numberOfUnchecks + ")-" + (char)('A' + LocalDate.now().getDayOfMonth());
        else {

            int startingIndexForLong = startingIndex + 2;
            int lastIndexForLong = lastIndex - 1;

            // else we parse the currentCounter and construct the new title
            long currentCounter = Long.parseLong(title.substring(startingIndexForLong, lastIndexForLong));
            long newCounter = currentCounter + numberOfUnchecks;
            return title.substring(0, startingIndex) + " (" + newCounter + ")-" + (char)('A' + LocalDate.now().getDayOfMonth());
        }
    }
}
