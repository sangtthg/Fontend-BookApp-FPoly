package sangttph30270.fptpoly.fontend_bookapp_fpoly.login.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryLogin {
    private static final String BASE_URL = "https://book-manager-phi.vercel.app/api/";
    private final ApiServiceLogin apiService;

    public RepositoryLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Đặt URL cơ sở
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiServiceLogin.class);
    }

    public ApiServiceLogin getApiService() {
        return apiService;
    }
}
