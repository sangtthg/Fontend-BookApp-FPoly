package frontend_book_market_app.polytechnic.client.auth.register.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import frontend_book_market_app.polytechnic.client.auth.register.model.OTPModel;

public interface ApiServiceRegister {
    @POST("api/send_otp")
    Call<ResponseBody> postOTP(@Body OTPModel otpModel);

    @POST("api/register")
    Call<ResponseBody> register(@Body OTPModel otpModel);
}