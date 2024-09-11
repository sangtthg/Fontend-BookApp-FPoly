package frontend_book_market_app.polytechnic.client.profile.model;

public class ChangePasswordModel {

    private String password;
    private String new_password;
    private String re_password;

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    // Constructor
    public ChangePasswordModel() {
    }

    public ChangePasswordModel(String password, String new_password, String re_password) {
        this.password = password;
        this.new_password = new_password;
        this.re_password = re_password;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Method to check if new password and confirm password match
    public boolean isPasswordMatch() {
        return new_password != null && new_password.equals(re_password);
    }

    @Override
    public String toString() {
        return "ChangePasswordModel{" +
                "password='" + password + '\'' +
                ", newPassword='" + new_password + '\'' +
                ", rePassword='" + re_password + '\'' +
                '}';
    }
}
