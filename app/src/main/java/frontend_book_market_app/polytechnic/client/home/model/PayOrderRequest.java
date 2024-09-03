package frontend_book_market_app.polytechnic.client.home.model;

public class PayOrderRequest {
    private int orderId;
    private String code;
    private String token;

    public PayOrderRequest(int orderId, String discountCode, String token) {
        this.orderId = orderId;
        this.code = discountCode;
        this.token = token;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
