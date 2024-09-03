package frontend_book_market_app.polytechnic.client.home.network;

import frontend_book_market_app.polytechnic.client.home.model.ImageRequest;
import frontend_book_market_app.polytechnic.client.home.model.ImageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import frontend_book_market_app.polytechnic.client.favorite.model.ReviewRequest;
import frontend_book_market_app.polytechnic.client.home.model.CartDeleteRequest;
import frontend_book_market_app.polytechnic.client.home.model.CartListResponse;
import frontend_book_market_app.polytechnic.client.home.model.CartRequest;
import frontend_book_market_app.polytechnic.client.home.model.CartUpdateRequest;
import frontend_book_market_app.polytechnic.client.home.model.DetailBookResponse;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookResponse;
import frontend_book_market_app.polytechnic.client.home.model.OrderRequest;
import frontend_book_market_app.polytechnic.client.home.model.OrderResponseHome;
import frontend_book_market_app.polytechnic.client.home.model.PayOrderRequest;
import frontend_book_market_app.polytechnic.client.home.model.PayOrderResponse;
import frontend_book_market_app.polytechnic.client.home.model.ReviewResponse;

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

    @POST("api/avatar_reviews/get")
    Call<ImageResponse> getImages(@Body ImageRequest imageRequest);


}