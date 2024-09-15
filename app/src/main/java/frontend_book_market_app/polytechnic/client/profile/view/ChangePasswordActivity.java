package frontend_book_market_app.polytechnic.client.profile.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.profile.model.ChangePasswordModel;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.ProfileViewModel;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePass;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePicture;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.ProfileViewModelFactory;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    private Button btnChangePassword;
    private ImageView imgBack;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(this);
        RepositoryChangePass repositoryChangePass = new RepositoryChangePass();
        RepositoryChangePicture repositoryChangePicture = new RepositoryChangePicture(); // Initialize this
        ProfileViewModelFactory factory = new ProfileViewModelFactory(
                repositoryChangePass, repositoryChangePicture, sharedPreferencesHelper
        );
        profileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        imgBack = findViewById(R.id.imgBack);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        imgBack.setOnClickListener(view -> onBackPressed());
        btnChangePassword.setOnClickListener(v -> handleChangePassword());
    }

    private void handleChangePassword() {
        String oldPassword = edtOldPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        ChangePasswordModel model = new ChangePasswordModel(oldPassword, newPassword, confirmPassword);

        if (validatePasswords(model)) {
            profileViewModel.changePassword(oldPassword, newPassword);
            observePasswordChange();
        }
    }

    private boolean validatePasswords(ChangePasswordModel model) {
        // Clear previous errors first, but only if the EditText has valid input
        if (!edtOldPassword.getText().toString().isEmpty()) {
            edtOldPassword.setError(null);
        }
        if (!edtNewPassword.getText().toString().isEmpty()) {
            edtNewPassword.setError(null);
        }
        if (!edtConfirmPassword.getText().toString().isEmpty()) {
            edtConfirmPassword.setError(null);
        }

        // Check if any of the fields are empty and apply errors only if empty
        if (model.getPassword().isEmpty()) {
            edtOldPassword.setError("Vui lòng điền mật khẩu cũ.");
            return false;
        } else if (model.getNew_password().isEmpty()) {
            edtNewPassword.setError("Vui lòng điền mật khẩu mới.");
            return false;
        } else if (model.getRe_password().isEmpty()) {
            edtConfirmPassword.setError("Vui lòng xác nhận mật khẩu mới.");
            return false;
        }
        // Check if the new password has fewer than 6 characters
        else if (model.getNew_password().length() < 6) {
            edtNewPassword.setError("Mật khẩu mới phải có ít nhất 6 ký tự.");
            return false;
        }
        // Check if the new password and confirm password match
        else if (!model.getNew_password().equals(model.getRe_password())) {
            edtConfirmPassword.setError("Mật khẩu mới và mật khẩu xác nhận không khớp.");
            return false;
        }

        // If everything is valid, return true
        return true;
    }



    private void observePasswordChange() {
        profileViewModel.getPasswordChangeSuccess().observe(this, success -> {
            if (success != null) {
                if (success) {
                    Toast.makeText(this, "Đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
                    finish(); // Close Activity on success
                } else {
                    Toast.makeText(this, "Đổi mật khẩu không thành công. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profileViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, "Mật khẩu cũ không chính xác" , Toast.LENGTH_LONG).show();
            }
        });
    }
}
