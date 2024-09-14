package frontend_book_market_app.polytechnic.client.auth.login.network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.utils.Common;

public class RepositoryLogin {
    private final ApiServiceLogin apiService;

    public RepositoryLogin() {
        apiService = RetrofitManager.createService(ApiServiceLogin.class, Common.API_URL, null);
    }

    public void login(String email, String password, String deviceID, String deviceToken, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.login(email, password,deviceID, deviceToken);
        call.enqueue(callback);
    }

    public void getAddress(String token, RequestBody body, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.getAddress(token, body);
        call.enqueue(callback);
    }
}
