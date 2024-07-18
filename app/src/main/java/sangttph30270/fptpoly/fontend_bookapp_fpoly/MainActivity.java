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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.favorite.view.FavoriteFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.notification.view.NotificationFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view.HomeFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view.ProfileFragment;


import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.favorite.view.FavoriteFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.notification.view.NotificationFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view.HomeFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private FavoriteFragment favoriteFragment;
    private NotificationFragment notificationFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.BLACK);
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SmoothBottomBar bottomBar = findViewById(R.id.bottomBar);

        homeFragment = new HomeFragment();
        favoriteFragment = new FavoriteFragment();
        notificationFragment = new NotificationFragment();
        profileFragment = new ProfileFragment();


        loadFragment(homeFragment);

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0: // Trang chủ
                        loadFragment(homeFragment);
                        break;
                    case 1: // Yêu thích
                        loadFragment(favoriteFragment);
                        break;
                    case 2: // Thông báo
                        loadFragment(notificationFragment);
                        break;
                    case 3: // Profile
                        loadFragment(profileFragment);
                        break;
                }
                return true;
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        }


        if (fragment != homeFragment) {
            fragmentTransaction.hide(homeFragment);
        }
        if (fragment != favoriteFragment) {
            fragmentTransaction.hide(favoriteFragment);
        }
        if (fragment != notificationFragment) {
            fragmentTransaction.hide(notificationFragment);
        }
        if (fragment != profileFragment) {
            fragmentTransaction.hide(profileFragment);
        }

        fragmentTransaction.commit();
    }
}
