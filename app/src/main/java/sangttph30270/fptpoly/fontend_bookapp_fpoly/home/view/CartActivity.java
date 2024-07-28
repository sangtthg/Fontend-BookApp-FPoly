package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.model.AddressModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterCart;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.view.SettingActivity;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCart cartAdapter;
    private HomeViewModel homeViewModel;
    private SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_shopping_cart);
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

        cartAdapter = new AdapterCart(new ArrayList<>());
        recyclerView.setAdapter(cartAdapter);

        cartAdapter.setOnItemCheckedBoxChangeListener((position, isChecked, bookID, bookTitle, cartId) -> {
            homeViewModel.updateSelectedCartItemIds(cartId, isChecked);
        });

        homeViewModel.getCartItemList().observe(this, cartItems -> {
            if (cartItems != null) {
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