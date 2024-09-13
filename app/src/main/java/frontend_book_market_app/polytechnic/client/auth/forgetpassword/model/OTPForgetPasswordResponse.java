package frontend_book_market_app.polytechnic.client.auth.forgetpassword.model;

import com.google.gson.annotations.SerializedName;

public class OTPForgetPasswordResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    // Getter và Setter cho các trường

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    // Lớp Data bên trong
    public static class Data {

        @SerializedName("id")
        private int id;

        @SerializedName("otp")
        private String otp;

        @SerializedName("created_at")
        private String created_at;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        @SerializedName("email")
        private String email;

        @SerializedName("type")
        private String type;

        // Getter và Setter cho các trường

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
