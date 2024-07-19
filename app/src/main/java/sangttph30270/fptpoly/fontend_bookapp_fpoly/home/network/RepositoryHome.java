package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import retrofit2.Call;
import retrofit2.Callback;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.RetrofitManager;
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

    public void fetchBookDetail(int bookId, String token, Callback<DetailBookResponse> callback) {

        String authHeader = token != null ? "Bearer " + token : null;
        Call<DetailBookResponse> call = apiService.getBookDetail(bookId, authHeader);
        call.enqueue(callback);
    }

}
