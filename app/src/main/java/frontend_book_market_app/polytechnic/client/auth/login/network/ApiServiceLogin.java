package frontend_book_market_app.polytechnic.client.auth.login.network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiServiceLogin {

    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_id") String deviceID,
            @Field("device_token") String deviceToken
    );



    @POST("api/address_detail/get")
    Call<ResponseBody> getAddress(@Header("Authorization") String token, @Body RequestBody body);

}