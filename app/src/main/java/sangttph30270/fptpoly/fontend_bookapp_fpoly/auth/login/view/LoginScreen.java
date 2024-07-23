package sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.viewmodel.LoginViewModelFactory;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.MainActivity;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.forgetpassword.view.ForgetPasswordScreen;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.viewmodel.LoginViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.network.RepositoryLogin;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.register.view.RegisterScreen;

public class LoginScreen extends AppCompatActivity {
    private EditText editTextPassword, editTextEmail;
    private boolean isPasswordVisible = false;
    private Button btnLogin;
    private TextView tvTaoTaiKhoan;
    private LoginViewModel loginViewModel;
    private TextView txtForgetPassword;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private ImageButton btnBackHomeLogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvTaoTaiKhoan = findViewById(R.id.tvTaoTaiKhoan);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        btnBackHomeLogin = findViewById(R.id.btnBackHomeLogin);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        LoginViewModelFactory factory = new LoginViewModelFactory(sharedPreferencesHelper);
        loginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
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
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
            finish(); // Kết thúc màn hình đăng nhập
        });

        btnLogin.setOnClickListener(v -> {
            if (validateInputs()) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                loginViewModel.login(email, password);
            }
        });

        loginViewModel.getLoginResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                if (response.equals("Đăng nhập thành công")) {
                    Toast.makeText(LoginScreen.this, response, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Đóng màn hình đăng nhập
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

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Vui lòng nhập email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email không hợp lệ");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Vui lòng nhập mật khẩu");
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
}
