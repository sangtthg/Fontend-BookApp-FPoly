package sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.model.AddressRequestModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.model.DeleteAddressRequestModel;

public interface ApiServiceAddInforUser {

    @POST("api/address_detail/add")
    Call<ResponseBody> addAddress(@Header("Authorization") String token, @Body AddressRequestModel body);

    @POST("api/address_detail/delete")
    Call<ResponseBody> deleteAddress(@Header("Authorization") String token, @Body DeleteAddressRequestModel body);
}
