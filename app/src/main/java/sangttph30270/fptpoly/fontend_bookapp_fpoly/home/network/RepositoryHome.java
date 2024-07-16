package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.Common.API_URL;

public class RepositoryHome {
    private final ApiServiceHome apiService;

    public RepositoryHome() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiServiceHome.class);
    }

    public void fetchApiHomePageBook(Callback<HomeBookResponse> callback) {
        Call<HomeBookResponse> call = apiService.getFirstApiProducts();
        call.enqueue(callback);
    }
}
