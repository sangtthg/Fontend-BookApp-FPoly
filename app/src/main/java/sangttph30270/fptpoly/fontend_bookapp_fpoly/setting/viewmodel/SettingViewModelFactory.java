package sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;

public class SettingViewModelFactory implements ViewModelProvider.Factory {
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public SettingViewModelFactory(SharedPreferencesHelper sharedPreferencesHelper) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SettingViewModel.class)) {
            return (T) new SettingViewModel(sharedPreferencesHelper);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
