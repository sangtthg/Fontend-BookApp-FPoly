package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chaos.view.PinView;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.model.OTPModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.viewmodel.RegisterViewModel;

public class OTPScreen extends AppCompatActivity {
    private RegisterViewModel registerViewModel;
    private PinView pinView;
    private Button btnXacNhan;
    private String email, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);

        pinView = findViewById(R.id.pinview);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        // Get the user data from the previous activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

    }

}