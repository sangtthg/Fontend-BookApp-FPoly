package frontend_book_market_app.polytechnic.client.onboardingscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import frontend_book_market_app.polytechnic.client.MainActivity;
import frontend_book_market_app.polytechnic.client.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_FIRST_LAUNCH = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.logo);
        TextView appName = findViewById(R.id.app_name);
        TextView appDescription = findViewById(R.id.app_description);

        // Load animations
        Animation imageAnimation = AnimationUtils.loadAnimation(this, R.anim.image_view_animation);
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_view_animation);
        Animation textAnimation2 = AnimationUtils.loadAnimation(this, R.anim.text2_view_animation);

        // Apply animations
        logo.startAnimation(imageAnimation);
        appName.startAnimation(textAnimation);
        appDescription.startAnimation(textAnimation2);

        // Delay for the duration of the animations, then start MainActivity or NavigationActivity
        int splashScreenDuration = 3000; // 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                boolean isFirstLaunch = prefs.getBoolean(KEY_FIRST_LAUNCH, true);

                if (isFirstLaunch) {
                    // First launch, show NavigationActivity
                    Intent intent = new Intent(SplashScreenActivity.this, NavigationActivity.class);
                    startActivity(intent);
                } else {
                    // Not the first launch, show MainActivity
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                // Update the first launch flag
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(KEY_FIRST_LAUNCH, false);
                editor.apply();

                finish();
            }
        }, splashScreenDuration);
    }
}
