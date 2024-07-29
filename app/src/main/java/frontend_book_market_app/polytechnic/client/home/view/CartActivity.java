package frontend_book_market_app.polytechnic.client.home.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.login.model.AddressModel;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapterCart;
import frontend_book_market_app.polytechnic.client.home.model.CartListResponse;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.setting.view.SettingActivity;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCart cartAdapter;
    private HomeViewModel homeViewModel;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        recyclerView = findViewById(R.id.recyclerViewCart);
        ImageButton btnToggleCheckbox = findViewById(R.id.btnCart);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.fetchCartList();

        btnToggleCheckbox.setOnClickListener(v -> cartAdapter.toggleCheckbox());

        findViewById(R.id.btnThanhToan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> selectedIds = new ArrayList<>(homeViewModel.getSelectedCartItemIds().getValue());

                if (selectedIds.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Hãy chọn ít nhất một sản phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    // Lấy địa chỉ mặc định từ SharedPreferencesHelper
                    AddressModel defaultAddress = getDefaultAddress();

                    // Kiểm tra xem địa chỉ mặc định có tồn tại và đầy đủ thông tin không
                    if (defaultAddress == null || defaultAddress.getPhone() == null || defaultAddress.getPhone().isEmpty() ||
                            defaultAddress.getAddress() == null || defaultAddress.getAddress().isEmpty()) {

                        // Nếu địa chỉ hoặc số điện thoại không có, hiển thị dialog
                        showMissingInfoDialog();
                    } else {
                        // Nếu đầy đủ thông tin, chuyển đến OrderActivity
                        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                        intent.putIntegerArrayListExtra("selectedCartItemIds", selectedIds);
                        startActivity(intent);
                    }
                }
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new AdapterCart(new ArrayList<>(), homeViewModel);

        SkeletonAdapter skeletonAdapter = new SkeletonAdapter(5);
        recyclerView.setAdapter(skeletonAdapter);

        cartAdapter.setOnItemCheckedBoxChangeListener((position, isChecked, bookID, bookTitle, cartId) -> {
            homeViewModel.updateSelectedCartItemIds(cartId, isChecked);
        });

        homeViewModel.getCartItemList().observe(this, cartItems -> {
            if (cartItems != null) {
                recyclerView.setAdapter(cartAdapter);
                cartAdapter.updateCartItems(cartItems);
            }

            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(CartActivity.this, "Giỏ hàng của bạn trống!", Toast.LENGTH_SHORT).show();
            } else {
                for (CartListResponse.CartItemDetail item : cartItems) {
                    if (item.getBook() != null) {
                        System.out.println("Book Title: " + item.getBook().getTitle());
                        System.out.println("Quantity: " + item.getQuantity());
                        System.out.println("New Price: " + item.getBook().getNewPrice());
                        System.out.println("Old Price: " + item.getBook().getOldPrice());
                        System.out.println("Book Avatar: " + item.getBook().getBookAvatar());
                    } else {
                        System.out.println("Book data is null for cart item with ID: " + item.getCartId());
                    }
                }
            }
        });

        findViewById(R.id.backDetailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutCart);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerView.setAdapter(skeletonAdapter);
            homeViewModel.fetchCartList();
            swipeRefreshLayout.setRefreshing(false);
        });
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

    private void showMissingInfoDialog() {
        // Tạo một đối tượng Dialog
        Dialog dialog = new Dialog(CartActivity.this);
        dialog.setContentView(R.layout.dialog_missing_info);

        // Ánh xạ các thành phần trong Dialog
        TextView tvDialogMessage = dialog.findViewById(R.id.tvDialogMessage);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        // Xử lý sự kiện click cho nút OK
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng dialog và chuyển đến SettingActivity
                dialog.dismiss();
                Intent intent = new Intent(CartActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        // Hiển thị dialog
        dialog.show();
    }
}