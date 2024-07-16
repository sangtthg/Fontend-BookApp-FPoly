package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.model.OTPModel;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.network.RepositoryRegister;

public class RegisterViewModel extends ViewModel {
    private final RepositoryRegister repositoryRegister = new RepositoryRegister();
    private final MutableLiveData<String> postOTPResponse = new MutableLiveData<>();
    private final MutableLiveData<String> registerResponse = new MutableLiveData<>();
    private final MutableLiveData<String> otpVerificationResponse = new MutableLiveData<>();


    public RegisterViewModel() {
        Log.d("RegisterViewModel", "Khởi tạo RegisterViewModel");
    }

    public MutableLiveData<String> getPostOTPResponse() {
        return postOTPResponse;
    }

    public MutableLiveData<String> getRegisterResponse() {
        return registerResponse;
    }

    public MutableLiveData<String> getOtpVerificationResponse() {
        return otpVerificationResponse;
    }

    public void postOTPAPI(OTPModel otpModel) {
        repositoryRegister.postOTP(otpModel, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    postOTPResponse.postValue("OTP đã được gửi thành công!");
                } else {
                    Log.e("RegisterViewModel", "Post OTP API thất bại: " + response.message());
                    postOTPResponse.postValue("Gửi OTP thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("RegisterViewModel", "Post OTP API onFailure: ", t);
                postOTPResponse.postValue("Gửi OTP thất bại! " + t.getMessage()); // Đưa ra thông báo lỗi chi tiết hơn
            }

        });
    }


    public void registerUser(String email, String username, String password) {
        OTPModel otpModel = new OTPModel(email);
        repositoryRegister.registerUser(otpModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    registerResponse.postValue("Đăng ký thành công!");
                } else {
                    Log.e("RegisterViewModel", "Đăng ký thất bại: " + response.message());
                    registerResponse.postValue("Đăng ký thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("RegisterViewModel", "Đăng ký thất bại onFailure: ", t);
                registerResponse.postValue("Đăng ký thất bại! " + t.getMessage());
            }
        });
    }

    public void verifyOTPAPI(OTPModel otpModel) {
        repositoryRegister.verifyOTP(otpModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    registerResponse.postValue("Đăng ký thành công!");
                } else {
                    Log.e("RegisterViewModel", "Đăng ký thất bại: " + response.message());
                    registerResponse.postValue("Đăng ký thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("RegisterViewModel", "Đăng ký thất bại onFailure: ", t);
                registerResponse.postValue("Đăng ký thất bại! " + t.getMessage());
            }
        });
    }
}
