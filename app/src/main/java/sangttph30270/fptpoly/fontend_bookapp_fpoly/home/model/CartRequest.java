package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

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