package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.network;


import retrofit2.Call;
import retrofit2.http.GET;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderUserResponse;

public interface ApiServiceOrderUser {
    @GET("orders/get-list-order?status=pending")
    Call<OrderUserResponse> getPendingOrders();

    @GET("orders/get-list-order?status=wait_for_delivery")
    Call<OrderUserResponse> getWaiForDeliverytOrders();

    @GET("orders/get-list-order?status=delivered")
    Call<OrderUserResponse> getDelivredtOrders();

    @GET("orders/get-list-order?status=cancelled")
    Call<OrderUserResponse> geCanlledOrders();
}

