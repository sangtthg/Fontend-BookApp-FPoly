package frontend_book_market_app.polytechnic.client.auth.forgetpassword.model;

public class CheckEmailRequest {
    private String email;

    public CheckEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}