package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.model.OTPModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.network.ApiServiceRegister;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.network.RepositoryRegister;

public class RegisterScreen extends AppCompatActivity {
    public EditText editTextEmailRegister, editTextUsernameRegister, editTextPasswordRegister, editTextRePasswordRegister;
    public Button btnRegister;
    private boolean isPasswordVisible = false;
    private ApiServiceRegister apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerscreen);
        editTextEmailRegister = findViewById(R.id.editTextEmailRegister);
        editTextUsernameRegister = findViewById(R.id.editTextUsernameRegister);
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        editTextRePasswordRegister = findViewById(R.id.editTextRePasswordRegister);
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


        RepositoryRegister repositoryRegister = new RepositoryRegister();
        apiService = repositoryRegister.getApiService();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    String email = editTextEmailRegister.getText().toString().trim();
                    sendOtp(email);
                }
            }
        });
    }


    private void sendOtp(String email) {
        OTPModel otpRequest = new OTPModel(email);
        Call<ResponseBody> call = apiService.sendOtp(otpRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    showOtpDialog();
                } else {
                    String errorBodyString = null;
                    try {
                        errorBodyString = response.errorBody().string();
                        Log.e("RegisterScreen", "Gửi OTP thất bại: " + response.code() + ", Error Body: " + errorBodyString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterScreen.this, "Gửi OTP thất bại: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterScreen.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterScreen", "Lỗi: " + t.getMessage());
            }
        });
    }

    private void showOtpDialog() {
        // Inflate custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_otp_sent, null);

        // Create AlertDialog with custom view
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        // Set the dialog's background to transparent
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // Set up the OK button in the custom layout
        Button btnOk = dialogView.findViewById(R.id.btnDialogOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(RegisterScreen.this, OTPScreen.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }



    private boolean validateInputs() {
        String email = editTextEmailRegister.getText().toString().trim();
        String username = editTextUsernameRegister.getText().toString().trim();
        String password = editTextPasswordRegister.getText().toString().trim();
        String rePassword = editTextRePasswordRegister.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextEmailRegister.setError("Vui lòng nhập email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmailRegister.setError("Email không hợp lệ");
            return false;
        }

        if (TextUtils.isEmpty(username)) {
            editTextUsernameRegister.setError("Vui lòng nhập tên người dùng");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPasswordRegister.setError("Vui lòng nhập mật khẩu");
            return false;
        } else if (password.length() < 6) {
            editTextPasswordRegister.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

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
}
