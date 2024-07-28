package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.core.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.Common;

public class RepositoryLogin {
    private final ApiServiceLogin apiService;

    public RepositoryLogin() {
        apiService = RetrofitManager.createService(ApiServiceLogin.class, Common.API_URL, null);
    }

    public void login(String email, String password, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.login(email, password);
        call.enqueue(callback);
    }

    public void getAddress(String token, RequestBody body, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.getAddress(token, body);
        call.enqueue(callback);
    }
}
