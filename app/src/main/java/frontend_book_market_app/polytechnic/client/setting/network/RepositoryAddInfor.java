package frontend_book_market_app.polytechnic.client.setting.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.setting.model.AddressRequestModel;
import frontend_book_market_app.polytechnic.client.setting.model.DeleteAddressRequestModel;
import frontend_book_market_app.polytechnic.client.utils.URL;

public class RepositoryAddInfor {
    private final ApiServiceAddInforUser apiServiceAddInforUser;

    public RepositoryAddInfor() {
        apiServiceAddInforUser = RetrofitManager.createService(ApiServiceAddInforUser.class, URL.API_URL, null);
    }

    public void addAddress(String token, AddressRequestModel addressRequestModel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiServiceAddInforUser.addAddress("Bearer " + token, addressRequestModel);
        call.enqueue(callback);
    }

    public void deleteAddress(String token, int addressId, Callback<ResponseBody> callback) {
        DeleteAddressRequestModel requestModel = new DeleteAddressRequestModel(addressId);
        Call<ResponseBody> call = apiServiceAddInforUser.deleteAddress("Bearer " + token, requestModel);
        call.enqueue(callback);
    }
}
