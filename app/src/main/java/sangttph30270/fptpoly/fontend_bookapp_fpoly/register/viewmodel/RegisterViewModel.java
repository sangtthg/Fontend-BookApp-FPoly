package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    // Gửi OTP thành công

    private final MutableLiveData<String> registerResponse = new MutableLiveData<>();
    private final MutableLiveData<String> otpVerificationResponse = new MutableLiveData<>();
    private OTPModel otpModel; // Biến để lưu trữ OTPModel

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
        repositoryRegister.postOTP(otpModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postOTPResponse.postValue("OTP đã được gửi thành công!");

                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        if (jsonObject.has("data")) {
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            if (dataArray.length() > 0) {
                                JSONObject dataObject = dataArray.getJSONObject(0);
                                String otp = dataObject.getString("otp");
                                otpModel.setOtp(otp); // Cập nhật OTP vào OTPModel
                                Log.d("RegisterViewModel", "Received OTP: " + otp);
                            }
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Log.e("RegisterViewModel", "Exception while parsing OTP response: " + e.getMessage());
                    }

                } else {
                    Log.e("RegisterViewModel", "Post OTP API thất bại: " + response.message());
                    postOTPResponse.postValue("Gửi OTP thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("RegisterViewModel", "Post OTP API onFailure: ", t);
                postOTPResponse.postValue("Gửi OTP thất bại! " + t.getMessage());
            }
        });
    }




    // Phương thức để set OTPModel vào ViewModel
    public void setOtpModel(OTPModel otpModel) {
        this.otpModel = otpModel;
    }

    public OTPModel getOtpModel() {
        return otpModel;
    }


    public void registerUser(String otp) {
        if (otpModel != null) {
            otpModel.setOtp(otp); // Gán OTP nhận được từ người dùng vào OTPModel
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
        } else {
            Log.e("RegisterViewModel", "OTPModel is null. Không thể đăng ký.");
            registerResponse.postValue("Đăng ký thất bại! OTP không hợp lệ.");
        }
    }
}
