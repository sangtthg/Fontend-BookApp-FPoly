package frontend_book_market_app.polytechnic.client.auth.forgetpassword.model;

public class CheckEmailResponse {
    private String status;
    private String message;

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEmailRegistered() {
        return "0".equals(status) && "Email đã tồn tại".equals(message);
    }
}