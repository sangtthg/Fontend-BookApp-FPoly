package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.profile.network.RepositoryAddress;

public class AddressViewModelFactory implements ViewModelProvider.Factory {
    private final SharedPreferences sharedPreferences;
    private final RepositoryAddress repositoryAddress;

    public AddressViewModelFactory(SharedPreferences sharedPreferences, RepositoryAddress repositoryAddress) {
        this.sharedPreferences = sharedPreferences;
        this.repositoryAddress = repositoryAddress;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddressViewModel.class)) {
            return (T) new AddressViewModel(sharedPreferences, repositoryAddress);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
