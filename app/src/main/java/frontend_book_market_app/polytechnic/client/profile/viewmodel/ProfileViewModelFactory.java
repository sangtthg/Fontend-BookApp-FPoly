package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePass;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private final RepositoryChangePass repositoryChangePass;
    private final SharedPreferences sharedPreferences;

    public ProfileViewModelFactory(RepositoryChangePass repositoryChangePass, SharedPreferences sharedPreferences) {
        this.repositoryChangePass = repositoryChangePass;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(sharedPreferences, repositoryChangePass);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
