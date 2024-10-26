package main;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NotionResponse {

    @SerializedName("object")
    private String object;

    public String getObject() {
        return this.object;
    }

    // 1) LIST - GET - SUCCESS RESPONSE
    public static class BlockListResponse extends NotionResponse {

        @SerializedName("results")
        private List<Block> blocks;

        public List<Block> getBlocks() {

            return this.blocks;
        }

        // 1.1) INNER
        public static class Block {

            private String id;
            private String type;

            @SerializedName("to_do") // for blocks that are Todos
            private Todo todo;

            @SerializedName("child_page") // for upper blocks (to get title)
            private Title title;

            public String getId() {
                return this.id;
            }

            public String getType() {
                return this.type;
            }

            public Todo getTodo() {
                return this.todo;
            }

            public Title getTitle() {
                return this.title;
            }

            // 1.2) INNER (Todo)
            public static class Todo {

                @SerializedName("checked")
                private boolean checked;

                public boolean isChecked() {
                    return this.checked;
                }
            }

            // 1.3) INNER (Title)
            public static class Title {

                @SerializedName("title")
                private String titleText;

                public String getTitleText() {
                    return this.titleText;
                }
            }
        }
    }

    // 2) ERROR RESPONSE
    public static class ErrorResponse extends NotionResponse {

        private int status;
        private String code;
        private String message;

        public int getStatus() {
            return status;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
