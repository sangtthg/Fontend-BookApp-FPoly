package frontend_book_market_app.polytechnic.client.home.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;


import frontend_book_market_app.polytechnic.client.coupon.view.CouponActivity;
import frontend_book_market_app.polytechnic.client.home.adapter.PaymentItemAdapter;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryAddress;
import frontend_book_market_app.polytechnic.client.profile.view.AddressListActivity;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModel;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModelFactory;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class PaymentActivity extends AppCompatActivity {
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerViewPaymentItem;
    private SkeletonAdapter skeletonAdapter;
    private LinearLayout layout2, layout3, layoutCoupon;
    private TextView txtThayDoiDiaChi, chonCoupon, tvGiaShip, tvTongPhu, tvTongVanChuyen, tvTongCong, tvTongSoTien, tvNhanHang, tvTenNguoiDungOrder, tvSDTNguoiDungOrder, tvDiaChiOrderChiTiet;
    private double totalPrice = 0.0;
    String soDienThoai;
    String diaChi;
    private static final int COUPON_REQUEST_CODE = 100; // Define a unique request code
    private AddressViewModel addressViewModel;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    double totalPriceExcludingShipping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_payment);
        tvSDTNguoiDungOrder = findViewById(R.id.tvSDTNguoiDungOrder);
        tvDiaChiOrderChiTiet = findViewById(R.id.tvDiaChiOrderChiTiet);
        tvTenNguoiDungOrder = findViewById(R.id.tvTenNguoiDungOrder);
        ArrayList<Integer> selectedCartItemIds = getIntent().getIntegerArrayListExtra("selectedCartItemIds");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        RepositoryAddress repositoryAddress = new RepositoryAddress(); // Ensure proper initialization
        AddressViewModelFactory factory = new AddressViewModelFactory(sharedPreferences, repositoryAddress);
        editor = sharedPreferences.edit();

        addressViewModel = new ViewModelProvider(this, factory).get(AddressViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        Log.d("PaymentActivity", "AddressViewModel initialized");
        addressViewModel.loadAddresses();
        addressViewModel.getAddressList().observe(this, addresses -> {
            if (addresses != null) {
                Log.d("PaymentActivity", "Address list size: " + addresses.size()); // Log size of the list
                List<AddressModel> defaultAddresses = getDefaultAddresses(addresses);
                if (!defaultAddresses.isEmpty()) {
                    AddressModel defaultAddress = defaultAddresses.get(0); // Get the first default address
                    // Update UI with default address
                    tvSDTNguoiDungOrder.setText(defaultAddress.getPhone());
                    tvDiaChiOrderChiTiet.setText(defaultAddress.getAddress());
                    tvTenNguoiDungOrder.setText(defaultAddress.getName());
                    Log.d("PaymentActivity", " " + defaultAddress.getPhone());
                    Log.d("PaymentActivity", " " + defaultAddress.getAddress());
                    Log.d("PaymentActivity", " " + defaultAddress.getName());
                } else {
                    tvDiaChiOrderChiTiet.setText("Chưa có địa chỉ mặc định");
                    Log.d("PaymentActivity", "No default address available");
                }
            } else {
                Log.d("PaymentActivity", "Address list is null");
                tvDiaChiOrderChiTiet.setText("Chưa có địa chỉ mặc định");
            }
        });



        initView();
        initRecyclerView(this);
        initItemClick();
        hidenLayout();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            homeViewModel.fetchOrderByCartID(selectedCartItemIds);
        }, 2000);
        chonCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, CouponActivity.class);
                startActivityForResult(intent, COUPON_REQUEST_CODE);
            }
        });

        homeViewModel.getOrderResponseLiveData().observe(this, orderResponse -> {
            if (orderResponse != null) {
                recyclerViewPaymentItem.setAdapter(new PaymentItemAdapter(orderResponse.getItems()));
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
                double shippingFee = orderResponse.getShippingFee();
                totalPrice = orderResponse.getTotalPrice(); // Cập nhật biến toàn cục
                tvNhanHang.setText(orderResponse.getDeliveryDateText());
                 totalPriceExcludingShipping = totalPrice - shippingFee;
                Log.d("PaymentActivity", "TauTau: " + totalPrice);
                tvGiaShip.setText(CurrencyFormatter.toVND(String.valueOf(shippingFee)));
                tvTongPhu.setText(CurrencyFormatter.toVND(String.valueOf(totalPriceExcludingShipping)));
                tvTongVanChuyen.setText(CurrencyFormatter.toVND(String.valueOf(shippingFee)));
                tvTongCong.setText(CurrencyFormatter.toVND(String.valueOf(totalPrice)));
                tvTongSoTien.setText(CurrencyFormatter.toVND(String.valueOf(totalPrice)));
            }
        });


    }

    private void hidenLayout() {
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layoutCoupon = findViewById(R.id.layoutCoupon);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
    }

    private void initItemClick() {
        findViewById(R.id.btnDatHang).setOnClickListener(v -> {
            String couponCode = chonCoupon.getText().toString().equals("Chọn hoặc nhập mã >") ? "" : chonCoupon.getText().toString();
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext());
            String token = sharedPreferencesHelper.getToken();
            Log.d("PaymentActivity", "Coupon Code: " + couponCode);
            Log.d("khiAnDâtHang", "Token: " + token);
            if (token.isEmpty()) {
                Toast.makeText(PaymentActivity.this, "Token không hợp lệ, không thể thanh toán!", Toast.LENGTH_SHORT).show();
                return;
            }

            homeViewModel.payOrder(getApplicationContext(), -1, couponCode, token);
            Log.d("PaymentActivity", "Calling payOrder with orderId: -1 and couponCode: " + couponCode);
        });

        findViewById(R.id.backDetailButton).setOnClickListener(v -> {
            finish();
        });
    }


    private void initRecyclerView(Context context) {
        recyclerViewPaymentItem.setLayoutManager(new LinearLayoutManager(context));
        skeletonAdapter = new SkeletonAdapter(3);
        recyclerViewPaymentItem.setAdapter(skeletonAdapter);
    }

    private void initView() {
        chonCoupon = findViewById(R.id.chonCoupon);
        tvGiaShip = findViewById(R.id.tvGiaShip);
        tvTongPhu = findViewById(R.id.tvTongPhu);
        tvTongVanChuyen = findViewById(R.id.tvTongvanChuyen);
        tvTongCong = findViewById(R.id.tvTongCong);
        tvTongSoTien = findViewById(R.id.tvTongSoTien);
        recyclerViewPaymentItem = findViewById(R.id.recyclerviewOrer);
        tvNhanHang = findViewById(R.id.tvNhanHang);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COUPON_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            int couponId = data.getIntExtra("couponId", -1);
            String couponCode = data.getStringExtra("couponCode");
            double couponDiscount = data.getDoubleExtra("couponDiscount", 0.0);
            if (couponDiscount > totalPrice &&  couponDiscount > totalPriceExcludingShipping) {
                Toast.makeText(PaymentActivity.this, "Không cho nhập mã giảm giá", Toast.LENGTH_SHORT).show();
            } else {
                double finalPrice = totalPrice - couponDiscount;
                Log.d("PaymentActivity", "Coupon ID: " + couponId);
                Log.d("PaymentActivity", "Coupon Code: " + couponCode);
                Log.d("PaymentActivity", "Coupon Discount: " + couponDiscount);
                Log.d("PaymentActivity", "Total Price: " + totalPrice);
                Log.d("PaymentActivity", "Final Price: " + finalPrice);
                Toast.makeText(PaymentActivity.this, "Áp dụng mã giảm giá thành công", Toast.LENGTH_SHORT).show();

                // Cập nhật giao diện với giá cuối cùng
                tvTongCong.setText(CurrencyFormatter.toVND(String.valueOf(finalPrice)));
                tvTongSoTien.setText(CurrencyFormatter.toVND(String.valueOf(finalPrice)));
                if (couponCode != null && !couponCode.isEmpty()) {
                    chonCoupon.setText(couponCode);
                    chonCoupon.setTextColor(Color.RED);
                    chonCoupon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Adjust size as needed
                    chonCoupon.setTypeface(null, Typeface.BOLD);
                    chonCoupon.setAlpha(1.0f); // Fully opaque
                } else {
                    // Reset to default text if no coupon code is applied
                    chonCoupon.setText("Chọn hoặc nhập mã >");
                    chonCoupon.setTextColor(Color.parseColor("#888888"));
                    chonCoupon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    chonCoupon.setTypeface(null, Typeface.NORMAL);
                    chonCoupon.setAlpha(0.5f);
                }
            }


        }
    }

    private List<AddressModel> getDefaultAddresses(List<AddressModel> addresses) {
        List<AddressModel> defaultAddresses = new ArrayList<>();
        if (addresses != null) {
            for (AddressModel address : addresses) {
                if (address.isIs_default()) {
                    defaultAddresses.add(address);
                }
            }
        }
        return defaultAddresses;
    }


    @Override
    protected void onDestroy() {
        editor.remove("selectedCouponId");
        editor.remove("selectedCouponCode");
        editor.remove("selectedCouponDiscount");
        editor.apply();
        super.onDestroy();
    }


}

