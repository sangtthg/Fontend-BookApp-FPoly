package frontend_book_market_app.polytechnic.client.profile.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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
import frontend_book_market_app.polytechnic.client.profile.viewmodel.ProfileViewModelFactory;

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
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        RepositoryChangePass repositoryChangePass = new RepositoryChangePass();

        profileViewModel = new ViewModelProvider(
                this,
                new ProfileViewModelFactory(repositoryChangePass, sharedPreferences)
        ).get(ProfileViewModel.class);
        imgBack = findViewById(R.id.imgBack);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        imgBack.setOnClickListener(view -> onBackPressed());
        btnChangePassword.setOnClickListener(v -> handleChangePassword());
        setUpPasswordVisibilityToggle(edtOldPassword);
        setUpPasswordVisibilityToggle(edtNewPassword);
        setUpPasswordVisibilityToggle(edtConfirmPassword);
    }

    private void setUpPasswordVisibilityToggle(final EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width())) {
                    // Toggle password visibility
                    if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        // Show password
                        editText.setTransformationMethod(null);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_on_24, 0);
                    } else {
                        // Hide password
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                    }
                    // Move cursor to the end of the text
                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
            return false;
        });
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
        if (model.getPassword().isEmpty() || model.getNew_password().isEmpty() || model.getRe_password().isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!model.getNew_password().equals(model.getRe_password())) {
            Toast.makeText(this, "Mật khẩu mới và mật khẩu xác nhận không khớp.", Toast.LENGTH_SHORT).show();
            return false;
        }
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
                Toast.makeText(this, "Lỗi: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
