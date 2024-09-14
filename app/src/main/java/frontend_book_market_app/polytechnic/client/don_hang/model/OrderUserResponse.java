package frontend_book_market_app.polytechnic.client.don_hang.model;


import java.util.List;

public class OrderUserResponse {
    private int code;
    private String message;
    private List<Order> orders;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Order> getOrders() {
        return orders;
    }



    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

