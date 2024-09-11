package frontend_book_market_app.polytechnic.client.profile.model;

public class ProfileModel {
    private int user_id;
    private String username;
    private String email;
    private String avatar;
    private String auth_token;
    private String reset_code;
    private int user_status;
    private String role;
    private String token;
    private String default_address;

    public ProfileModel(int user_id, String username, String email, String avatar, String auth_token, String reset_code, int user_status, String role, String token, String default_address) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.auth_token = auth_token;
        this.reset_code = reset_code;
        this.user_status = user_status;
        this.role = role;
        this.token = token;
        this.default_address = default_address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getReset_code() {
        return reset_code;
    }

    public void setReset_code(String reset_code) {
        this.reset_code = reset_code;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDefault_address() {
        return default_address;
    }

    public void setDefault_address(String default_address) {
        this.default_address = default_address;
    }
}
