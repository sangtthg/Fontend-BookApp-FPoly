package frontend_book_market_app.polytechnic.client.order_user.model;


import java.util.List;

public class Order {
    public int id;
    public int userId;
    public int quantity;
    public int totalPrice;
    public String paymentType;
    public String paymentStatus;
    public String orderStatus;
    public String voucherId;
    public String discountPrice;
    public String address;
    public List<OrderItem> items;
    public String statusShip;
    public String createdAt;
    public String updatedAt;
    public boolean isReview;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean isReview() {
        return isReview;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getOrderStatus() {
        if ("pending".equals(orderStatus)) {
            return "Chờ thanh toán";
        }
        else if ("wait_for_delivery".equals(orderStatus)) {
            return "Chờ vận chuyển";
        }
        else if ("delivered".equals(orderStatus)) {
            return "Đã giao";
        }
        else if ("cancelled".equals(orderStatus)) {
            return "Đã huỷ";
        }
        return orderStatus;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatusShip() {
        if ("pending".equals(statusShip)) {
            return "Chờ thanh toán";
        }
        else if ("wait_for_delivery".equals(statusShip)) {
            return "Chờ vận chuyển";
        }
        else if ("delivered".equals(statusShip)) {
            return "Đã giao";
        }
        else if ("cancelled".equals(statusShip)) {
            return "Đã huỷ";
        }
        return statusShip;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setStatusShip(String statusShip) {
        this.statusShip = statusShip;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
