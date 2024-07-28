package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

public class CartUpdateRequest {
    private int cart_id;
    private int quantity;

    public CartUpdateRequest(int cart_id, int quantity) {
        this.cart_id = cart_id;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cart_id;
    }

    public void setCartId(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}