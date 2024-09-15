package frontend_book_market_app.polytechnic.client.auth.forgetpassword.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ChangePasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.viewmodel.ForgetPasswordViewModel;
import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;

public class ChangePasswordScreen extends AppCompatActivity {

    private ImageButton btnBackChangePassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private Button btnChangePassword;
    private ProgressBar progressBar;

    private ForgetPasswordViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_change_password_screen);

        // Initialize views
        btnBackChangePassword =  findViewById(R.id.btnBackChangePassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        progressBar = findViewById(R.id.progressBar);
        btnBackChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordScreen.this, ForgetPasswordScreen.class);
                startActivity(intent);
                finish();
            }
        });
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);

        // Retrieve OTP and ID from Intent
        Intent intent = getIntent();
        String otpIdString = intent.getStringExtra("otp_id");
        String otp = intent.getStringExtra("otp");

        // Log OTP and OTP ID
        Log.d(TAG, "Received OTP ID: " + otpIdString);
        Log.d(TAG, "Received OTP: " + otp);


        btnChangePassword.setOnClickListener(v -> {
            String newPassword = editTextNewPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();
            // Kiểm tra độ dài của mật khẩu
            if (newPassword.length() < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPassword.equals(confirmPassword)) {
                if (otpIdString != null && otp != null) {
                    try {
                        int otpId = Integer.parseInt(otpIdString);
                        // Call changePasswordAfterOtp with OTP ID (as int) and OTP
                        viewModel.changePasswordAfterOtp(new ChangePasswordRequest(otpId, otp, newPassword, confirmPassword));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "NumberFormatException: ", e);
                    }
                } else {
                    Toast.makeText(this, "Không có OTP hoặc IDOTP", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe changes to password change success
        viewModel.getPasswordChangedSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Thay đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ChangePasswordScreen.this, LoginScreen.class);
                startActivity(intent2);
                finish();            } else {
                Toast.makeText(this, "Thay đổi mật khẩu thất bại.", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe error messages
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChangePasswordScreen.this, ForgetPasswordScreen.class);
        startActivity(intent);
        finish();
    }

}
