package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePass;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePicture;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    private final RepositoryChangePass repositoryChangePass;
    private final RepositoryChangePicture repositoryChangePicture;
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public ProfileViewModelFactory(RepositoryChangePass repositoryChangePass, RepositoryChangePicture repositoryChangePicture, SharedPreferencesHelper sharedPreferencesHelper) {
        this.repositoryChangePass = repositoryChangePass;
        this.repositoryChangePicture = repositoryChangePicture;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(repositoryChangePass, repositoryChangePicture, sharedPreferencesHelper);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
