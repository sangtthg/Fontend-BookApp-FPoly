package frontend_book_market_app.polytechnic.client.auth.forgetpassword.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ForgotPasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.viewmodel.ForgetPasswordViewModel;
import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.register.model.OTPModel;
import frontend_book_market_app.polytechnic.client.auth.register.view.RegisterScreen;

public class ForgetPasswordScreen extends AppCompatActivity {
    private EditText edtTextForgetPassword;
    private Button btnXacNhanForgetPassword;
    private ImageButton btnBackLogin;
    private ForgetPasswordViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password_screen);

        edtTextForgetPassword = findViewById(R.id.edtTextForgetPassword);
        btnXacNhanForgetPassword = findViewById(R.id.btnXacNhanForgetPassword);
        btnBackLogin = findViewById(R.id.btnBackLogin);

        btnBackLogin.setOnClickListener(view -> {
            Intent intent = new Intent(ForgetPasswordScreen.this, LoginScreen.class);
            startActivity(intent);
            finish();
        });
        viewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);

        viewModel.getOtpSentSuccess().observe(this, otpSentSuccess -> {
            if (otpSentSuccess) {
                // Retrieve OTP ID, email, and OTP
                String email = viewModel.getEmailLiveData().getValue();
                String otpId = viewModel.getOtpIdLiveData().getValue();
                String otp = viewModel.getOtpLiveData().getValue();

                // Log the values
                Log.d("YourActivity", "Email: " + email);
                Log.d("YourActivity", "OTP ID: " + otpId);
                Log.d("YourActivity", "OTP: " + otp);
                Intent intent = new Intent(this, OTPScreenForgetPass.class);
                intent.putExtra("email", email);
                intent.putExtra("otp_id", otpId);  // Ensure the key matches
                startActivity(intent);

            } else {
                // OTP send failed, handle failure
                Toast.makeText(this, "Lỗi khi gửi OTP", Toast.LENGTH_SHORT).show();
            }
        });


        // Quan sát email check
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMsg) {
                Toast.makeText(ForgetPasswordScreen.this, errorMsg, Toast.LENGTH_SHORT).show();
                if (errorMsg.equals("")) {
                    showEmailCheckDialog();
                    Log.d("RegisterScreen", "Email đã tồn tại.");
                } else if (errorMsg.equals("Email chưa có trong hệ thống")) {
                    Log.d("RegisterScreen", "Email chưa có trong hệ thống");
                }
            }
        });
        // Handle button click
        btnXacNhanForgetPassword.setOnClickListener(view -> {
            String email = edtTextForgetPassword.getText().toString().trim();

            if (validateEmail(email)) {
                viewModel.checkEmail(email); // Check if email exists before sending OTP
            } else {
                edtTextForgetPassword.setError("Vui lòng nhập Email đúng định dạng.");
            }
        });
    }

    private void showEmailCheckDialog() {
        // Inflate the dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_otp_resetpass, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        dialog.show();

        // Set background for dialog
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background_pass);
        new Handler().postDelayed(() -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
                navigateToOtpScreen();
            }
        }, 5000);
    }


    private boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void navigateToOtpScreen() {
        Intent intent = new Intent(ForgetPasswordScreen.this, OTPScreenForgetPass.class);
        startActivity(intent);
        finish(); // Optionally finish this activity to prevent returning to it
    }
}
