package frontend_book_market_app.polytechnic.client.auth.forgetpassword.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ForgotPasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.OTPForgetPasswordResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.network.RepositoryForgetPassword;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetPasswordViewModel extends ViewModel {
    private final String NAME = this.getClass().getSimpleName();

    private final RepositoryForgetPassword repositoryForgetPassword = new RepositoryForgetPassword();
    private final MutableLiveData<Boolean> otpSentSuccess = new MutableLiveData<>();
    private final MutableLiveData<OTPForgetPasswordResponse> otpResponse = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> otp = new MutableLiveData<>();
    private final MutableLiveData<Boolean> otpVerificationSuccess = new MutableLiveData<>();
    private final MutableLiveData<OTPForgetPasswordResponse.Data> otpData = new MutableLiveData<>();
    private MutableLiveData<String> storedOtp = new MutableLiveData<>();
    private MutableLiveData<String> otpId = new MutableLiveData<>();

    public void setOtpId(String otpId) {
        this.otpId.setValue(otpId);
    }

    public LiveData<String> getOtpId() {
        return otpId;
    }
    // Giả sử bạn có phương thức để đặt OTP
    public void setStoredOtp(String otp) {
        storedOtp.setValue(otp);
    }

    public LiveData<String> getStoredOtp() {
        return storedOtp;
    }
    public LiveData<OTPForgetPasswordResponse.Data> getOtpData() {
        return otpData;
    }

    public LiveData<Boolean> getOtpSentSuccess() {
        return otpSentSuccess;
    }

    public LiveData<OTPForgetPasswordResponse> getOtpResponse() {
        return otpResponse;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getOtp() {
        return otp;
    }

    public LiveData<Boolean> getOtpVerificationSuccess() {
        return otpVerificationSuccess;
    }

    public void sendForgotPasswordOtp(ForgotPasswordRequest forgotPasswordRequest) {
        Log.d(NAME, "Sending OTP request with: " + forgotPasswordRequest.toString());

        repositoryForgetPassword.postOTPForgetPassword(forgotPasswordRequest, new Callback<OTPForgetPasswordResponse>() {
            @Override
            public void onResponse(Call<OTPForgetPasswordResponse> call, Response<OTPForgetPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OTPForgetPasswordResponse responseBody = response.body();
                    OTPForgetPasswordResponse.Data data = responseBody.getData();
                    if (data != null) {
                        otpData.setValue(data);
                        storedOtp.setValue(data.getOtp());
                        setOtpId(String.valueOf(data.getId())); // Save OTP ID to LiveData
//                        Log.d(NAME, "Stored OTP ID: " + data.getId());
                        Log.d(NAME, "Stored OTP: " + data.getOtp());
                    } else {
                        Log.d(NAME, "Received OTP data is null");
                    }
                    otpSentSuccess.postValue(true);
                    logOtpDetails(responseBody);
                } else {
                    otpSentSuccess.postValue(false);
                    logErrorResponse("Send OTP", response);
                }
            }

            @Override
            public void onFailure(Call<OTPForgetPasswordResponse> call, Throwable t) {
                otpSentSuccess.postValue(false);
                Log.e(NAME, "Send OTP onFailure: ", t);
            }
        });
    }

    private void logOtpDetails(OTPForgetPasswordResponse response) {
        OTPForgetPasswordResponse.Data data = response.getData();
        if (data != null) {
            Log.d(NAME, "OTP Details: ");
            Log.d(NAME, "ID: " + data.getId());
            Log.d(NAME, "OTP: " + data.getOtp());
            Log.d(NAME, "Created At: " + data.getCreated_at());
            Log.d(NAME, "Email: " + data.getEmail());
            Log.d(NAME, "Type: " + data.getType());
        } else {
            Log.d(NAME, "No OTP data available.");
        }
    }

    private void logErrorResponse(String name, Response<?> response) {
        String logMessage = String.format("📛 Error: %s => Code: %d => Message: %s", name, response.code(), response.message());
        Log.e(NAME, logMessage);

        if (response.errorBody() != null) {
            try {
                String errorBody = response.errorBody().string();
                Log.e(NAME, "📛 Error Body: " + errorBody);
                errorMessage.postValue(errorBody);
            } catch (Exception e) {
                Log.e(NAME, "📛 Error reading error body", e);
                errorMessage.postValue(e.getMessage());
            }
        } else {
            Log.e(NAME, "📛 No error body");
            errorMessage.postValue("No error body");
        }
    }

}


