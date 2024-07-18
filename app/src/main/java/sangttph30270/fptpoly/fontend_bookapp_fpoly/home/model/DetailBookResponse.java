package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

import com.google.gson.annotations.SerializedName;

public class DetailBookResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private BookData data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public BookData getData() {
        return data;
    }

    public static class BookData {
        @SerializedName("book_id")
        private int bookId;

        @SerializedName("title")
        private String title;

        @SerializedName("author_name")
        private String authorName;

        @SerializedName("category_name")
        private String categoryName;

        @SerializedName("description")
        private String description;

        @SerializedName("publication_year")
        private int publicationYear;

        @SerializedName("book_avatar")
        private String bookAvatar;

        @SerializedName("old_price")
        private String oldPrice;

        @SerializedName("new_price")
        private String newPrice;

        @SerializedName("views_count")
        private int viewsCount;

        @SerializedName("purchase_count")
        private int purchaseCount;

        @SerializedName("used_books")
        private String usedBooks;

        public int getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthorName() {
            return authorName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getDescription() {
            return description;
        }

        public int getPublicationYear() {
            return publicationYear;
        }

        public String getBookAvatar() {
            return bookAvatar;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public String getNewPrice() {
            return newPrice;
        }

        public int getViewsCount() {
            return viewsCount;
        }

        public int getPurchaseCount() {
            return purchaseCount;
        }

        public String getUsedBooks() {
            return usedBooks;
        }
    }
}
