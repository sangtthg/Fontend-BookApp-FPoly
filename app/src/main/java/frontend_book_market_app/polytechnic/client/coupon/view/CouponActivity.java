package frontend_book_market_app.polytechnic.client.coupon.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.coupon.adapter.AdapterCoupon;
import frontend_book_market_app.polytechnic.client.coupon.adapter.AdapterCouponHetHan;
import frontend_book_market_app.polytechnic.client.coupon.viewmodel.CouponViewModel;
import frontend_book_market_app.polytechnic.client.home.view.PaymentActivity;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class CouponActivity extends AppCompatActivity {
    private ImageView btnBackThanhToan;
    private RecyclerView recyclerviewCoupon,recyclerviewExpiredCoupon;
    private AdapterCoupon adapterCoupon;
    private AdapterCouponHetHan adapterCouponHetHan;
    private Button btnDongYCoupon;
    private CouponViewModel couponViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerviewExpiredCoupon = findViewById(R.id.recyclerviewExpiredCoupon);
        btnDongYCoupon = findViewById(R.id.btnDongYCoupon);
        btnBackThanhToan = findViewById(R.id.btnBackThanhToan);
        recyclerviewCoupon = findViewById(R.id.recyclerviewCoupon);
        couponViewModel = new ViewModelProvider(this).get(CouponViewModel.class);

        btnBackThanhToan.setOnClickListener(v -> {
            // Navigate back to PaymentActivity
            Intent intent = new Intent(CouponActivity.this, PaymentActivity.class);
            startActivity(intent);
        });


        btnDongYCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if a coupon is selected
                if (adapterCoupon.getSelectedCouponId() != -1) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("couponId", adapterCoupon.getSelectedCouponId());
                    resultIntent.putExtra("couponCode", adapterCoupon.getSelectedCouponCode());
                    resultIntent.putExtra("couponDiscount", adapterCoupon.getSelectedCouponDiscount());
                    setResult(RESULT_OK, resultIntent);

                    // Show success message
                    Toast.makeText(CouponActivity.this, "Áp mã giảm giá thành công", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(CouponActivity.this, "Mã giảm giá không tồn tài", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        setupRecyclerViewHetHan();
        setupRecyclerView();
        observeViewModel();
        couponViewModel.fetchCouponList();
    }

    private void setupRecyclerView() {
        recyclerviewCoupon.setLayoutManager(new LinearLayoutManager(this));
        adapterCoupon = new AdapterCoupon(this, new ArrayList<>()); // Initialize with an empty list
        recyclerviewCoupon.setAdapter(adapterCoupon);
    }


//    private void setupRecyclerViewHetHan() {
//        recyclerviewExpiredCoupon.setLayoutManager(new LinearLayoutManager(this));
//        adapterCouponHetHan = new AdapterCouponHetHan(this, new ArrayList<>()); // Initialize with an empty list
//        recyclerviewExpiredCoupon.setAdapter(adapterCouponHetHan);
//    }

    private void observeViewModel() {
        couponViewModel.getCouponList().observe(this, couponList -> {
            if (couponList != null) {
                // Update the adapter with the new coupon list
                adapterCoupon.setCouponList(couponList);
//                adapterCouponHetHan.setCouponList(couponList);
                // Restore selected coupon state
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                int savedCouponId = sharedPreferences.getInt("selectedCouponId", -1);
                if (savedCouponId != -1) {
                    adapterCoupon.setSelectedCouponId(savedCouponId);
//                    adapterCouponHetHan.setSelectedCouponId(savedCouponId);
                }
            }
        });
    }
}
