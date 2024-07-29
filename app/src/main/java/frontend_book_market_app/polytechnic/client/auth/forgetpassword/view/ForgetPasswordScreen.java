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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.R;

public class ForgetPasswordScreen extends AppCompatActivity {
    private EditText edtTextForgetPassword;
    private Button btnXacNhanForgetPassword;
    private ImageButton btnBackLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password_screen);
        edtTextForgetPassword = findViewById(R.id.edtTextForgetPassword);
        btnXacNhanForgetPassword = findViewById(R.id.btnXacNhanForgetPassword);
        btnBackLogin = findViewById(R.id.btnBackLogin);
        validateInputs();
        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
        btnXacNhanForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmailCheckDialog();
            }
        });

    }

    private void showEmailCheckDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_otp_resetpass, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        dialog.show();

        // Đặt background cho dialog sau khi nó được tạo
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background_pass);

        // Use Handler to dismiss dialog after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    // Optional: Perform any additional actions after dialog is dismissed
                    // e.g., navigateToAnotherScreen();
                }
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }

    private boolean validateInputs() {
        String email = edtTextForgetPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            edtTextForgetPassword.setError("Vui lòng nhập email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtTextForgetPassword.setError("Email không hợp lệ");
            return false;
        }


        return true;
    }
}