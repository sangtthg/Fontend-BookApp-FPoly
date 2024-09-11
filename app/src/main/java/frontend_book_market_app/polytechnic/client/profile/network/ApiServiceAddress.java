package frontend_book_market_app.polytechnic.client.profile.network;

import frontend_book_market_app.polytechnic.client.profile.model.AddressDeleteRequest;
import frontend_book_market_app.polytechnic.client.profile.model.UpdateAddressModel;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;
import retrofit2.http.Path;

public interface ApiServiceAddress {
    @POST("api/address_detail/add")
    Call<ResponseBody> addAddress(
            @Header("Authorization") String token,
            @Body AddressModel addressModel
    );

    @POST("api/address_detail/get")
    Call<ResponseBody> getAllAddresses(
            @Header("Authorization") String token,
            @Body RequestBody body
    );


    @POST("api/address_detail/delete")
    Call<ResponseBody> deleteAddress(
            @Header("Authorization") String token,
            @Body AddressDeleteRequest request
    );

    @POST("api/address_detail/update")
    Call<ResponseBody> updateAddress(
            @Header("Authorization") String token,
            @Body UpdateAddressModel updatedAddress // ID included in the request body
    );

}
