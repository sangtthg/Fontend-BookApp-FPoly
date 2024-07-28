package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.favorite.model.ReviewRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartDeleteRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartUpdateRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.OrderRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.OrderResponseHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.PayOrderRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.PayOrderResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.ReviewResponse;

public interface ApiServiceHome {
    @POST("api/home/get-list-book")
    Call<HomeBookResponse> getApiHomeList();

    @FormUrlEncoded
    @POST("api/book/get-detail")
    Call<DetailBookResponse> getBookDetail(@Field("book_id") int bookId);

    @POST("api/cart/add")
    Call<Void> addToCart(@Body CartRequest cartRequest);

    @POST("api/cart/delete")
    Call<Void> deleteCartItems(@Body CartDeleteRequest cartDeleteRequest);

    @GET("api/cart/get")
    Call<CartListResponse> fetchCartList(@Query("page") int page, @Query("limit") int limit);

    @GET("api/cart/total-items")
    Call<CartListResponse> fetchTotalItemInCart();

    @POST("api/cart/update-quantity")
    Call<Void> updateCartQuantity(@Body CartUpdateRequest cartUpdateRequest);

    @POST("orders/create-order")
    Call<OrderResponseHome> createOrder(@Body OrderRequest orderRequest);

    @POST("orders/pay-order")
    Call<PayOrderResponse> payOrder(@Body PayOrderRequest payOrderRequest);

    @GET("reviews/{bookId}")
    Call<ReviewResponse> getBookReviews(@Path("bookId") int bookId);


    @POST("reviews")
    Call<ReviewResponse> submitReview(@Body ReviewRequest reviewRequest);


}