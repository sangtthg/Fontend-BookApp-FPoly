package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.network;


import retrofit2.Call;
import retrofit2.http.GET;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderResponse2;

public interface ApiServiceOrderUser {
    @GET("orders/get-list-order?status=pending")
    Call<OrderResponse2> getPendingOrders();

    @GET("orders/get-list-order?status=wait_for_delivery")
    Call<OrderResponse2> getWaiForDeliverytOrders();

    @GET("orders/get-list-order?status=delivered")
    Call<OrderResponse2> getDelivredtOrders();

    @GET("orders/get-list-order?status=cancelled")
    Call<OrderResponse2> geCanlledOrders();
}

