package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import retrofit2.Call;
import retrofit2.Callback;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.Common.API_URL;

public class RepositoryHome {
    private final ApiServiceHome apiService;

    public RepositoryHome() {
        apiService = RetrofitManager.createService(ApiServiceHome.class, API_URL);
    }

    public void fetchApiHomePageBook(Callback<HomeBookResponse> callback) {
        Call<HomeBookResponse> call = apiService.getApiHomeList();
        call.enqueue(callback);
    }
}
