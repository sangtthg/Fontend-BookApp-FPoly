package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model;


import java.util.List;

public class OrderResponse2 {
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

