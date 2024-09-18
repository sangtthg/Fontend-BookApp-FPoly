package frontend_book_market_app.polytechnic.client.home.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
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
    private String ten;
    private String sdt;
    private String diachi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        EdgeToEdge.enable(this);
        recyclerView = findViewById(R.id.recyclerViewCart);
        ImageButton btnToggleCheckbox = findViewById(R.id.btnCart);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        RepositoryAddress repositoryAddress = new RepositoryAddress(); // Ensure proper initialization
        AddressViewModelFactory factory = new AddressViewModelFactory(sharedPreferences, repositoryAddress);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        addressViewModel = new ViewModelProvider(this, factory).get(AddressViewModel.class);
        homeViewModel.fetchCartList();

        addressViewModel = new ViewModelProvider(this, factory).get(AddressViewModel.class);
        addressViewModel.loadAddresses();
        addressViewModel.getAddressList().observe(this, addresses -> {
            if (addresses != null) {
                Log.d("PaymentActivity", "Address list size: " + addresses.size()); // Log size of the list
                List<AddressModel> defaultAddresses = getDefaultAddresses(addresses);
                if (!defaultAddresses.isEmpty()) {
                    AddressModel defaultAddress = defaultAddresses.get(0); // Get the first default address
                    Log.d("CartActivity222222", " " + defaultAddress.getPhone());
                    Log.d("CartActivity222222", " " + defaultAddress.getAddress());
                    Log.d("CartActivity222222", " " + defaultAddress.getName());
                    ten = defaultAddress.getName();
                    sdt = defaultAddress.getPhone();
                    diachi = defaultAddress.getAddress();
                } else {

                    Log.d("PaymentActivity", "No default address available");
                }
            } else {
                Log.d("PaymentActivity", "Address list is null");

            }
        });







        // Trong Activity đích, lấy Intent đã được gửi tới
        Intent intent = getIntent();
        ten = intent.getStringExtra("ten");
        sdt = intent.getStringExtra("sdt");
        diachi = intent.getStringExtra("diachi");

        Log.d("CartActivity", " " + ten);
        Log.d("CartActivity", " " + sdt);
        Log.d("CartActivity", " " + diachi);


        btnToggleCheckbox.setOnClickListener(v -> cartAdapter.toggleCheckbox());

        findViewById(R.id.btnDatHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> selectedIds = new ArrayList<>(homeViewModel.getSelectedCartItemIds().getValue());

                if (selectedIds.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Hãy chọn ít nhất một sản phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    if (Objects.equals(getUserAddress(), "0")) showMissingInfoDialog();
                    else {
                        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                        intent.putIntegerArrayListExtra("selectedCartItemIds", selectedIds);
                        intent.putExtra("ten", ten);
                        intent.putExtra("sdt", sdt);
                        intent.putExtra("diachi", diachi);
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
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Thiếu Thông Tin")
                .setMessage("Bạn chưa cung cấp địa chỉ mặc định. Bạn có muốn thêm địa chỉ không?")
                .setCancelable(false)
                .setPositiveButton("Thêm địa chỉ", R.drawable.ic_check_24_default, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Chuyển đến màn hình thêm địa chỉ
                        Intent intent = new Intent(CartActivity.this, AddressListActivity.class);
                        intent.putExtra("diachi", 0);
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Hủy", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        mDialog.show();
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
    protected void onResume() {
        super.onResume();
        loadAddress();
    }



    private void loadAddress() {
        addressViewModel.loadAddresses();
        addressViewModel.getAddressList().observe(this, addresses -> {
            if (addresses != null) {
                Log.d("CartActivity", "Address list size: " + addresses.size());
                List<AddressModel> defaultAddresses = getDefaultAddresses(addresses);
                if (!defaultAddresses.isEmpty()) {
                    AddressModel defaultAddress = defaultAddresses.get(0);
                    Log.d("CartActivity", " " + defaultAddress.getPhone());
                    Log.d("CartActivity", " " + defaultAddress.getAddress());
                    Log.d("CartActivity", " " + defaultAddress.getName());
                    ten = defaultAddress.getName();
                    sdt = defaultAddress.getPhone();
                    diachi = defaultAddress.getAddress();
                } else {
                    Log.d("CartActivity", "No default address available");
                }
            } else {
                Log.d("CartActivity", "Address list is null");
            }
        });
    }

}


