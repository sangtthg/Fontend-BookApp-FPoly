package frontend_book_market_app.polytechnic.client.profile.model;

public class ProfileModel {
    private int userId;
    private String username;
    private String email;
    private String avatar;
    private String authToken;
    private String resetCode;
    private int userStatus;
    private String role;
    private String token;
    private String default_address;

    public ProfileModel(int userId, String username, String email, String avatar, String authToken, String resetCode, int userStatus, String role, String token, String default_address) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.authToken = authToken;
        this.resetCode = resetCode;
        this.userStatus = userStatus;
        this.role = role;
        this.token = token;
        this.default_address = default_address;
    }

    public String getDefault_address() {
        return default_address;
    }

    public void setDefault_address(String default_address) {
        this.default_address = default_address;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
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
}
