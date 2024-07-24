package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.OrderItemAdapter;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.OrderResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;
    private ShimmerFrameLayout shimmerFrameLayout;

    private ImageView backDetailButton;
    private TextView tvGiaShip, tvTongPhu, tvTongvanChuyen, tvTongCong, tvTongSoTien;
    private Button btnDatHang;

    SkeletonAdapter skeletonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_order);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerviewOrer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        skeletonAdapter = new SkeletonAdapter(1);
        recyclerView.setAdapter(skeletonAdapter);

        ArrayList<Integer> selectedCartItemIds = getIntent().getIntegerArrayListExtra("selectedCartItemIds");

        tvGiaShip = findViewById(R.id.tvGiaShip);
        tvTongPhu = findViewById(R.id.tvTongPhu);
        tvTongvanChuyen = findViewById(R.id.tvTongvanChuyen);
        tvTongCong = findViewById(R.id.tvTongCong);
        tvTongSoTien = findViewById(R.id.tvTongSoTien);


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.order(selectedCartItemIds);

        homeViewModel.getOrderResponseLiveData().observe(this, orderResponse -> {
            if (orderResponse != null) {

                OrderItemAdapter adapter = new OrderItemAdapter(orderResponse.getItems());
                recyclerView.setAdapter(adapter);

                tvGiaShip.setText(CurrencyFormatter.toVND(String.valueOf(orderResponse.getShippingFee())));
                tvTongPhu.setText(CurrencyFormatter.toVND(String.valueOf(orderResponse.getTotalPrice())));
                tvTongvanChuyen.setText(CurrencyFormatter.toVND(String.valueOf(orderResponse.getShippingFee())));
                tvTongCong.setText(CurrencyFormatter.toVND(String.valueOf(orderResponse.getTotalPrice() + orderResponse.getShippingFee())));
                tvTongSoTien.setText(CurrencyFormatter.toVND(String.valueOf(orderResponse.getTotalPrice() + orderResponse.getShippingFee())));

            }
        });

        findViewById(R.id.btnDatHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.payOrder(getApplicationContext());
            }
        });

        findViewById(R.id.backDetailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
