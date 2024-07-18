package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.network.RepositoryLogin;

public class LoginViewModel extends ViewModel {
    private final RepositoryLogin repositoryLogin;
    private final MutableLiveData<String> loginResponse = new MutableLiveData<>();

    public LoginViewModel() {
        this.repositoryLogin = new RepositoryLogin(); // Khởi tạo RepositoryLogin ở đây
    }

    public LiveData<String> getLoginResponse() {
        return loginResponse;
    }

    public void login(String email, String password) {
        repositoryLogin.login(email, password, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        Log.d("LoginViewModel", "Response Body: " + responseBody);
                        JSONObject jsonObject = new JSONObject(responseBody);
                        if (jsonObject.has("status") && jsonObject.getString("status").equals("1")) {
                            loginResponse.postValue("Đăng nhập thành công");
                        } else {
                            String errorMessage = jsonObject.optString("message", "Đăng nhập thất bại");
                            loginResponse.postValue("Đăng nhập thất bại: " + errorMessage);
                        }
                    } else {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Đã xảy ra lỗi";
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.optString("message", "Đăng nhập thất bại");
                        loginResponse.postValue("Đăng nhập thất bại: " + errorMessage);
                        Log.e("LoginViewModel", "Error Response Body: " + errorBody);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    loginResponse.postValue("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("LoginViewModel", "Login API onFailure: ", t);
                loginResponse.postValue("Lỗi: " + t.getMessage());
            }
        });
    }


}
