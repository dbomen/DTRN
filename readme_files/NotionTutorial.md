# NOTION TUTORIAL
This tutorial explains how to obtain block IDs, generate a Notion API key, and connect a block with a Notion API key (integration) so that the integration can make changes to the block through the Notion API.

**HEADERS:**
- [HOW TO GET BLOCK IDS FOR ANY BLOCK](#how-to-get-block-ids-for-any-block)
- [HOW TO GET A NOTION API KEY](#how-to-get-a-notion-api-key)
- [HOW TO CONNECT A BLOCK WITH AN INTEGRATION](#how-to-connect-a-block-with-an-integration)

## HOW TO GET BLOCK IDS FOR ANY BLOCK

1. Right-click or click the "..." menu and select "Copy link," as shown in the two examples below:

    ![alt text](assets/NotionTutorial-blockId-example1_1.png)
    ![alt text](assets/NotionTutorial-blockId-example1_2.png)

    ![alt text](assets/NotionTutorial-blockId-example2_1.png)
    ![alt text](assets/NotionTutorial-blockId-example2_2.png)

2. Paste the link into Notepad (or your favorite text editor). The block ID is the alphanumeric string after the block name. Refer to the example below:

    ![alt text](assets/NotionTutorial-blockId-example3_1.png)

## HOW TO GET A NOTION API KEY

1. Navigate to the Notion Developer documentation: [Create a Notion Integration](https://developers.notion.com/docs/create-a-notion-integration).

2. Click on **View my integrations** in the top-right corner:

    ![alt text](assets/NotionTutorial-apiKey-example1.png)

3. Click **New integration**:

    ![alt text](assets/NotionTutorial-apiKey-example2.png)

4. Provide a name for the integration and select an associated workspace (you likely only have one):

    ![alt text](assets/NotionTutorial-apiKey-example3.png)

5. The Integration Secret (Notion API key) will be displayed. Click **Show** to view it:

    ![alt text](assets/NotionTutorial-apiKey-example4.png)

## HOW TO CONNECT A BLOCK WITH AN INTEGRATION

1. Open the block, click the "..." menu, and select **Connections** at the bottom, as shown below:

    ![alt text](assets/NotionTutorial-connectBoth-example1.png)

2. Search for your integration and click **Connect** to link it to the block:

    ![alt text](assets/NotionTutorial-connectBoth-example2.png)
