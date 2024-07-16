package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeModel;

public class RepositoryHome {
    private static final String BASE_URL = "https://book-manager-phi.vercel.app/";
    private final ApiServiceHome apiService;

    public RepositoryHome() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Đặt URL cơ sở
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiServiceHome.class);
    }

    public void fetchFirstApiProducts(Callback<List<HomeModel>> callback) {
        Call<List<HomeModel>> call = apiService.getFirstApiProducts();
        call.enqueue(callback);
    }

}
