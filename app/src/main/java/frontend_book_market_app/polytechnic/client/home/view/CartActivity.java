package frontend_book_market_app.polytechnic.client.home.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapterCart;
import frontend_book_market_app.polytechnic.client.home.model.CartListResponse;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel; // Correct import
import frontend_book_market_app.polytechnic.client.profile.network.SharedService;
import frontend_book_market_app.polytechnic.client.profile.view.AddressListActivity;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;
import frontend_book_market_app.polytechnic.client.setting.view.SettingActivity;

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
                    AddressModel defaultAddress = SharedService.getInstance().getDefaultAddress();

                    if (defaultAddress == null ||
                            defaultAddress.getPhone() == null || defaultAddress.getPhone().isEmpty() ||
                            defaultAddress.getAddress() == null || defaultAddress.getAddress().isEmpty()) {
                        showMissingInfoDialog();
                    } else {
                        // Use the address details if all fields are present
                        Log.d("CartActivity", "Default Address: " + defaultAddress.toString());

                        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
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
                        Log.d("CartItem", "Book Title: " + item.getBook().getTitle());
                        Log.d("CartItem", "Quantity: " + item.getQuantity());
                        Log.d("CartItem", "New Price: " + item.getBook().getNewPrice());
                        Log.d("CartItem", "Old Price: " + item.getBook().getOldPrice());
                        Log.d("CartItem", "Book Avatar: " + item.getBook().getBookAvatar());
                    } else {
                        Log.d("CartItem", "Book data is null for cart item with ID: " + item.getCartId());
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

    public AddressModel getDefaultAddress() {
        List<AddressModel> addresses = sharedPreferencesHelper.getAddresses();


        if (addresses == null || addresses.isEmpty()) {
            Log.d("SharedPreferencesHelper", "No addresses found.");
            return null;
        }

        for (AddressModel address : addresses) {
            if (address.isIs_default()) {
                Log.d("SharedPreferencesHelper", "Default Address: " + address.toString());
                return address;
            }
        }

        Log.d("SharedPreferencesHelper", "No default address found.");
        return null;
    }


    private void showMissingInfoDialog() {
        Dialog dialog = new Dialog(CartActivity.this);
        dialog.setContentView(R.layout.dialog_missing_info);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(CartActivity.this, AddressListActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}
