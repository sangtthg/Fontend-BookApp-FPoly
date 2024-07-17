package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.Common;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.model.OTPModel;

public class RepositoryRegister {
    private final ApiServiceRegister apiService;

    public RepositoryRegister() {
        apiService = RetrofitManager.createService(ApiServiceRegister.class, Common.API_URL);
    }

    public void postOTP(OTPModel otpModel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.postOTP(otpModel);
        call.enqueue(callback);
    }

    public void registerUser(OTPModel otpModel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.register(otpModel);
        call.enqueue(callback);
    }

}