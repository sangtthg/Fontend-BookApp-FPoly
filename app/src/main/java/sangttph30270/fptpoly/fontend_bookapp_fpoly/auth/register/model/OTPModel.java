package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.register.model;

import com.google.gson.annotations.SerializedName;

public class OTPModel {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("re_password")
    private String re_password;

    public OTPModel(String email, String password, String re_password, String username, String otp_id, String otp) {
        this.email = email;
        this.password = password;
        this.re_password = re_password;
        this.username = username;
        this.otp_id = otp_id;
        this.otp = otp;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    @SerializedName("username")
    private String username;
    @SerializedName("otp_id")
    private String otp_id;
    @SerializedName("otp")
    private String otp;

    public OTPModel() {
        this.email = email;
        this.password = password;
        this.username = username;
        this.otp_id = otp_id;
        this.otp = otp;
    }

    public OTPModel(String email) {
        this.email = email;
    }

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp_id() {
        return otp_id;
    }

    public void setOtp_id(String otp_id) {
        this.otp_id = otp_id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
