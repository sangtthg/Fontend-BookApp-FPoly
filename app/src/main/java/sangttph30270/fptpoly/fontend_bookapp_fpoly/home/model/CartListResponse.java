package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CartListResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private CartData data;



    public String getStatus() {
        return status;
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

        @SerializedName("modify_date")
        private String modifyDate;

        @SerializedName("Book")
        private DetailBookResponse.BookData book;

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
    }

}
