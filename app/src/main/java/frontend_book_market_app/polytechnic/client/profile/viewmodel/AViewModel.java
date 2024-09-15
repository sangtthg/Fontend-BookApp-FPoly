package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import frontend_book_market_app.polytechnic.client.profile.model.UserProfileRequest;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryUserProfile;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AViewModel extends ViewModel {
    SharedPreferencesHelper sharedPreferencesHelper;
    private SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    public void fetchProfile(Context context) {
        RepositoryUserProfile repositoryUserProfile = new RepositoryUserProfile();

        repositoryUserProfile.getUserProfile(new Callback<UserProfileRequest>() {
            @Override
            public void onResponse(Call<UserProfileRequest> call, Response<UserProfileRequest> response) {
                if (response.isSuccessful() || response.body() != null) {
                    UserProfileRequest userProfileRequest = response.body();
                    String avatarNew = userProfileRequest.getUser().getAvatar();
                    setUserAvatar(context, avatarNew);
                }
            }

            @Override
            public void onFailure(Call<UserProfileRequest> call, Throwable throwable) {

            }
        });
    }

    public void setUserAvatar(Context context, String linkAvatar) {
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
        System.out.println("BUG: 1"+linkAvatar);
        sharedPreferencesHelper.updateAvatar(linkAvatar);
    }
}


