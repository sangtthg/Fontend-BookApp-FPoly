package frontend_book_market_app.polytechnic.client.home.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.don_hang.viewmodel.DonHangUserViewModel;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapterCart;
import frontend_book_market_app.polytechnic.client.home.model.CartListResponse;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryAddress;
import frontend_book_market_app.polytechnic.client.profile.network.SharedService;
import frontend_book_market_app.polytechnic.client.profile.view.AddressListActivity;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModel;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModelFactory;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCart cartAdapter;
    private HomeViewModel homeViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences sharedPreferences;
    private AddressViewModel addressViewModel;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        EdgeToEdge.enable(this);
        recyclerView = findViewById(R.id.recyclerViewCart);
        ImageButton btnToggleCheckbox = findViewById(R.id.btnCart);
        RepositoryAddress repositoryAddress = new RepositoryAddress(); // Ensure proper initialization
        AddressViewModelFactory factory = new AddressViewModelFactory(sharedPreferences, repositoryAddress);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        addressViewModel = new ViewModelProvider(this, factory).get(AddressViewModel .class);

        homeViewModel.fetchCartList();
        btnToggleCheckbox.setOnClickListener(v -> cartAdapter.toggleCheckbox());

        findViewById(R.id.btnDatHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> selectedIds = new ArrayList<>(homeViewModel.getSelectedCartItemIds().getValue());

                if (selectedIds.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Hãy chọn ít nhất một sản phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    if (Objects.equals(getUserAddress(), "0")) showMissingInfoDialog();
                    else{
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


        findViewById(R.id.backDetailButton).setOnClickListener(v -> finish());

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutCart);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerView.setAdapter(skeletonAdapter);
            homeViewModel.fetchCartList();
            swipeRefreshLayout.setRefreshing(false);
        });









    }

    private void showMissingInfoDialog() {
        Dialog dialog = new Dialog(CartActivity.this);
        dialog.setContentView(R.layout.dialog_missing_info);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(CartActivity.this, AddressListActivity.class);
            startActivity(intent);
        });

        dialog.show();
    }

    public String getUserAddress() {
        String defaultAddress = sharedPreferences.getString("default_address", null);

        if (defaultAddress != null) {
            Log.d("SharedPreferencesHelper", "Retrieved Default Address: " + defaultAddress);
        } else {
            Log.e("SharedPreferencesHelper", "No Default Address found.");
        }

        return defaultAddress;
    }




}


