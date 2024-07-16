package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryRegister {
    private static final String BASE_URL = "https://book-manager-phi.vercel.app/";
    private final ApiServiceRegister apiService;

    public RepositoryRegister() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiServiceRegister.class);
    }

    public ApiServiceRegister getApiService() {
        return apiService;
    }
}