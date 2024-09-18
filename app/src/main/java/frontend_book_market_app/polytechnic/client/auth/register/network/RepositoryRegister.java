package frontend_book_market_app.polytechnic.client.auth.register.network;


import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.auth.register.model.OTPModel;
import frontend_book_market_app.polytechnic.client.utils.URL;

public class RepositoryRegister {
    private final ApiServiceRegister apiService;

    public RepositoryRegister() {
        apiService = RetrofitManager.createService(ApiServiceRegister.class, URL.API_URL, null);
    }

    public void postOTP(OTPModel otpModel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.postOTP(otpModel);
        call.enqueue(callback);
    }

    public void register(OTPModel otpModel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.register(otpModel);
        call.enqueue(callback);
    }
    public void checkEmail(CheckEmailRequest checkEmailRequest, Callback<CheckEmailResponse> callback) {
        Call<CheckEmailResponse> call = apiService.checkEmail(checkEmailRequest);
        call.enqueue(callback);
    }
}