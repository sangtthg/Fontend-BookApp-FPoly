package frontend_book_market_app.polytechnic.client.home.model;

public class CartItem {
    private int book_id;
    private int quantity;

    public CartItem(int book_id, int quantity) {
        this.book_id = book_id;
        this.quantity = quantity;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
