package frontend_book_market_app.polytechnic.client.coupon.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.coupon.adapter.AdapterCoupon;
import frontend_book_market_app.polytechnic.client.coupon.adapter.AdapterCouponHetHan;
import frontend_book_market_app.polytechnic.client.coupon.viewmodel.CouponViewModel;

public class CouponActivity extends AppCompatActivity {
    private ImageView btnBackThanhToan;
    private RecyclerView recyclerviewCoupon,recyclerviewExpiredCoupon;
    private AdapterCoupon adapterCoupon;
    private AdapterCouponHetHan adapterCouponHetHan;
    private Button btnDongYCoupon;
    private CouponViewModel couponViewModel;
    private SharedPreferences sharedPreferences;
    private static int myFirst = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        btnDongYCoupon = findViewById(R.id.btnDongYCoupon);
        btnBackThanhToan = findViewById(R.id.btnBackThanhToan);
        recyclerviewCoupon = findViewById(R.id.recyclerviewCoupon);
        couponViewModel = new ViewModelProvider(this).get(CouponViewModel.class);

        btnBackThanhToan.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            int savedCouponId = sharedPreferences.getInt("selectedCouponId", -1);
            String selectedCouponCode = sharedPreferences.getString("selectedCouponCode", "");
            double selectedCouponDiscount = sharedPreferences.getFloat("selectedCouponDiscount", -1);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("couponId", savedCouponId);
            resultIntent.putExtra("couponCode", selectedCouponCode);
            resultIntent.putExtra("couponDiscount", selectedCouponDiscount);
            setResult(RESULT_OK, resultIntent);
            finish();
        });


        btnDongYCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCoupon.saveVoucher();
                // Check if a coupon is selected
                if (adapterCoupon.getSelectedCouponId() != -1) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("couponId", adapterCoupon.getSelectedCouponId());
                    resultIntent.putExtra("couponCode", adapterCoupon.getSelectedCouponCode());
                    resultIntent.putExtra("couponDiscount", adapterCoupon.getSelectedCouponDiscount());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("couponId", "");
                    resultIntent.putExtra("couponCode", "");
                    resultIntent.putExtra("couponDiscount", "");
                    setResult(RESULT_OK, resultIntent);
                    finish();
//                }
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


    private void observeViewModel() {
        couponViewModel.getCouponList().observe(this, couponList -> {
            if (couponList != null) {
                adapterCoupon.setCouponList(couponList);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                int savedCouponId = sharedPreferences.getInt("selectedCouponId", -1);
                if (savedCouponId != -1) {
                    adapterCoupon.setSelectedCouponId(savedCouponId);
                }
            }
        });
    }
}
