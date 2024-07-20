package sangttph30270.fptpoly.fontend_bookapp_fpoly;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.favorite.view.FavoriteFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view.HomeFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.notification.view.NotificationFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private final Fragment homeFragment = new HomeFragment();
    private final Fragment favoriteFragment = new FavoriteFragment();
    private final Fragment notificationFragment = new NotificationFragment();
    private final Fragment profileFragment = new ProfileFragment();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment activeFragment = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fm.beginTransaction().add(R.id.frameLayout, homeFragment, "1").commit();
        fm.beginTransaction().add(R.id.frameLayout, favoriteFragment, "2").hide(favoriteFragment).commit();
        fm.beginTransaction().add(R.id.frameLayout, notificationFragment, "3").hide(notificationFragment).commit();
        fm.beginTransaction().add(R.id.frameLayout, profileFragment, "4").hide(profileFragment).commit();

        SmoothBottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            fm.beginTransaction().hide(activeFragment).commit();

            switch (i) {
                case 0: // Home
                    activeFragment = homeFragment;
                    break;
                case 1: // Favorite
                    activeFragment = favoriteFragment;
                    break;
                case 2: // Notifications
                    activeFragment = notificationFragment;
                    break;
                case 3: // Profile
                    activeFragment = profileFragment;
                    break;
            }

            fm.beginTransaction().show(activeFragment).commit();
            return true;
        });
    }
}