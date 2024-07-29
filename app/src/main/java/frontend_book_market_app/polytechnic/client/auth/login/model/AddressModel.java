package frontend_book_market_app.polytechnic.client.auth.login.model;

public class AddressModel {
    private int addressId;
    private int userId;
    private String name;
    private String phone;
    private String email; // Thêm trường email

    public AddressModel(int addressId, int userId, String name, String phone, String email, String address, String addressType, int status, String createdAt, String updatedAt, boolean isDefault) {
        this.addressId = addressId;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.addressType = addressType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDefault = isDefault;
    }
    @Override
    public String toString() {
        return "AddressModel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", addressType='" + addressType + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String address;
    private String addressType;
    private int status;
    private String createdAt;
    private String updatedAt;
    private boolean isDefault;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
// Constructor, getters, and setters

    public AddressModel(int addressId, int userId, String name, String phone, String address, String addressType, int status, String createdAt, String updatedAt, boolean isDefault) {
        this.addressId = addressId;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.addressType = addressType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDefault = isDefault;
    }

}
