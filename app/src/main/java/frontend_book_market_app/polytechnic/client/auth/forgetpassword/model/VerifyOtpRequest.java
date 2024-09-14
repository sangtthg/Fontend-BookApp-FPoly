package frontend_book_market_app.polytechnic.client.auth.forgetpassword.model;

public class VerifyOtpRequest {
    private String email;
    private String otp;
    private String otp_id;

    public VerifyOtpRequest(String email, String otp, String otp_id) {
        this.email = email;
        this.otp = otp;
        this.otp_id = otp_id;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp_id() {
        return otp_id;
    }

    public void setOtp_id(String otp_id) {
        this.otp_id = otp_id;
    }
    @Override
    public String toString() {
        return "VerifyOtpRequest{" +
                "email='" + email + '\'' +
                ", otpId='" + otp_id + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
