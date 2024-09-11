package frontend_book_market_app.polytechnic.client.setting.viewmodel;

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
import frontend_book_market_app.polytechnic.client.setting.model.AddressRequestModel;
import frontend_book_market_app.polytechnic.client.setting.network.RepositoryAddInfor;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class SettingViewModel extends ViewModel {
    private final RepositoryAddInfor repositoryAddInfor;
    private final MutableLiveData<String> addAddressResponse = new MutableLiveData<>();
    private final MutableLiveData<String> deleteAddressResponse = new MutableLiveData<>();
    private final SharedPreferencesHelper sharedPreferencesHelper;

    // Constructor
    public SettingViewModel(SharedPreferencesHelper sharedPreferencesHelper) {
        this.repositoryAddInfor = new RepositoryAddInfor();
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    public LiveData<String> getAddAddressResponse() {
        return addAddressResponse;
    }

    public LiveData<String> getDeleteAddressResponse() {
        return deleteAddressResponse;
    }

    public void addAddress(String token, AddressRequestModel addressRequestModel) {
        repositoryAddInfor.addAddress(token, addressRequestModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        Log.d("SettingViewModel", "Response Body: " + responseBody);
                        JSONObject jsonObject = new JSONObject(responseBody);
                        if (jsonObject.has("status") && jsonObject.getString("status").equals("1")) {
                            addAddressResponse.postValue("Thêm địa chỉ thành công");
//                            sharedPreferencesHelper.saveAddress(addressRequestModel);
                        } else {
                            String errorMessage = jsonObject.optString("message", "Thêm địa chỉ thất bại");
                            addAddressResponse.postValue("Thêm địa chỉ thất bại: " + errorMessage);
                        }
                    } else {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Đã xảy ra lỗi";
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.optString("message", "Thêm địa chỉ thất bại");
                        addAddressResponse.postValue("Thêm địa chỉ thất bại: " + errorMessage);
                        Log.e("SettingViewModel", "Error Response Body: " + errorBody);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    addAddressResponse.postValue("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("SettingViewModel", "Add Address API onFailure: ", t);
                addAddressResponse.postValue("Lỗi: " + t.getMessage());
            }
        });
    }
    public void deleteAddress(String token, int addressId) {
        repositoryAddInfor.deleteAddress(token, addressId, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        Log.d("SettingViewModel", "Response Body: " + responseBody);
                        JSONObject jsonObject = new JSONObject(responseBody);
                        if (jsonObject.has("status") && jsonObject.getString("status").equals("1")) {
                            deleteAddressResponse.postValue("Xóa địa chỉ thành công");
                            // Có thể cần làm mới danh sách địa chỉ sau khi xóa
                        } else {
                            String errorMessage = jsonObject.optString("message", "Xóa địa chỉ thất bại");
                            deleteAddressResponse.postValue("Xóa địa chỉ thất bại: " + errorMessage);
                        }
                    } else {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Đã xảy ra lỗi";
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.optString("message", "Xóa địa chỉ thất bại");
                        deleteAddressResponse.postValue("Xóa địa chỉ thất bại: " + errorMessage);
                        Log.e("SettingViewModel", "Error Response Body: " + errorBody);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    deleteAddressResponse.postValue("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("SettingViewModel", "Delete Address API onFailure: ", t);
                deleteAddressResponse.postValue("Lỗi: " + t.getMessage());
            }
        });
    }


    public SharedPreferencesHelper getSharedPreferencesHelper() {
        return sharedPreferencesHelper;
    }
}
