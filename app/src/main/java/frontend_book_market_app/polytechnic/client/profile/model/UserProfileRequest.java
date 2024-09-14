package frontend_book_market_app.polytechnic.client.profile.model;

import com.google.gson.annotations.SerializedName;

public class UserProfileRequest {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public static class User {

        @SerializedName("user_id")
        private int userId;

        @SerializedName("username")
        private String username;

        @SerializedName("password")
        private String password;

        @SerializedName("email")
        private String email;

        @SerializedName("avatar")
        private String avatar;

        @SerializedName("user_status")
        private int userStatus;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("role")
        private String role;

        @SerializedName("sex")
        private int sex;

        @SerializedName("default_address")
        private int defaultAddress;

        @SerializedName("is_block")
        private boolean isBlock;

        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public String getAvatar() {
            return avatar;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getRole() {
            return role;
        }

        public int getSex() {
            return sex;
        }

        public int getDefaultAddress() {
            return defaultAddress;
        }

        public boolean isBlock() {
            return isBlock;
        }
    }
}
