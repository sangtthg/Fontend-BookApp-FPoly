package frontend_book_market_app.polytechnic.client.home.model;

import java.util.List;

public class OrderRequest {
    private List<Integer> listCart;
    private String address;
    private String phone;

    public OrderRequest(List<Integer> listCart, String address, String phone) {
        this.listCart = listCart;
        this.address = address;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}