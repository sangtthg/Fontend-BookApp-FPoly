package frontend_book_market_app.polytechnic.client.auth.login.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
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

import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.login.viewmodel.LoginViewModelFactory;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.MainActivity;
import frontend_book_market_app.polytechnic.client.auth.forgetpassword.view.ForgetPasswordScreen;
import frontend_book_market_app.polytechnic.client.auth.login.viewmodel.LoginViewModel;
import frontend_book_market_app.polytechnic.client.auth.register.view.RegisterScreen;

public class LoginScreen extends AppCompatActivity {
    private EditText editTextPassword, editTextEmail;
    private boolean isPasswordVisible = false;
    private Button btnLogin;
    private TextView tvTaoTaiKhoan;
    private LoginViewModel loginViewModel;
    private TextView txtForgetPassword;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private ImageButton btnBackHomeLogin;
    private ProgressBar progressBar; // Add ProgressBar
    private View darkOverlay;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvTaoTaiKhoan = findViewById(R.id.tvTaoTaiKhoan);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        btnBackHomeLogin = findViewById(R.id.btnBackHomeLogin);
        progressBar = findViewById(R.id.progressBar); // Initialize ProgressBar

         darkOverlay = findViewById(R.id.darkOverlay);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        LoginViewModelFactory factory = new LoginViewModelFactory(sharedPreferencesHelper);
        loginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);


        editTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePasswordVisibility();
            }
        });
        btnBackHomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khởi tạo Intent để mở MainActivity
                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvTaoTaiKhoan.setOnClickListener(view -> {
            showTermsDialog();

        });

        btnLogin.setOnClickListener(v -> {
            if (validateInputs()) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                // Hiển thị ProgressBar và lớp phủ tối
                darkOverlay.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(email, password);
            }
        });

        loginViewModel.getLoginResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                darkOverlay.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (response.equals("Đăng nhập thành công")) {
                    Toast.makeText(LoginScreen.this, response, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginScreen.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        });


        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, ForgetPasswordScreen.class);
                startActivity(intent);

            }
        });
    }

    private boolean validateInputs() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Kiểm tra email không được để trống
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Vui lòng nhập email");
            return false;
        }
        // Kiểm tra email có hợp lệ hay không
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email không hợp lệ");
            return false;
        }

        // Kiểm tra mật khẩu không được để trống
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        // Kiểm tra mật khẩu phải từ 6 ký tự trở lên
        else if (password.length() < 6) {
            editTextPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

        return true;
    }


    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
        } else {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_on_24, 0);
        }
        isPasswordVisible = !isPasswordVisible;
        editTextPassword.setSelection(editTextPassword.length());
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Khởi tạo Intent để mở MainActivity
//        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
//        startActivity(intent);
        finish();
    }

    private void showTermsDialog() {
        // Tạo và cấu hình dialog
        Dialog dialog = new Dialog(LoginScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_terms);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow); // Apply shadow

        // Cấu hình các phần tử trong dialog
        ImageView imageViewTerms1 = dialog.findViewById(R.id.imageViewTerms1);
        ImageView imageViewTerms2 = dialog.findViewById(R.id.imageViewTerms2);
        Button btnAcceptTerms = dialog.findViewById(R.id.btnAcceptTerms);
        Button btnDeclineTerms = dialog.findViewById(R.id.btnDeclineTerms);

        // Thiết lập sự kiện cho các nút
        btnAcceptTerms.setOnClickListener(v -> {
            // Chuyển đến màn hình đăng ký khi chấp nhận điều khoản
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
            dialog.dismiss(); // Đóng dialog
        });

        btnDeclineTerms.setOnClickListener(v -> {
            dialog.dismiss(); // Đóng dialog nếu từ chối
        });

        // Hiển thị dialog
        dialog.show();
    }

}
