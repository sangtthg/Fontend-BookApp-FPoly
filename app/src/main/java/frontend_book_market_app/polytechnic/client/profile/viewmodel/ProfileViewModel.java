package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import frontend_book_market_app.polytechnic.client.home.model.ReviewResponse;
import frontend_book_market_app.polytechnic.client.home.network.RepositoryHome;
import frontend_book_market_app.polytechnic.client.profile.model.ProfileModel;
import frontend_book_market_app.polytechnic.client.profile.model.UserProfileRequest;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePass;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePicture;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryUserProfile;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<ProfileModel> profileData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passwordChangeSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> pictureChangeSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final RepositoryChangePass repositoryChangePass;
    private final RepositoryChangePicture repositoryChangePicture;
    private final RepositoryUserProfile repositoryUserProfile;
    private final SharedPreferencesHelper sharedPreferencesHelper;


    public ProfileViewModel(RepositoryChangePass repositoryChangePass, RepositoryChangePicture repositoryChangePicture, SharedPreferencesHelper sharedPreferencesHelper) {
        this.repositoryChangePass = repositoryChangePass;
        this.repositoryChangePicture = repositoryChangePicture;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.repositoryUserProfile = new RepositoryUserProfile();
    }

    public String getToken() {
        return sharedPreferencesHelper.getToken();
    }

    public LiveData<ProfileModel> getProfileData() {
        return profileData;
    }

    public LiveData<Boolean> getPictureChangeSuccess() {
        return pictureChangeSuccess;
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
        String token = sharedPreferencesHelper.getToken(); // Get token from SharedPreferencesHelper
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
