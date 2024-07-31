package frontend_book_market_app.polytechnic.client.order_user.model;

public class OrderItem {
    public int book_id;
    public String title;
    public int author_id;
    public String description;
    public int category_id;
    public int publication_year;
    public String book_avatar;
    public String created_at;
    public int views_count;
    public int purchase_count;
    public String old_price;
    public String new_price;
    public String used_books;
    public String author_name;
    public int totalPrice;
    public int quantity;
    public int shippingFee;
    private boolean isChecked;
    private boolean isReview;

    public int getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public boolean isReview() {
        return isReview;
    }

    public void setReview(boolean review) {
        isReview = review;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public String getBook_avatar() {
        return book_avatar;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getViews_count() {
        return views_count;
    }

    public int getPurchase_count() {
        return purchase_count;
    }

    public String getOld_price() {
        return old_price;
    }

    public String getNew_price() {
        return new_price;
    }

    public String getUsed_books() {
        return used_books;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getShippingFee() {
        return shippingFee;
    }


    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public void setBook_avatar(String book_avatar) {
        this.book_avatar = book_avatar;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public void setPurchase_count(int purchase_count) {
        this.purchase_count = purchase_count;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    public void setUsed_books(String used_books) {
        this.used_books = used_books;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }
}