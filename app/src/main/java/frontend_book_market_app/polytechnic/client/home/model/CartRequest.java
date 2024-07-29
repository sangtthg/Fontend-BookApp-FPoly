package frontend_book_market_app.polytechnic.client.home.model;

import java.util.List;

public class CartRequest {
    private List<CartItem> listCart;

    public CartRequest(List<CartItem> listCart) {
        this.listCart = listCart;
    }

    public List<CartItem> getListCart() {
        return listCart;
    }

    public void setListCart(List<CartItem> listCart) {
        this.listCart = listCart;
    }
}