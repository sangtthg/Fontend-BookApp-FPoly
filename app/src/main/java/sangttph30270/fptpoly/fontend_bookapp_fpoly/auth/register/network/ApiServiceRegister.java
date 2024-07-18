package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.register.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.register.model.OTPModel;

public interface ApiServiceRegister {
    @POST("send_otp")
    Call<ResponseBody> postOTP(@Body OTPModel otpModel);

    @POST("register")
    Call<ResponseBody> register(@Body OTPModel otpModel);
}