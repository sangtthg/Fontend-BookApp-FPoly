package frontend_book_market_app.polytechnic.client.auth.forgetpassword.network;

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
    }


}
