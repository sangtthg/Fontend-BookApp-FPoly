package frontend_book_market_app.polytechnic.client.don_hang.network;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import frontend_book_market_app.polytechnic.client.don_hang.model.OrderUserResponse;
import retrofit2.http.POST;

public interface ApiServiceDonHangUser {
    @GET("orders/get-list-order?status=pending")
    Call<OrderUserResponse> getPendingOrders();

    @GET("orders/get-list-order?status=wait_for_delivery")
    Call<OrderUserResponse> getWaiForDeliverytOrders();

    @GET("orders/get-list-order?status=delivered")
    Call<OrderUserResponse> getDelivredtOrders();

    @GET("orders/get-list-order?status=cancelled")
    Call<OrderUserResponse> geCanlledOrders();

    @FormUrlEncoded
    @POST("orders/cancelled-order")
    Call<Void> cancelOrder(@Field("orderId") int orderId);
}

