package sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.model.ProfileModel;

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