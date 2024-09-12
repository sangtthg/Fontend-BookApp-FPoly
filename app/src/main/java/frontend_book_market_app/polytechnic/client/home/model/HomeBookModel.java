package frontend_book_market_app.polytechnic.client.home.model;

import com.google.gson.annotations.SerializedName;

public class HomeBookModel {
    @SerializedName("book_id")
    private int bookId;

    @SerializedName("title")
    private String title;

    @SerializedName("author_name")
    private String authorName;

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

    @SerializedName("discount_percentage")
    private int discountPercentage;

    @SerializedName("rate_book")
    private double rateBook;

    public double getRateBook() {
        return rateBook;
    }

    public void setRateBook(int rateBook) {
        this.rateBook = rateBook;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getBookAvatar() {
        return bookAvatar;
    }

    public void setBookAvatar(String bookAvatar) {
        this.bookAvatar = bookAvatar;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getUsedBooks() {
        return usedBooks;
    }

    public void setUsedBooks(String usedBooks) {
        this.usedBooks = usedBooks;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public HomeBookModel() {
    }

    public HomeBookModel(int bookId, String title, String authorName, String description, int publicationYear, String bookAvatar, String oldPrice, String newPrice, int viewsCount, int purchaseCount, String usedBooks, int discountPercentage, int rateBook) {
        this.bookId = bookId;
        this.title = title;
        this.authorName = authorName;
        this.description = description;
        this.publicationYear = publicationYear;
        this.bookAvatar = bookAvatar;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.viewsCount = viewsCount;
        this.purchaseCount = purchaseCount;
        this.usedBooks = usedBooks;
        this.discountPercentage = discountPercentage;
        this.rateBook = rateBook;
    }

    @Override
    public String toString() {
        return "HomeBookModel{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", description='" + description + '\'' +
                ", publicationYear=" + publicationYear +
                ", bookAvatar='" + bookAvatar + '\'' +
                ", oldPrice='" + oldPrice + '\'' +
                ", newPrice='" + newPrice + '\'' +
                ", viewsCount=" + viewsCount +
                ", purchaseCount=" + purchaseCount +
                ", usedBooks='" + usedBooks + '\'' +
                '}';
    }
}
