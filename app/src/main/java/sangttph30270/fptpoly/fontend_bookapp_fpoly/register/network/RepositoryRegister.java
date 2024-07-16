package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.network;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.Common.API_URL;


import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.Common;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network.ApiServiceHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.model.OTPModel;

public class RepositoryRegister {
    private final ApiServiceRegister apiService;

    public RepositoryRegister() {
        apiService = RetrofitManager.createService(ApiServiceRegister.class, Common.API_URL);
    }

    public void postOTP(OTPModel otpModel, Callback<Void> callback) {
        Call<Void> call = apiService.postOTP(otpModel);
        call.enqueue(callback);
    }
}