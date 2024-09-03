package frontend_book_market_app.polytechnic.client.home.network;

import frontend_book_market_app.polytechnic.client.home.model.ImageRequest;
import frontend_book_market_app.polytechnic.client.home.model.ImageResponse;
import retrofit2.Call;
import retrofit2.Callback;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
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

import static frontend_book_market_app.polytechnic.client.utils.Common.API_URL;

public class RepositoryHome {
    private final ApiServiceHome apiService;

    public RepositoryHome() {
        apiService = RetrofitManager.createService(ApiServiceHome.class, API_URL, null);
    }

    public void fetchApiHomePageBook(Callback<HomeBookResponse> callback) {
        Call<HomeBookResponse> call = apiService.getApiHomeList();
        call.enqueue(callback);
    }

    public void fetchBookDetail(int bookId, Callback<DetailBookResponse> callback) {
        Call<DetailBookResponse> call = apiService.getBookDetail(bookId);
        call.enqueue(callback);
    }

    public void addToCart(CartRequest cartRequest, Callback<Void> callback) {
        Call<Void> call = apiService.addToCart(cartRequest);
        call.enqueue(callback);
    }

    public void deleteCartItems(CartDeleteRequest cartDeleteRequest, Callback<Void> callback) {
        Call<Void> call = apiService.deleteCartItems(cartDeleteRequest);
        call.enqueue(callback);
    }

    public void fetchCartList(Callback<CartListResponse> callback) {
        Call<CartListResponse> call = apiService.fetchCartList(1, 50);
        call.enqueue(callback);
    }

    public void updateCartQuantity(int cartId, int quantity, Callback<Void> callback) {
        CartUpdateRequest request = new CartUpdateRequest(cartId, quantity);
        Call<Void> call = apiService.updateCartQuantity(request);
        call.enqueue(callback);
    }

    public void fetchBookReviews(int bookId, Callback<ReviewResponse> callback) {
        Call<ReviewResponse> call = apiService.getBookReviews(bookId);
        call.enqueue(callback);
    }

    public void fetchTotalItemInCart(Callback<CartListResponse> callback) {
        Call<CartListResponse> call = apiService.fetchTotalItemInCart();
        call.enqueue(callback);
    }

    public void createOrder(OrderRequest orderRequest, Callback<OrderResponseHome> callback) {
        Call<OrderResponseHome> call = apiService.createOrder(orderRequest);
        call.enqueue(callback);
    }

    public void payOrder(PayOrderRequest payOrderRequest, Callback<PayOrderResponse> callback) {
        Call<PayOrderResponse> call = apiService.payOrder(payOrderRequest);
        call.enqueue(callback);
    }

    public void submitReview(ReviewRequest reviewRequest, Callback<ReviewResponse> callback) {
        Call<ReviewResponse> call = apiService.submitReview(reviewRequest);
        call.enqueue(callback);
    }

    public void fetchImages(int bookId, Callback<ImageResponse> callback) {
        Call<ImageResponse> call = apiService.getImages(new ImageRequest(1, 10, new ImageRequest.Query(bookId)));
        call.enqueue(callback);
    }



}
