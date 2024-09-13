package frontend_book_market_app.polytechnic.client.auth.forgetpassword.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private String storedOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen_forget_pass);

        pinViewForgetPass = findViewById(R.id.pinviewForgetPass);
        btnXacNhanForgetPass = findViewById(R.id.btnXacNhanForgetPass);

        viewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);

        // Observe stored OTP
        viewModel.getStoredOtp().observe(this, storedOtp -> {
            this.storedOtp = storedOtp;
            if (storedOtp != null) {
                Log.d(TAG, "Stored OTP: " + storedOtp);
            } else {
                Log.d(TAG, "No Stored OTP");
            }
        });

        btnXacNhanForgetPass.setOnClickListener(v -> {
            String enteredOtp = pinViewForgetPass.getText().toString();
            Log.d(TAG, "Entered OTP: " + enteredOtp);
            Log.d(TAG, "Stored OTP: " + storedOtp);

            if (enteredOtp.isEmpty()) {
                Toast.makeText(OTPScreenForgetPass.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            if (storedOtp == null) {
                Toast.makeText(OTPScreenForgetPass.this, "OTP data not available. Please request a new OTP.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (enteredOtp.equals(storedOtp)) {
                Intent intent = new Intent(OTPScreenForgetPass.this, ChangePasswordScreen.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(OTPScreenForgetPass.this, "Incorrect OTP. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

