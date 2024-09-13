package frontend_book_market_app.polytechnic.client.auth.forgetpassword.network;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ForgotPasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.OTPForgetPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServiceForgetPassword {

    @POST("api/otp/forgot_password")
    Call<OTPForgetPasswordResponse> sendForgotPasswordOtp(@Body ForgotPasswordRequest forgotPasswordRequest);


}
