package frontend_book_market_app.polytechnic.client.auth.register.network;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailResponse;
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

    // New method to check if the email already exists
    @POST("api/send_otp")
    Call<CheckEmailResponse> checkEmail(@Body CheckEmailRequest request);
}