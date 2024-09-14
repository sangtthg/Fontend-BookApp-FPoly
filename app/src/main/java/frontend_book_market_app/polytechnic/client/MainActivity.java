package frontend_book_market_app.polytechnic.client;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import frontend_book_market_app.polytechnic.client.favorite.view.FavoriteFragment;
import frontend_book_market_app.polytechnic.client.home.view.HomeFragment;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.notification.view.NotificationFragment;
import frontend_book_market_app.polytechnic.client.order_user.viewmodel.OrderUserViewModel;
import frontend_book_market_app.polytechnic.client.profile.view.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private SharedPreferencesHelper sharedPreferencesHelper;

    private Fragment homeFragment = new HomeFragment();
    private Fragment favoriteFragment;
    private Fragment notificationFragment;
    private Fragment profileFragment;
    private final FragmentManager fm = getSupportFragmentManager();

    private Fragment activeFragment = homeFragment;

    private HomeViewModel homeViewModel;
    private OrderUserViewModel orderUserViewModel;

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
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        orderUserViewModel = new ViewModelProvider(this).get(OrderUserViewModel.class);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);

        fm.beginTransaction().add(R.id.frameLayout, homeFragment, "1").commit();

        SmoothBottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            if (activeFragment != null) {
                fm.beginTransaction().hide(activeFragment).commit();
            }

            switch (i) {
                case 0:
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                        fm.beginTransaction().add(R.id.frameLayout, homeFragment, "1").commit();
                    }
                    activeFragment = homeFragment;
                    break;
                case 1:
                    if (checkUserLogin()) {
                        if (favoriteFragment == null) {
                            favoriteFragment = new FavoriteFragment();
                            fm.beginTransaction().add(R.id.frameLayout, favoriteFragment, "2").commit();
                        }
                        activeFragment = favoriteFragment;
                    }  else{
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            fm.beginTransaction().add(R.id.frameLayout, homeFragment, "1").commit();
                        }
                        activeFragment = homeFragment;
                        bottomBar.setItemActiveIndex(0);
                    }
                    break;
                case 2:
                    if (checkUserLogin()) {
                        if (notificationFragment == null) {
                            notificationFragment = new NotificationFragment();
                            fm.beginTransaction().add(R.id.frameLayout, notificationFragment, "3").commit();
                        }
                        activeFragment = notificationFragment;
                    }  else{
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            fm.beginTransaction().add(R.id.frameLayout, homeFragment, "1").commit();
                        }
                        activeFragment = homeFragment;
                        bottomBar.setItemActiveIndex(0);
                    }

                    break;
                case 3:
                    if (checkUserLogin()) {
                        if (profileFragment == null) {
                            profileFragment = new ProfileFragment();
                            fm.beginTransaction().add(R.id.frameLayout, profileFragment, "4").commit();
                        }
                        activeFragment = profileFragment;
                    } else {
                        // Nếu chưa đăng nhập, quay về HomeFragment
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            fm.beginTransaction().add(R.id.frameLayout, homeFragment, "1").commit();
                        }
                        activeFragment = homeFragment;
                        bottomBar.setItemActiveIndex(0); // Quay về tab Home
                    }
                    break;
            }

            if (activeFragment != null) {
                fm.beginTransaction().show(activeFragment).commit();
            }

            return true;
        });
    }

    private boolean checkUserLogin() {
        String token = sharedPreferencesHelper.getToken();
        if (token == null || token.isEmpty()) {
            Log.d("HomeFragment", "User is not logged in.");
            promptLogin();
            return false;
        } else {
            Log.d("HomeFragment", "User is logged in.");
            return true;
        }
    }

    private void promptLogin() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_custom_login, null);

        Button btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancel);
        Button btnDialogOk = dialogView.findViewById(R.id.btnDialogOk);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        btnDialogOk.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            dialog.dismiss();
        });

        btnDialogCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}