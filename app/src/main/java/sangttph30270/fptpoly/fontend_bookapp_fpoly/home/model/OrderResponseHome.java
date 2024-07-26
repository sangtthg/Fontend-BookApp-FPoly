package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponseHome {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("orderId")
    private int orderId;
    @SerializedName("totalPrice")
    private double totalPrice;
    @SerializedName("totalQuantity")
    private int totalQuantity;
    @SerializedName("shippingFee")
    private double shippingFee;
    @SerializedName("payUrl")
    private String payUrl;
    @SerializedName("items")
    private List<Item> items;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public List<Item> getItems() {
        return items;
    }

    public static class Item {
        @SerializedName("book_id")
        private int bookId;
        @SerializedName("title")
        private String title;
        @SerializedName("author_id")
        private int authorId;
        @SerializedName("description")
        private String description;
        @SerializedName("category_id")
        private int categoryId;
        @SerializedName("publication_year")
        private int publicationYear;
        @SerializedName("book_avatar")
        private String bookAvatar;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("views_count")
        private int viewsCount;
        @SerializedName("purchase_count")
        private int purchaseCount;
        @SerializedName("old_price")
        private String oldPrice;
        @SerializedName("new_price")
        private String newPrice;
        @SerializedName("used_books")
        private String usedBooks;
        @SerializedName("totalPrice")
        private double itemTotalPrice;
        @SerializedName("quantity")
        private int quantity;
        @SerializedName("shippingFee")
        private double itemShippingFee;


        public int getQuantity() {
            return quantity;
        }

        public int getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public int getAuthorId() {
            return authorId;
        }

        public String getDescription() {
            return description;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public int getPublicationYear() {
            return publicationYear;
        }

        public String getBookAvatar() {
            return bookAvatar;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getViewsCount() {
            return viewsCount;
        }

        public int getPurchaseCount() {
            return purchaseCount;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public String getNewPrice() {
            return newPrice;
        }

        public String getUsedBooks() {
            return usedBooks;
        }

        public double getItemTotalPrice() {
            return itemTotalPrice;
        }


        public double getItemShippingFee() {
            return itemShippingFee;
        }
    }
}