package frontend_book_market_app.polytechnic.client.auth.login.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.auth.login.model.AddressModel;
import frontend_book_market_app.polytechnic.client.auth.login.network.RepositoryLogin;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class LoginViewModel extends ViewModel {
    private final RepositoryLogin repositoryLogin;
    private final MutableLiveData<String> loginResponse = new MutableLiveData<>();
    private final MutableLiveData<List<AddressModel>> addressResponse = new MutableLiveData<>();
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public LoginViewModel(SharedPreferencesHelper sharedPreferencesHelper) {
        this.repositoryLogin = new RepositoryLogin();
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    public LiveData<String> getLoginResponse() {
        return loginResponse;
    }

    public LiveData<List<AddressModel>> getAddressResponse() {
        return addressResponse;
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
                            String defaultAddress = userObject.optString("address");

                            sharedPreferencesHelper.saveUserData(userId, username, email, avatar, authToken, resetCode, userStatus, role, token, defaultAddress);

                            loginResponse.postValue("Đăng nhập thành công");

                            // Gọi getAddress sau khi đăng nhập thành công
                            getAddress(userId, token);
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

    private void getAddress(int userId, String token) {
        String authHeader = "Bearer " + token;
        RequestBody body = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(userId));

        repositoryLogin.getAddress(authHeader, body, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        Log.d("LoginViewModel", "Address Response Body: " + responseBody);

                        JSONObject jsonObject = new JSONObject(responseBody);
                        if (jsonObject.has("status") && jsonObject.getString("status").equals("1")) {
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            List<AddressModel> addresses = new ArrayList<>();

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject addressObject = dataArray.getJSONObject(i);

                                AddressModel address = new AddressModel(
                                        addressObject.getInt("address_id"),
                                        addressObject.getInt("user_id"),
                                        addressObject.getString("name"),
                                        addressObject.getString("phone"),
                                        addressObject.getString("address"),
                                        addressObject.getString("address_type"),
                                        addressObject.getInt("status"),
                                        addressObject.getString("created_at"),
                                        addressObject.getString("updated_at"),
                                        addressObject.getBoolean("is_default")
                                );

                                addresses.add(address);
                                Log.d("LoginViewModel", "Address " + i + ": " + addressObject.toString());
                            }

                            // Lưu địa chỉ vào SharedPreferencesHelper
                            sharedPreferencesHelper.saveAddresses(addresses);

                            // Cập nhật dữ liệu vào LiveData
                            addressResponse.postValue(addresses);
                        } else {
                            String errorMessage = jsonObject.optString("message", "Không thể lấy địa chỉ");
                            addressResponse.postValue(new ArrayList<>());
                            Log.e("LoginViewModel", "Address Error: " + errorMessage);
                        }
                    } else {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Đã xảy ra lỗi";
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.optString("message", "Không thể lấy địa chỉ");
                        addressResponse.postValue(new ArrayList<>());
                        Log.e("LoginViewModel", "Address Error Response Body: " + errorBody);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    addressResponse.postValue(new ArrayList<>());
                    Log.e("LoginViewModel", "Address Exception: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("LoginViewModel", "Address API onFailure: ", t);
                addressResponse.postValue(new ArrayList<>());
            }
        });
    }

}