package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import frontend_book_market_app.polytechnic.client.profile.model.ProfileModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileModel> profileData = new MutableLiveData<>();

    public LiveData<ProfileModel> getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileModel profile) {
        profileData.setValue(profile);
    }

    public void clearProfileData() {
        profileData.setValue(null);
    }
}