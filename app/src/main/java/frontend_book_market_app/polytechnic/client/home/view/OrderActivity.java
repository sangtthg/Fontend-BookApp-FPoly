package frontend_book_market_app.polytechnic.client.home.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.login.model.AddressModel;
import frontend_book_market_app.polytechnic.client.home.adapter.OrderItemAdapter;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;

    private TextView tvGiaShip, tvTongPhu, tvTongvanChuyen, tvTongCong, tvTongSoTien, tvNhanHang, tvTenNguoiDungOrder, tvSDTNguoiDungOrder, tvDiaChiOrderChiTiet;

    private SkeletonAdapter skeletonAdapter;

    private LinearLayout layout2, layout3;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_order);
        tvSDTNguoiDungOrder = findViewById(R.id.tvSDTNguoiDungOrder);
        tvDiaChiOrderChiTiet = findViewById(R.id.tvDiaChiOrderChiTiet);
        tvTenNguoiDungOrder = findViewById(R.id.tvTenNguoiDungOrder);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        String tenNguoiDung = sharedPreferencesHelper.getUsername();
        AddressModel defaultAddressModel = getDefaultAddress();

        String soDienThoai = defaultAddressModel != null ? convertPhoneNumberToInternational(defaultAddressModel.getPhone()) : "Chưa có số điện thoại";
        String diaChi = defaultAddressModel != null ? defaultAddressModel.getAddress() : "Chưa có địa chỉ";

        tvTenNguoiDungOrder.setText(tenNguoiDung);
        tvSDTNguoiDungOrder.setText(formatPhoneNumber(soDienThoai));
        tvDiaChiOrderChiTiet.setText(diaChi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ArrayList<Integer> selectedCartItemIds = getIntent().getIntegerArrayListExtra("selectedCartItemIds");
        initView();
        initRecyclerView(this);
        initItemClick();
        hidenLayout();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.fetchOrderByCartID(selectedCartItemIds);
        homeViewModel.getOrderResponseLiveData().observe(this, orderResponse -> {
            if (orderResponse != null) {
                recyclerView.setAdapter(new OrderItemAdapter(orderResponse.getItems()));
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
                double shippingFee = orderResponse.getShippingFee();
                double totalPrice = orderResponse.getTotalPrice();
                tvNhanHang.setText(orderResponse.getDeliveryDateText());
                double totalPriceExcludingShipping = totalPrice - shippingFee;

                tvGiaShip.setText(CurrencyFormatter.toVND(String.valueOf(shippingFee)));
                tvTongPhu.setText(CurrencyFormatter.toVND(String.valueOf(totalPriceExcludingShipping)));
                tvTongvanChuyen.setText(CurrencyFormatter.toVND(String.valueOf(shippingFee)));
                tvTongCong.setText(CurrencyFormatter.toVND(String.valueOf(totalPrice)));
                tvTongSoTien.setText(CurrencyFormatter.toVND(String.valueOf(totalPrice)));
            }
        });


    }

    private void hidenLayout() {
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);

        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
    }

    private void initItemClick() {
        findViewById(R.id.btnDatHang).setOnClickListener(v -> homeViewModel.payOrder(getApplicationContext(), -1));

        findViewById(R.id.backDetailButton).setOnClickListener(v -> finish());
    }

    private void initRecyclerView(Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        skeletonAdapter = new SkeletonAdapter(3);
        recyclerView.setAdapter(skeletonAdapter);
    }

    private void initView() {

        tvGiaShip = findViewById(R.id.tvGiaShip);
        tvTongPhu = findViewById(R.id.tvTongPhu);
        tvTongvanChuyen = findViewById(R.id.tvTongvanChuyen);
        tvTongCong = findViewById(R.id.tvTongCong);
        tvTongSoTien = findViewById(R.id.tvTongSoTien);
        recyclerView = findViewById(R.id.recyclerviewOrer);
        tvNhanHang = findViewById(R.id.tvNhanHang);
    }

    private AddressModel getDefaultAddress() {
        List<AddressModel> addresses = sharedPreferencesHelper.getAddresses();

        if (addresses == null || addresses.isEmpty()) {
            return null;
        }

        for (AddressModel address : addresses) {
            if (address.isDefault()) {
                return address;
            }
        }

        return null;
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() > 6) {
            return phoneNumber.substring(0, 4) + "****" + phoneNumber.substring(phoneNumber.length() - 4);
        } else {
            return phoneNumber;
        }
    }

    private String convertPhoneNumberToInternational(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "+84" + phoneNumber.substring(1);
        }
        return phoneNumber;
    }


}
