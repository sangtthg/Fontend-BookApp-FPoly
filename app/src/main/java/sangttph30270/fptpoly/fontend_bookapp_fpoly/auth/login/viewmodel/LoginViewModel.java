package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.viewmodel;

import android.content.Context;
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
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;

public class LoginViewModel extends ViewModel {
    private final RepositoryLogin repositoryLogin;
    private final MutableLiveData<String> loginResponse = new MutableLiveData<>();
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public LoginViewModel(SharedPreferencesHelper sharedPreferencesHelper) {
        this.repositoryLogin = new RepositoryLogin(); // Khởi tạo RepositoryLogin ở đây
        this.sharedPreferencesHelper = sharedPreferencesHelper; // Khởi tạo SharedPreferencesHelper ở đây
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
                        Log.d("LoginViewModel", "Response Body: thong tin tai khoan " + responseBody);
                        JSONObject jsonObject = new JSONObject(responseBody);
                        if (jsonObject.has("status") && jsonObject.getString("status").equals("1")) {
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            JSONObject userObject = dataObject.getJSONObject("user");

                            int userId = userObject.getInt("user_id");
                            String username = userObject.getString("username");
                            String email = userObject.getString("email");
                            String avatar = userObject.getString("avatar");
                            String authToken = userObject.getString("auth_token");
                            String resetCode = userObject.getString("reset_code");
                            int userStatus = userObject.getInt("user_status");
                            String role = userObject.getString("role");
                            String token = dataObject.getString("token");

                            // Lưu dữ liệu vào SharedPreferences
                            sharedPreferencesHelper.saveUserData(userId, username, email, avatar, authToken, resetCode, userStatus, role, token);

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




