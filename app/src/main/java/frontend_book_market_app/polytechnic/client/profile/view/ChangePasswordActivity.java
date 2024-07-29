package frontend_book_market_app.polytechnic.client.profile.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import frontend_book_market_app.polytechnic.client.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    private Button btnChangePassword;
    private ImageView imgBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        imgBack = findViewById(R.id.imgBack);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        imgBack.setOnClickListener(view -> onBackPressed());

        setUpPasswordVisibilityToggle(edtOldPassword);
        setUpPasswordVisibilityToggle(edtNewPassword);
        setUpPasswordVisibilityToggle(edtConfirmPassword);
    }

    private void setUpPasswordVisibilityToggle(final EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width())) {
                    // Toggling visibility
                    if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        // Show password
                        editText.setTransformationMethod(null);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_on_24, 0);
                    } else {
                        // Hide password
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                    }
                    // Move cursor to the end of the text
                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
            return false;
        });
    }
}