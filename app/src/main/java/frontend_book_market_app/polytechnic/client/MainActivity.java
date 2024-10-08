package frontend_book_market_app.polytechnic.client;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;


import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.core.MessagingService;
import frontend_book_market_app.polytechnic.client.don_hang.view.DonHangFragment;
import frontend_book_market_app.polytechnic.client.home.view.HomeFragment;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.notification.view.NotificationFragment;
import frontend_book_market_app.polytechnic.client.don_hang.viewmodel.DonHangUserViewModel;
import frontend_book_market_app.polytechnic.client.profile.view.ProfileFragment;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;
    private final FragmentManager fm = getSupportFragmentManager();

    MessagingService messagingService = new MessagingService();

    private final ActivityResultLauncher<String> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    messagingService.getDeviceToken();
                } else {
                    Toast.makeText(this, "Quyền thông báo bị từ chối", Toast.LENGTH_SHORT).show();
                }
            }
    );
    private SharedPreferencesHelper sharedPreferencesHelper;
    private Fragment homeFragment = new HomeFragment();
    private Fragment favoriteFragment;
    private Fragment notificationFragment;
    private Fragment profileFragment;
    private Fragment activeFragment = homeFragment;
    private HomeViewModel homeViewModel;
    private DonHangUserViewModel donHangUserViewModel;

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
        donHangUserViewModel = new ViewModelProvider(this).get(DonHangUserViewModel.class);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.v("FirebaseLogs", "Device ID: " + android_id);



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
                            favoriteFragment = new DonHangFragment();
                            fm.beginTransaction().add(R.id.frameLayout, favoriteFragment, "2").commit();
                        }
                        activeFragment = favoriteFragment;
                    } else {
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
                    } else {
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

        requestNotificationPermission();
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
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Bạn Chưa Đăng Nhập")
                .setMessage("Bạn chưa đăng nhập tài khoản, bạn có muốn Đăng nhập để sử dụng tính năng này không?")
                .setCancelable(false)
                .setPositiveButton("Đăng nhập", R.drawable.ic_check_24_default, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Chuyển đến màn hình đăng nhập
                        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Hủy", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        mDialog.show();

    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                messagingService.getDeviceToken();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Toast.makeText(this, "Bạn cần cấp quyền thông báo để BookMarket App hoạt động bình thường.", Toast.LENGTH_LONG).show();
                resultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            } else {
                resultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            if (notificationManagerCompat.areNotificationsEnabled()) {
                messagingService.getDeviceToken();
            } else {
                Toast.makeText(this, "Bạn cần bật thông báo trong cài đặt để BookMarket App hoạt động bình thường.", Toast.LENGTH_LONG).show();
            }
        }
    }


}