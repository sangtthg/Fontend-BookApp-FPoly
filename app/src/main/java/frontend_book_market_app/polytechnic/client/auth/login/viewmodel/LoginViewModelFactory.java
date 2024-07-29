package frontend_book_market_app.polytechnic.client.auth.login.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public LoginViewModelFactory(SharedPreferencesHelper sharedPreferencesHelper) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(sharedPreferencesHelper);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
