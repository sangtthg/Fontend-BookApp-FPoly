package frontend_book_market_app.polytechnic.client.profile.network;

import static frontend_book_market_app.polytechnic.client.utils.Common.API_URL;

import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.profile.model.AddressDeleteRequest;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;
import frontend_book_market_app.polytechnic.client.profile.model.UpdateAddressModel;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RepositoryAddress {
    private final ApiServiceAddress apiService;

    public RepositoryAddress() {
        apiService = RetrofitManager.createService(ApiServiceAddress.class, API_URL, null);
    }

    // Phương thức lấy tất cả địa chỉ
    public void getAllAddresses(String token, RequestBody body, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.getAllAddresses("Bearer " + token, body);
        call.enqueue(callback);
    }

    // Phương thức thêm địa chỉ (đã có)
    public void addAddress(String token, AddressModel addressModel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.addAddress("Bearer " + token, addressModel);
        call.enqueue(callback);
    }

    // Method to delete an address
    public void deleteAddress(String token, AddressDeleteRequest addressDeleteRequest, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.deleteAddress("Bearer " + token, addressDeleteRequest);
        call.enqueue(callback);
    }

    // Method to update an address
    public void updateAddress(String token, UpdateAddressModel updatedAddress, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.updateAddress("Bearer " + token, updatedAddress);
        call.enqueue(callback);
    }
}
