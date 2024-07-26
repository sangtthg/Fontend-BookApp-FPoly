package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;

public class DonHangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.backActivityDonHang).setOnClickListener(v -> finish());
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_medium);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            TextView tabTextView = new TextView(this);
            tabTextView.setTypeface(typeface, Typeface.NORMAL);
            tabTextView.setGravity(Gravity.CENTER);
            tabTextView.setSingleLine(true);
//            int widthInDp = 100;
//            float scale = getResources().getDisplayMetrics().density;
//            int widthInPx = (int) (widthInDp * scale + 0.5f);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthInPx, ViewGroup.LayoutParams.WRAP_CONTENT);
//            tabTextView.setLayoutParams(params);

            switch (position) {
                case 0:
                    tabTextView.setText("Chưa thanh toán");
                    break;
                case 1:
                    tabTextView.setText("Chờ vận chuyển");
                    break;
                case 2:
                    tabTextView.setText("Đã giao hàng");
                    break;
                case 3:
                    tabTextView.setText("Đơn đã huỷ");
                    break;
            }
            tab.setCustomView(tabTextView);
        }).attach();

        tabLayout.getTabAt(0).select();
        TextView firstTabTextView = (TextView) tabLayout.getTabAt(0).getCustomView();
        firstTabTextView.setTextColor(Color.parseColor("#aa0116"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tabTextView = (TextView) tab.getCustomView();
                tabTextView.setTextColor(Color.parseColor("#aa0116"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tabTextView = (TextView) tab.getCustomView();
                tabTextView.setTextColor(Color.BLACK);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
