package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

import java.util.List;

public class CartDeleteRequest {
    private List<CartItemDelete> listCart;

    public CartDeleteRequest(List<CartItemDelete> listCart) {
        this.listCart = listCart;
    }

    public List<CartItemDelete> getListCart() {
        return listCart;
    }

    public void setListCart(List<CartItemDelete> listCart) {
        this.listCart = listCart;
    }

    public static class CartItemDelete {
        private int cart_id;

        public CartItemDelete(int cart_id) {
            this.cart_id = cart_id;
        }

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }
    }
}