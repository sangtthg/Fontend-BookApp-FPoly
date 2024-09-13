package frontend_book_market_app.polytechnic.client.search.model;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L; // Thêm serialVersionUID cho an toàn

    private int book_id;
    private String title;
    private String author_name;
    private String category_name;
    private String description;
    private int publication_year;
    private String book_avatar;
    private String old_price;
    private String new_price;
    private int views_count;
    private int purchase_count;
    private int quantity;
    private String used_books;
    private int discountPercentage;

//    private List<String> avatar_reviews; // Cập nhật kiểu dữ liệu thành List<String>

    // Getters và Setters cho từng trường
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public String getBook_avatar() {
        return book_avatar;
    }

    public void setBook_avatar(String book_avatar) {
        this.book_avatar = book_avatar;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public int getPurchase_count() {
        return purchase_count;
    }

    public void setPurchase_count(int purchase_count) {
        this.purchase_count = purchase_count;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUsed_books() {
        return used_books;
    }

    public void setUsed_books(String used_books) {
        this.used_books = used_books;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

//    public List<String> getAvatar_reviews() {
//        return avatar_reviews;
//    }
//
//    public void setAvatar_reviews(List<String> avatar_reviews) {
//        this.avatar_reviews = avatar_reviews;
//    }

    // Phương thức tính toán phần trăm giảm giá
    public void calculateDiscountPercentage() {
        try {
            double oldPrice = Double.parseDouble(old_price);
            double newPrice = Double.parseDouble(new_price);
            if (oldPrice > 0) {
                this.discountPercentage = (int) (((oldPrice - newPrice) / oldPrice) * 100);
            } else {
                this.discountPercentage = 0;
            }
        } catch (NumberFormatException e) {
            this.discountPercentage = 0; // Trả về 0 nếu không thể chuyển đổi chuỗi thành số
        }
    }
}
