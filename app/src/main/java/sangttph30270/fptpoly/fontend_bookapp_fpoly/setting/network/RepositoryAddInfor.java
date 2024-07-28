package sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.core.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.model.AddressRequestModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.model.DeleteAddressRequestModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.Common;

public class RepositoryAddInfor {
    private final ApiServiceAddInforUser apiServiceAddInforUser;

    public RepositoryAddInfor() {
        apiServiceAddInforUser = RetrofitManager.createService(ApiServiceAddInforUser.class, Common.API_URL, null);
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
