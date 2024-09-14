package frontend_book_market_app.polytechnic.client.auth.forgetpassword.network;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ChangePasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ChangePasswordResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ForgotPasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.OTPForgetPasswordResponse;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.VerifyOtpRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.VerifyOtpResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServiceForgetPassword {

    @POST("api/otp/forgot_password")
    Call<OTPForgetPasswordResponse> sendForgotPasswordOtp(@Body ForgotPasswordRequest forgotPasswordRequest);

    // New method to verify OTP
    @POST("api/verify_otp_forgot_password")
    Call<VerifyOtpResponse> verifyForgotPasswordOtp(@Body VerifyOtpRequest request);

    // New method for changing the password
    @POST("api/forgot_password")
    Call<ChangePasswordResponse> changePasswordAfterOtp(@Body ChangePasswordRequest request);

    // New method to check if the email already exists
    @POST("api/send_otp")
    Call<CheckEmailResponse> checkEmail(@Body CheckEmailRequest request);

}


