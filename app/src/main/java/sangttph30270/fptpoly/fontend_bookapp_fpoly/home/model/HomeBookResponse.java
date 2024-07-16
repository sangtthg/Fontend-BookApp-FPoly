package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HomeBookResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private BookData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookData getData() {
        return data;
    }

    public void setData(BookData data) {
        this.data = data;
    }

    //=====================================

    public static class BookData {
        @SerializedName("new_books")
        private List<HomeBookModel> newBooks;

        @SerializedName("best_seller_books")
        private List<HomeBookModel> bestSellerBooks;

        public List<HomeBookModel> getNewBooks() {
            return newBooks;
        }

        public void setNewBooks(List<HomeBookModel> newBooks) {
            this.newBooks = newBooks;
        }

        public List<HomeBookModel> getBestSellerBooks() {
            return bestSellerBooks;
        }

        public void setBestSellerBooks(List<HomeBookModel> bestSellerBooks) {
            this.bestSellerBooks = bestSellerBooks;
        }
    }
}
