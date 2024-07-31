package frontend_book_market_app.polytechnic.client.favorite.model;

import com.google.gson.annotations.SerializedName;

public class ReviewRequest {
    @SerializedName("bookId")
    private int bookId;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("rating")
    private int rating;

    @SerializedName("comment")
    private String comment;


    public ReviewRequest(int bookId, int orderId, int rating, String comment) {
        this.bookId = bookId;
        this.orderId = orderId;
        this.rating = rating;
        this.comment = comment;
    }

}