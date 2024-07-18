package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.login.view.LoginScreen;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.model.OTPModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.viewmodel.RegisterViewModel;

public class OTPScreen extends AppCompatActivity {
    private RegisterViewModel registerViewModel;
    private PinView pinView;
    private Button btnXacNhan;
    private String email, username, password, repassword, otp, otp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);
        pinView = findViewById(R.id.pinview);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
            username = intent.getStringExtra("username");
            password = intent.getStringExtra("password");
            repassword = intent.getStringExtra("re_password");
            otp = intent.getStringExtra("otp");
            otp_id = intent.getStringExtra("otp_id");
            Log.d("OTPScreen", "Email: " + email);
            Log.d("OTPScreen", "Username: " + username);
            Log.d("OTPScreen", "Password: " + password);
            Log.d("OTPScreen", "RePassword: " + repassword);
            Log.d("OTPScreen", "OTP: " + otp);
            Log.d("OTPScreen", "id: " + otp_id);
        }

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        final Observer<String> registerObserver = new Observer<String>() {
            @Override
            public void onChanged(String result) {
                Toast.makeText(OTPScreen.this, result, Toast.LENGTH_SHORT).show();
                if (result.equals("Success")) {
                    Toast.makeText(OTPScreen.this, "Thành công", Toast.LENGTH_SHORT).show();

                }
            }
        };

        registerViewModel.getRegisterResponse().observe(this, registerObserver);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpEntered = pinView.getText().toString().trim();
                if (!otpEntered.isEmpty() && otp != null && otp_id != null) {
                    if (otpEntered.equals(otp)) {
                        try {
                            JSONObject requestBody = new JSONObject();
                            requestBody.put("email", email);
                            requestBody.put("password", password);
                            requestBody.put("re_password", repassword);
                            requestBody.put("username", username);

                            JSONObject verifyObject = new JSONObject();
                            verifyObject.put("otp_id", otp_id);
                            verifyObject.put("otp", otp);

                            requestBody.put("verify", verifyObject);

                            // Tạo một đối tượng OTPModel từ requestBody
                            OTPModel otpModel = new OTPModel();
                            otpModel.setEmail(email);
                            otpModel.setPassword(password);
                            otpModel.setRe_password(repassword);
                            otpModel.setUsername(username);
                            otpModel.setOtp(otp);
                            otpModel.setOtp_id(otp_id);

                            // Gọi phương thức đăng ký từ ViewModel với OTPModel đã tạo
                            registerViewModel.register(otpModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OTPScreen.this, "Error preparing JSON request", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("OTPScreen", "Mã OTP không chính xác");
                        Toast.makeText(OTPScreen.this, "Mã OTP không chính xác", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("OTPScreen", "Vui lòng nhập mã OTP");
                    Toast.makeText(OTPScreen.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}
