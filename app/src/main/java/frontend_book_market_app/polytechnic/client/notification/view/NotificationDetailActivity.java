package frontend_book_market_app.polytechnic.client.notification.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.Objects;

import frontend_book_market_app.polytechnic.client.R;

public class NotificationDetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView messageTextView;
    private TextView dateTextView;
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView backDetailButton;
    private CardView card2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification_detail);
        setupWindowInsets();
        setupStatusBar();
        initializeViews();
        setupBackButton();
        displayNotificationDetails();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupStatusBar() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
    }

    private void initializeViews() {
        titleTextView = findViewById(R.id.notificationDetailTitle);
        messageTextView = findViewById(R.id.notificationDetailMessage);
        dateTextView = findViewById(R.id.notificationDetailDate);
        imageView = findViewById(R.id.notificationDetailImage);
        imageView2 = findViewById(R.id.notificationDetailImage2);
        backDetailButton = findViewById(R.id.backDetailButton);
        card2 = findViewById(R.id.card2);
    }

    private void setupBackButton() {
        backDetailButton.setOnClickListener(v -> finish());
    }

    private void displayNotificationDetails() {
        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");
        String date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String type = getIntent().getStringExtra("type");

        if (Objects.equals(type, "system")) {
            titleTextView.setText("📢  Thông báo hệ thống - " + title);
            imageView.setVisibility(View.GONE);
            if (imageUrl == null){
                card2.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        } else {
            titleTextView.setText("📢  Thông báo đơn hàng - " + title);
            card2.setVisibility(View.GONE);
        }

        messageTextView.setText(message);
        dateTextView.setText("Được gửi lúc: " + date);
        int errorImageResId;

        if (Objects.equals(type, "cancelled")){
            errorImageResId = R.drawable.giao_that_bai_1;
        } else{
            errorImageResId = R.drawable.courier_1;
        }

        loadImage(imageUrl, imageView, errorImageResId);
        loadImage(imageUrl, imageView2, errorImageResId);
    }

    private void loadImage(String url, ImageView imageView,  int imError) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading_book)
                .error(imError)
                .centerCrop()
                .into(imageView);
    }
}