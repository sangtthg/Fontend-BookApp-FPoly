package frontend_book_market_app.polytechnic.client.home.model;

public class PayOrderRequest {
    private int orderId;

    public PayOrderRequest(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}