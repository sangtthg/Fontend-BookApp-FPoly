package frontend_book_market_app.polytechnic.client.auth.forgetpassword.model;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("otp_id")
    private int otp_id;

    @SerializedName("otp")
    private String otp;

    @SerializedName("password")
    private String password;

    @SerializedName("re_password")
    private String rePassword;

    // Constructor

    public ChangePasswordRequest(int otp_id, String otp, String password, String rePassword) {
        this.otp_id = otp_id;
        this.otp = otp;
        this.password = password;
        this.rePassword = rePassword;
    }

    public int getOtp_id() {
        return otp_id;
    }

    public void setOtp_id(int otp_id) {
        this.otp_id = otp_id;
    }


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}