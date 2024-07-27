package sangttph30270.fptpoly.fontend_bookapp_fpoly.favorite.model;

import com.google.gson.annotations.SerializedName;

public class ReviewRequest {
    @SerializedName("bookId")
    private int bookId;

    @SerializedName("rating")
    private int rating;

    @SerializedName("comment")
    private String comment;

    public ReviewRequest(int bookId, int rating, String comment) {
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getBookId() {
        return bookId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}