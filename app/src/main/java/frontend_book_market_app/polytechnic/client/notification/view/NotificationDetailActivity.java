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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import frontend_book_market_app.polytechnic.client.R;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification_detail);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            View decor = getWindow().getDecorView();
//            decor.setSystemUiVisibility(0);
//        }
        TextView titleTextView = findViewById(R.id.notificationDetailTitle);
        TextView messageTextView = findViewById(R.id.notificationDetailMessage);
        TextView dateTextView = findViewById(R.id.notificationDetailDate);
        ImageView imageView = findViewById(R.id.notificationDetailImage);
        ImageView backDetailButton = findViewById(R.id.backDetailButton);

        backDetailButton.setOnClickListener(v -> finish());



        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");
        String date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        titleTextView.setText(title);
        messageTextView.setText(message);
        dateTextView.setText(date);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.loading_book)
                .error(R.drawable.ic_error_photo)
                .centerCrop()
                .into(imageView);
    }
}