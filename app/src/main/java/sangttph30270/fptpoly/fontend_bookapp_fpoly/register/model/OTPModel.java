package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.model;
import com.google.gson.annotations.SerializedName;

public class OTPModel {
    @SerializedName("email")
    private String email;

    public OTPModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
