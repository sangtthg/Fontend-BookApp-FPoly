package frontend_book_market_app.polytechnic.client.auth.forgetpassword.viewmodel;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ChangePasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ChangePasswordResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.CheckEmailResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ForgotPasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.OTPForgetPasswordResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.VerifyOtpRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.VerifyOtpResponse;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.network.RepositoryForgetPassword;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.view.OTPScreenForgetPass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetPasswordViewModel extends ViewModel {
    private final String NAME = this.getClass().getSimpleName();

    private final RepositoryForgetPassword repositoryForgetPassword = new RepositoryForgetPassword();
    private final MutableLiveData<Boolean> otpSentSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> otpVerifiedSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<String> otp = new MutableLiveData<>();
    private final MutableLiveData<OTPForgetPasswordResponse.Data> otpData = new MutableLiveData<>();
    private MutableLiveData<String> emailLiveData = new MutableLiveData<>();
    private MutableLiveData<String> otpIdLiveData;
    private MutableLiveData<String> otpLiveData;
    private MutableLiveData<Boolean> passwordChangedSuccess;

    public ForgetPasswordViewModel() {
        otpLiveData = new MutableLiveData<>();
        otpIdLiveData = new MutableLiveData<>();
        passwordChangedSuccess = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }
    public LiveData<Boolean> getPasswordChangedSuccess() {
        return passwordChangedSuccess;
    }
    public LiveData<OTPForgetPasswordResponse.Data> getOtpData() {
        return otpData;
    }

    public LiveData<Boolean> getOtpSentSuccess() {
        return otpSentSuccess;
    }

    public LiveData<Boolean> getOtpVerifiedSuccess() {
        return otpVerifiedSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getOtp() {
        return otp;
    }    // Add getter methods for LiveData

    public LiveData<String> getEmailLiveData() {
        return emailLiveData;
    }

    public LiveData<String> getOtpIdLiveData() {
        return otpIdLiveData;
    }

    public LiveData<String> getOtpLiveData() {
        return otpLiveData;
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
                        otpIdLiveData.setValue(String.valueOf(data.getId())); // Update OTP ID
                        emailLiveData.setValue(data.getEmail()); // Update Email
                        otpLiveData.setValue(data.getOtp()); // Update OTP

                        Log.d(NAME, "Stored OTP: " + data.getOtp());
                        Log.d(NAME, "OTP ID: " + data.getId());
                        Log.d(NAME, "Email: " + data.getEmail());

                        otpSentSuccess.postValue(true);
                    } else {
                        Log.d(NAME, "Received OTP data is null");
                        otpSentSuccess.postValue(false);
                    }
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

    public void verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        repositoryForgetPassword.postVerifyOTP(verifyOtpRequest, new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VerifyOtpResponse responseBody = response.body();
                    boolean success = responseBody.isSuccess();

                    otpVerifiedSuccess.postValue(success);

                    if (success) {
                        Log.d(TAG, "OTP chính xác, mời bạn đổi mật khẩu.");
                    } else {
                        Log.d(TAG, "OTP không chính xác. Message: " + responseBody.getMessage());
                        errorMessage.postValue(responseBody.getMessage());
                    }
                } else {
                    otpVerifiedSuccess.postValue(false);
                    Log.e(TAG, "Verify OTP error: " + response.errorBody());
                    errorMessage.postValue("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                otpVerifiedSuccess.postValue(false);
                Log.e(TAG, "Verify OTP onFailure: ", t);
                errorMessage.postValue("Failure: " + t.getMessage());
            }
        });
    }



    public void changePasswordAfterOtp(ChangePasswordRequest changePasswordRequest) {
        repositoryForgetPassword.changePasswordAfterOtp(changePasswordRequest, new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChangePasswordResponse responseBody = response.body();
                    boolean success = responseBody.getStatus().equals("1");

                    passwordChangedSuccess.postValue(success);

                    if (success) {
                        Log.d(TAG, "Đổi mật khẩu thành công. ");
                    } else {
                        Log.d(TAG, "Đổi mật khẩu thất bại. Message: " + responseBody.getMessage());
                        errorMessage.postValue(responseBody.getMessage());
                    }
                } else {
                    passwordChangedSuccess.postValue(false);
                    Log.e(TAG, "Change password error: " + response.errorBody());
                    errorMessage.postValue("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                passwordChangedSuccess.postValue(false);
                Log.e(TAG, "Change password onFailure: ", t);
                errorMessage.postValue("Failure: " + t.getMessage());
            }
        });
    }
    public void checkEmail(String email) {
        CheckEmailRequest request = new CheckEmailRequest(email);
        repositoryForgetPassword.checkEmail(request, new Callback<CheckEmailResponse>() {
            @Override
            public void onResponse(Call<CheckEmailResponse> call, Response<CheckEmailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CheckEmailResponse checkEmailResponse = response.body();
                    if (checkEmailResponse.isEmailRegistered()) {
                        // Email exists, allow sending OTP
                        Log.d(TAG, "Email đã tồn tại");
                        ForgotPasswordRequest request = new ForgotPasswordRequest(email);
                        sendForgotPasswordOtp(request);
                        otpSentSuccess.postValue(true); // Or any appropriate action
                    } else {
                        // Email does not exist
                        Log.d(TAG, "Email không được đăng ký");
                        errorMessage.postValue("Email chưa có trong hệ thống");
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
