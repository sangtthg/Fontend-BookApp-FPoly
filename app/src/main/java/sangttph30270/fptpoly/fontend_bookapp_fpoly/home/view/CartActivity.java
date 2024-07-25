package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterCart;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCart cartAdapter;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

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
                    Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                    intent.putIntegerArrayListExtra("selectedCartItemIds", selectedIds);
                    startActivity(intent);
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
}