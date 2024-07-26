package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.register.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.core.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.register.model.OTPModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.Common;

public class RepositoryRegister {
    private final ApiServiceRegister apiService;

    public RepositoryRegister() {
        apiService = RetrofitManager.createService(ApiServiceRegister.class, Common.API_URL, null);
    }

    public void postOTP(OTPModel otpModel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.postOTP(otpModel);
        call.enqueue(callback);
    }

    public void register(OTPModel otpModel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = apiService.register(otpModel);
        call.enqueue(callback);
    }

}