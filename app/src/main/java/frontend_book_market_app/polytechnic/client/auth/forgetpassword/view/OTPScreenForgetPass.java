package frontend_book_market_app.polytechnic.client.auth.forgetpassword.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chaos.view.PinView;
import com.google.android.material.button.MaterialButton;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.model.VerifyOtpRequest;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.viewmodel.ForgetPasswordViewModel;

public class OTPScreenForgetPass extends ComponentActivity {
    private PinView pinViewForgetPass;
    private MaterialButton btnXacNhanForgetPass;
    private ForgetPasswordViewModel viewModel;
    private static final String TAG = "OTPScreenForgetPass";
   private ImageView btnBackForget;
    private String email;  // To store email
    private String otpId;  // To store OTP ID

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_otpscreen_forget_pass);
        btnBackForget =findViewById(R.id.btnBackForget);
        pinViewForgetPass = findViewById(R.id.pinviewForgetPass);
        btnXacNhanForgetPass = findViewById(R.id.btnXacNhanForgetPass);
        viewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);

        btnBackForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPScreenForgetPass.this, ForgetPasswordScreen.class);
                startActivity(intent);
                finish();
            }
        });
        email = getIntent().getStringExtra("email");
        otpId = getIntent().getStringExtra("otp_id");  // Use the correct key

        Log.d(TAG, "Received email: " + email);
        Log.d(TAG, "Received OTP ID: " + otpId);

        // Check if email and OTP ID are not null
        if (email == null || otpId == null) {
            finish(); // Close the activity
            return;
        }

        btnXacNhanForgetPass.setOnClickListener(v -> {
            String enteredOtp = pinViewForgetPass.getText().toString().trim();
            // Check if the entered OTP is not empty
            if (enteredOtp.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập OTP xác nhận", Toast.LENGTH_SHORT).show();
                return;
            }
            // Log the entered OTP
            Log.d(TAG, "Entered OTP: " + enteredOtp);

            if (enteredOtp.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã OTP.", Toast.LENGTH_SHORT).show();
                return;
            }

            VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest(email, enteredOtp, otpId);
            viewModel.verifyOtp(verifyOtpRequest);
        });


        viewModel.getOtpVerifiedSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(OTPScreenForgetPass.this, "OTP chính xác, vui lòng nhập mật khẩu mới.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OTPScreenForgetPass.this, ChangePasswordScreen.class);
                intent.putExtra("otp_id", otpId);
                intent.putExtra("otp", pinViewForgetPass.getText().toString().trim());  // Send OTP
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(OTPScreenForgetPass.this, "OTP không đúng vui lòng nhập lại.", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(OTPScreenForgetPass.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OTPScreenForgetPass.this, ForgetPasswordScreen.class);
        startActivity(intent);
        finish();
    }
}

