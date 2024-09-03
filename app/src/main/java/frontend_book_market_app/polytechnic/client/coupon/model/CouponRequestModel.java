package frontend_book_market_app.polytechnic.client.coupon.model;

import java.util.Date;

public class CouponRequestModel {
    private int id;
    private String code;
    private String description;
    private int quantity;
    private String status;
    private Date validFrom;
    private Date validTo;
    private double discountAmount;
    private boolean isExpired;
    private Date createdAt;
    private Date updatedAt;

    public CouponRequestModel() {
    }

    public CouponRequestModel(int id, String code, String description, int quantity, String status, Date validFrom, Date validTo, double discountAmount, boolean isExpired, Date createdAt, Date updatedAt) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.discountAmount = discountAmount;
        this.isExpired = isExpired;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Các phương thức getter và setter
    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }


    @Override
    public String toString() {
        return "CouponRequestModel{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", discountAmount=" + discountAmount +
                ", isExpired=" + isExpired +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
