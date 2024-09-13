package frontend_book_market_app.polytechnic.client.auth.forgetpassword.model;

public class VerifyOtpRequest {

    private VerifyDetails verify;
    private String password;
    private String re_password;

    public VerifyOtpRequest(String otp, String otpId, String email, String password, String re_password) {
        this.verify = new VerifyDetails(otp, otpId, email);
        this.password = password;
        this.re_password = re_password;
    }

    // Getter và Setter

    public VerifyDetails getVerify() {
        return verify;
    }

    public void setVerify(VerifyDetails verify) {
        this.verify = verify;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public static class VerifyDetails {
        private String otp;
        private String otp_id;
        private String email;

        public VerifyDetails(String otp, String otp_id, String email) {
            this.otp = otp;
            this.otp_id = otp_id;
            this.email = email;
        }

        // Getter và Setter

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
