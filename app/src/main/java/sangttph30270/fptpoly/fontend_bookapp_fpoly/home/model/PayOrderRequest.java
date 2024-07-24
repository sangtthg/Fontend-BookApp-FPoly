package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

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