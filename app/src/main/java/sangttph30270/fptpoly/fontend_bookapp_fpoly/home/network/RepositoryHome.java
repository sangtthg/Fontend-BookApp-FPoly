package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import retrofit2.Call;
import retrofit2.Callback;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartDeleteRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;

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

}
