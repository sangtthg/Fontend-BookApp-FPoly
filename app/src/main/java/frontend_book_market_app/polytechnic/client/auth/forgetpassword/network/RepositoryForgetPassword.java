package frontend_book_market_app.polytechnic.client.auth.forgetpassword.network;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ChangePasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ChangePasswordResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.VerifyOtpRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.VerifyOtpResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ForgotPasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.OTPForgetPasswordResponse;
import frontend_book_market_app.polytechnic.client.utils.Common;

public class RepositoryForgetPassword {
    private final ApiServiceForgetPassword apiService;

    public RepositoryForgetPassword() {
        apiService = RetrofitManager.createService(ApiServiceForgetPassword.class, Common.API_URL, null);
    }

    public void postOTPForgetPassword(ForgotPasswordRequest forgotPasswordRequest, Callback<OTPForgetPasswordResponse> callback) {
        Call<OTPForgetPasswordResponse> call = apiService.sendForgotPasswordOtp(forgotPasswordRequest);
        call.enqueue(callback);
    }  // Method to verify OTP for Forgot Password

    public void postVerifyOTP(VerifyOtpRequest verifyOtpRequest, Callback<VerifyOtpResponse> callback) {
        Call<VerifyOtpResponse> call = apiService.verifyForgotPasswordOtp(verifyOtpRequest);
        call.enqueue(callback); // Implementation provided here
    }
    public void changePasswordAfterOtp(ChangePasswordRequest changePasswordRequest, Callback<ChangePasswordResponse> callback) {
        Call<ChangePasswordResponse> call = apiService.changePasswordAfterOtp(changePasswordRequest);
        call.enqueue(callback);
    }
    public void checkEmail(CheckEmailRequest checkEmailRequest, Callback<CheckEmailResponse> callback) {
        Call<CheckEmailResponse> call = apiService.checkEmail(checkEmailRequest);
        call.enqueue(callback);
    }

}
