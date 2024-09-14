package frontend_book_market_app.polytechnic.client.auth.register.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import frontend_book_market_app.polytechnic.client.MainActivity;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.auth.register.model.OTPModel;
import frontend_book_market_app.polytechnic.client.auth.register.viewmodel.RegisterViewModel;


public class RegisterScreen extends AppCompatActivity {
    public EditText editTextEmailRegister, editTextUsernameRegister, editTextPasswordRegister, editTextRePasswordRegister;
    public Button btnRegister;
    private boolean isPasswordVisible = false;
    private RegisterViewModel registerViewModel;
    private TextView tvDangNhap;
    private ImageButton btnBackLoginRegister;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_registerscreen);
        editTextEmailRegister = findViewById(R.id.editTextEmailRegister);
        editTextUsernameRegister = findViewById(R.id.editTextUsernameRegister);
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        editTextRePasswordRegister = findViewById(R.id.editTextRePasswordRegister);
        btnBackLoginRegister = findViewById(R.id.btnBackLoginRegister);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        btnRegister = findViewById(R.id.btnRegister);
        editTextPasswordRegister.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editTextPasswordRegister.getRight() - editTextPasswordRegister.getCompoundDrawables()[2].getBounds().width())) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
        });

        editTextRePasswordRegister.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editTextRePasswordRegister.getRight() - editTextRePasswordRegister.getCompoundDrawables()[2].getBounds().width())) {
                    togglePasswordVisibility2();
                    return true;
                }
            }
            return false;
        });

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Quan sát email check
        registerViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMsg) {
                Toast.makeText(RegisterScreen.this, errorMsg, Toast.LENGTH_SHORT).show();
                if (errorMsg.equals("Email đã tồn tại")) {

                    Log.d("RegisterScreen", "Email đã tồn tại.");
                } else if (errorMsg.equals("Chuẩn bị đăng ký")) {
                    // Tiến hành gửi OTP
                    String email = editTextEmailRegister.getText().toString().trim();
                    String username = editTextUsernameRegister.getText().toString().trim();
                    String password = editTextPasswordRegister.getText().toString().trim();
                    String repassword = editTextRePasswordRegister.getText().toString().trim();
                    String address = "Trống"; // Giá trị mặc định cho address
                    OTPModel otpModel = new OTPModel(email, password, repassword, username, address, null, null);
                    registerViewModel.setOtpModel(otpModel);
                    registerViewModel.postOTPAPI(otpModel);
                }
            }
        });
        registerViewModel.getPostOTPResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                Toast.makeText(RegisterScreen.this, response, Toast.LENGTH_SHORT).show();
                if (response.equals("OTP đã được gửi thành công!")) {
                    showOtpDialog();
                }
            }
        });
        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    String email = editTextEmailRegister.getText().toString().trim();
                    registerViewModel.checkEmail(email); // Kiểm tra email trước khi gửi OTP
                }
            }
        });

        btnBackLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showOtpDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_otp_sent, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        Button btnOk = dialogView.findViewById(R.id.btnDialogOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss dialog immediately when button is clicked
                dialog.dismiss();
                navigateToOtpScreen();
            }
        });

        // Show the dialog
        dialog.show();

        // Use Handler to automatically dismiss dialog after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    navigateToOtpScreen();
                }
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }

    private void navigateToOtpScreen() {
        Intent intent = new Intent(RegisterScreen.this, OTPScreen.class);

        intent.putExtra("email", editTextEmailRegister.getText().toString().trim());
        intent.putExtra("username", editTextUsernameRegister.getText().toString().trim());
        intent.putExtra("password", editTextPasswordRegister.getText().toString().trim());
        intent.putExtra("re_password", editTextRePasswordRegister.getText().toString().trim());
        intent.putExtra("email", editTextEmailRegister.getText().toString().trim());
        intent.putExtra("address", registerViewModel.getOtpModel().getAddress()); // Truyền address
        intent.putExtra("otp", registerViewModel.getOtpModel().getVerify().getOtp()); // Truyền OTP ID
        intent.putExtra("otp_id", registerViewModel.getIdotpLiveData().getVerify().getOtp_id()); // Truyền OTP ID
        startActivity(intent);
    }


    private boolean validateInputs() {
        String email = editTextEmailRegister.getText().toString().trim();
        String username = editTextUsernameRegister.getText().toString().trim();
        String password = editTextPasswordRegister.getText().toString().trim();
        String rePassword = editTextRePasswordRegister.getText().toString().trim();

        // Kiểm tra email
        if (TextUtils.isEmpty(email)) {
            editTextEmailRegister.setError("Vui lòng nhập email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmailRegister.setError("Email không hợp lệ");
            return false;
        }

        // Kiểm tra tên người dùng
        if (TextUtils.isEmpty(username)) {
            editTextUsernameRegister.setError("Vui lòng nhập tên người dùng");
            return false;
        } else if (username.length() < 10) {
            editTextUsernameRegister.setError("Tên người dùng phải có ít nhất 10 ký tự");
            return false;
        }


        // Kiểm tra mật khẩu
        if (TextUtils.isEmpty(password)) {
            editTextPasswordRegister.setError("Vui lòng nhập mật khẩu");
            return false;
        } else if (password.length() < 6) {
            editTextPasswordRegister.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

        // Kiểm tra nhập lại mật khẩu
        if (TextUtils.isEmpty(rePassword)) {
            editTextRePasswordRegister.setError("Vui lòng nhập lại mật khẩu");
            return false;
        } else if (!password.equals(rePassword)) {
            editTextRePasswordRegister.setError("Mật khẩu nhập lại không khớp");
            return false;
        }

        return true;
    }


    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            editTextPasswordRegister.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editTextPasswordRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
        } else {
            editTextPasswordRegister.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editTextPasswordRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_on_24, 0);
        }
        isPasswordVisible = !isPasswordVisible;
        editTextPasswordRegister.setSelection(editTextPasswordRegister.length());
    }

    private void togglePasswordVisibility2() {
        if (isPasswordVisible) {
            editTextRePasswordRegister.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editTextRePasswordRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
        } else {
            editTextRePasswordRegister.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editTextRePasswordRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_on_24, 0);
        }
        isPasswordVisible = !isPasswordVisible;
        editTextRePasswordRegister.setSelection(editTextRePasswordRegister.length());
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Khởi tạo Intent để mở MainActivity
        Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

    private void showTermsDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_terms, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        Button btnAccept = dialogView.findViewById(R.id.btnAccept);
        Button btnDecline = dialogView.findViewById(R.id.btnDecline);
        TextView tvTerms = dialogView.findViewById(R.id.tvTerms);

        // Set terms text (optional)
        tvTerms.setText("Please read and accept the terms and conditions before registering.");

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (validateInputs()) {
                    String email = editTextEmailRegister.getText().toString().trim();
                    registerViewModel.checkEmail(email); // Kiểm tra email trước khi gửi OTP
                }
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(RegisterScreen.this, "You must accept the terms to proceed.", Toast.LENGTH_SHORT).show();
            }
        });

        // Show the dialog
        dialog.show();
    }

}