package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import retrofit2.Call;
import retrofit2.Callback;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.core.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.favorite.model.ReviewRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartDeleteRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.OrderRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.OrderResponseHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.PayOrderRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.PayOrderResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.ReviewResponse;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.Common.API_URL;

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

}
