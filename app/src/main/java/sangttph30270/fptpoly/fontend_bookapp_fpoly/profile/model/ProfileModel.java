package sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.model;

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

    public ProfileModel(int userId, String username, String email, String avatar, String authToken, String resetCode, int userStatus, String role, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.authToken = authToken;
        this.resetCode = resetCode;
        this.userStatus = userStatus;
        this.role = role;
        this.token = token;
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
