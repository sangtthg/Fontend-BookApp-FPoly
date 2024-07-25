package sangttph30270.fptpoly.fontend_bookapp_fpoly.onboardingscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.MainActivity;

public class LauncherActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_FIRST_LAUNCH = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean(KEY_FIRST_LAUNCH, true);

        Intent intent;
        if (isFirstLaunch) {
            // First launch, show NavigationActivity
            intent = new Intent(this, NavigationActivity.class);
        } else {
            // Not the first launch, show SplashScreenActivity
            intent = new Intent(this, SplashScreenActivity.class);
        }

        // Update the first launch flag
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_FIRST_LAUNCH, false);
        editor.apply();

        startActivity(intent);
        finish();
    }
}
