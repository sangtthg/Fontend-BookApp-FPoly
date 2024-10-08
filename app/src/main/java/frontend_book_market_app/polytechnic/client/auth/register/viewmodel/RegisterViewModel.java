package frontend_book_market_app.polytechnic.client.auth.register.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.auth.register.model.OTPModel;
import frontend_book_market_app.polytechnic.client.auth.register.network.RepositoryRegister;

public class RegisterViewModel extends ViewModel {
    private final RepositoryRegister repositoryRegister = new RepositoryRegister();
    private final MutableLiveData<String> postOTPResponse = new MutableLiveData<>();
    private final MutableLiveData<String> otpLiveData;
    private final MutableLiveData<String> idotpLiveData = new MutableLiveData<>();
    private OTPModel otpModel; // Biến để lưu trữ OTPModel
    private final MutableLiveData<String> errorMessage;

    public RegisterViewModel() {
        otpLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }
    public MutableLiveData<String> getPostOTPResponse() {
        return postOTPResponse;
    }
    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public MutableLiveData<String> getOtpLiveData() {
        return otpLiveData;
    }

    private final MutableLiveData<String> registerResponse = new MutableLiveData<>();


    public OTPModel getIDOtpModel() {
        return otpModel;
    }

    public void setIDOtpModel(OTPModel otpModel) {
        this.otpModel = otpModel;
    }

    public OTPModel getIdotpLiveData() {
        return otpModel;
    }

    public OTPModel getOtpModel() {
        return otpModel;
    }

    public void setOtpModel(OTPModel otpModel) {
        this.otpModel = otpModel;
    }

    public MutableLiveData<String> getRegisterResponse() {
        return registerResponse;
    }


    public void postOTPAPI(OTPModel otpModel) {
        this.otpModel = otpModel; // Lưu OTPModel hiện tại
        repositoryRegister.postOTP(otpModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postOTPResponse.postValue("OTP đã được gửi thành công!");

                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        if (jsonObject.has("data")) {
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            String otp = dataObject.getString("otp");
                            String otpId = dataObject.getString("id");
                            otpLiveData.postValue(otp);
                            idotpLiveData.postValue(otpId);
                            otpModel.setOtp(otp);
                            otpModel.setOtp_id(otpId);
                            Log.d("RegisterViewModel", "Received OTP: " + otp);
                            Log.d("RegisterViewModel", "Received OTP ID: " + otpId);
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Log.e("RegisterViewModel", "Exception while parsing OTP response: " + e.getMessage());
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Log.e("RegisterViewModel", "Error Response Body: " + errorBody);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("RegisterViewModel", "Post OTP API thất bại: " + response.message());
                    postOTPResponse.postValue("Gửi OTP thất bại: " + response.message() + " | Mã trạng thái: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("RegisterViewModel", "Post OTP API onFailure: ", t);
                postOTPResponse.postValue("Gửi OTP thất bại! " + t.getMessage());
            }
        });
    }


    public void register(OTPModel otpModel) {
        repositoryRegister.register(otpModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registerResponse.postValue("Success");
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        registerResponse.postValue(errorMessage);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        registerResponse.postValue("Failure: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                registerResponse.postValue("Failure: " + t.getMessage());
            }
        });
    }

    public void checkEmail(String email) {
        CheckEmailRequest request = new CheckEmailRequest(email);
        repositoryRegister.checkEmail(request, new Callback<CheckEmailResponse>() {
            @Override
            public void onResponse(Call<CheckEmailResponse> call, Response<CheckEmailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CheckEmailResponse checkEmailResponse = response.body();
                    if (checkEmailResponse.isEmailRegistered()) {
                        Log.d(TAG, "Email is already registered.");
                        errorMessage.postValue("Email đã tồn tại");
                    } else {
                        Log.d(TAG, "Email is available.");
                        errorMessage.postValue("Chuẩn bị đăng ký");
                    }
                } else {
                    Log.e(TAG, "Check email error: " + response.errorBody());
                    errorMessage.postValue("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CheckEmailResponse> call, Throwable t) {
                Log.e(TAG, "Check email onFailure: ", t);
                errorMessage.postValue("Failure: " + t.getMessage());
            }
        });
    }


}