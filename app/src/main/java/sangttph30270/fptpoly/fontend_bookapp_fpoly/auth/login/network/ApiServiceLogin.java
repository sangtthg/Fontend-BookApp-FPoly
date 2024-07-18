package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServiceLogin {

    @FormUrlEncoded
    @POST("api/login") // Thay đổi URL endpoint nếu cần
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String password
    );

}