package sangttph30270.fptpoly.fontend_bookapp_fpoly;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.ApiInterface;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;

public class loginscreen extends AppCompatActivity {
    public EditText editTextPassword, editTextEmail;
    private boolean isPasswordVisible = false;
    private Button btnLogin;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://book-manager-phi.vercel.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Khởi tạo Interface API
        apiInterface = retrofit.create(ApiInterface.class);

        btnLogin = findViewById(R.id.btnLogin);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        editTextPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[2].getBounds().width())) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(loginscreen.this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                Call<ResponseBody> call = apiInterface.login(email, password);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String responseBody = response.body().string();
                                Log.d("LoginScreen", "Phản hồi từ API: " + responseBody);
                                if (responseBody.contains("success")) {
                                    Toast.makeText(loginscreen.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(loginscreen.this, MainActivity.class);
                                    startActivity(intent);
                                    finish(); // Đóng màn hình đăng nhập để người dùng không thể quay lại bằng nút Back
                                } else {
                                    Toast.makeText(loginscreen.this, "Đăng nhập thất bại: Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String errorBodyString = null;
                            try {
                                errorBodyString = response.errorBody().string();
                                Log.e("LoginScreen", "Đăng nhập thất bại: " + response.code() + ", Error Body: " + errorBodyString);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(loginscreen.this, "Đăng nhập thất bại: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Xử lý lỗi từ Retrofit
                        Toast.makeText(loginscreen.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("LoginScreen", "Lỗi: " + t.getMessage());
                    }
                });
            }
        });



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
