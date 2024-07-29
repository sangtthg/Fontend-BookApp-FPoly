package frontend_book_market_app.polytechnic.client.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("totalItems")
    private int totalItems;

    @SerializedName("data")
    private CartData data;


    public String getStatus() {
        return status;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public CartData getData() {
        return data;
    }

    public static class CartData {
        @SerializedName("totalAll")
        private int totalAll;

        @SerializedName("data")
        private List<CartItemDetail> data;

        public int getTotalAll() {
            return totalAll;
        }

        public List<CartItemDetail> getData() {
            return data;
        }
    }

    public static class CartItemDetail {
        @SerializedName("cart_id")
        private int cartId;

        @SerializedName("user_id")
        private int userId;

        @SerializedName("book_id")
        private int bookId;

        @SerializedName("quantity")
        private int quantity;

        @SerializedName("status")
        private int status;

        @SerializedName("created_date")
        private String createdDate;

        @SerializedName("book_title_in_cart")
        private String book_title_in_cart;

        @SerializedName("modify_date")
        private String modifyDate;

        @SerializedName("book")
        private DetailBookResponse.BookData book;

        private boolean selected;

        public String getBook_title_in_cart() {
            return book_title_in_cart;
        }

        public int getCartId() {
            return cartId;
        }

        public int getUserId() {
            return userId;
        }

        public int getBookId() {
            return bookId;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getStatus() {
            return status;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public DetailBookResponse.BookData getBook() {
            return book;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {

        }

    }
}
