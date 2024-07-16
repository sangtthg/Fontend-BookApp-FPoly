package sangttph30270.fptpoly.fontend_bookapp_fpoly;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.favorite.view.FavoriteFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.notification.view.NotificationFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view.HomeFragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Khởi tạo SmoothBottomBar
        SmoothBottomBar bottomBar = findViewById(R.id.bottomBar);

        // Load HomeFragment as default
        loadFragment(new HomeFragment());

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                // Xử lý sự kiện khi một item được chọn
                switch (i) {
                    case 0: // navHome
                        loadFragment(new HomeFragment());
                        break;
                    case 1: // navSearch
                        loadFragment(new FavoriteFragment());
                        break;
                    case 2: // navNotification
                        loadFragment(new NotificationFragment());
                        break;
                    case 3: // navProfile
                        loadFragment(new ProfileFragment());
                        break;
                }
                return true;
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
