package frontend_book_market_app.polytechnic.client.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        @SerializedName("discount_percentage")
        private int discountPercentage;

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

        @SerializedName("quantity")
        private String quantity;

        @SerializedName("rate_book")
        private double rateBook;

        @SerializedName("avatar_reviews")
        private Object avatarReviews;



        public double getRateBook() {
            return rateBook;
        }

        public void setRateBook(double rateBook) {
            this.rateBook = rateBook;
        }

        public String getQuantity() {
            return quantity;
        }

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

        public int getDiscountPercentage() {
            return discountPercentage;
        }

        public List<String> getAvatarReviews() {
            if (avatarReviews instanceof String) {
                return Arrays.asList(((String) avatarReviews).split(","));
            } else if (avatarReviews instanceof List) {
                return (List<String>) avatarReviews;
            } else {
                return new ArrayList<>();
            }
        }

        public void setAvatarReviews(List<String> avatarReviews) {
            this.avatarReviews = avatarReviews;
        }
    }
}