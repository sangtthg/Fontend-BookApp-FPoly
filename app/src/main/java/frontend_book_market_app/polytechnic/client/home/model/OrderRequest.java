package frontend_book_market_app.polytechnic.client.home.model;

import java.util.List;

public class OrderRequest {
    private List<Integer> listCart;
    private String address;

    public OrderRequest(List<Integer> listCart, String address) {
        this.listCart = listCart;
        this.address = address;
    }

    public List<Integer> getListCart() {
        return listCart;
    }

    public void setListCart(List<Integer> listCart) {
        this.listCart = listCart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}