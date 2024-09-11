package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import frontend_book_market_app.polytechnic.client.profile.model.ProfileModel;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePass;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileModel> profileData = new MutableLiveData<>();
    private MutableLiveData<Boolean> passwordChangeSuccess = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final RepositoryChangePass repositoryChangePass;
    private final SharedPreferences sharedPreferences;

    public ProfileViewModel(SharedPreferences sharedPreferences, RepositoryChangePass repositoryChangePass) {
        this.sharedPreferences = sharedPreferences;
        this.repositoryChangePass = repositoryChangePass;
    }

    public LiveData<ProfileModel> getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileModel profile) {
        profileData.setValue(profile);
    }

    public void clearProfileData() {
        profileData.setValue(null);
    }

    public LiveData<Boolean> getPasswordChangeSuccess() {
        return passwordChangeSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void changePassword(String oldPassword, String newPassword) {
        String token = sharedPreferences.getString("token", ""); // Get token from SharedPreferences
        repositoryChangePass.changePassword(token, oldPassword, newPassword, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    passwordChangeSuccess.setValue(true);
                } else {
                    String error = "Error: " + response.code() + " - " + response.message();
                    errorMessage.setValue(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error = "Network Error: " + t.getMessage();
                errorMessage.setValue(error);
            }
        });
    }
}
