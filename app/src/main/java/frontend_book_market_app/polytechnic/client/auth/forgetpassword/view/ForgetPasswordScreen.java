package frontend_book_market_app.polytechnic.client.auth.forgetpassword.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.ForgotPasswordRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.viewmodel.ForgetPasswordViewModel;
import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.R;

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
                // OTP sent successfully, show dialog
                showEmailCheckDialog();
            } else {
                // OTP send failed, handle failure
                Toast.makeText(this, "Lỗi khi gửi OTP", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            // Handle error message
            Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
        });

        // Handle button click
        btnXacNhanForgetPassword.setOnClickListener(view -> {
            String email = edtTextForgetPassword.getText().toString().trim();
            if (validateEmail(email)) {
                ForgotPasswordRequest request = new ForgotPasswordRequest(email);
                viewModel.sendForgotPasswordOtp(request);
            } else {
                edtTextForgetPassword.setError("Invalid email");
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
        }, 3000);
    }


    private boolean validateEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void navigateToOtpScreen() {
        Intent intent = new Intent(ForgetPasswordScreen.this, OTPScreenForgetPass.class);
        startActivity(intent);
        finish(); // Optionally finish this activity to prevent returning to it
    }
}
