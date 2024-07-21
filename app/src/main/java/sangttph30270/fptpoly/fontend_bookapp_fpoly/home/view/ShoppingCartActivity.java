package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterCart;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCart cartAdapter;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.fetchCartList();

        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView = findViewById(R.id.recyclerViewCart);


        findViewById(R.id.backDetailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new AdapterCart(new ArrayList<>());
        recyclerView.setAdapter(cartAdapter);

        homeViewModel.getCartItemList().observe(this, cartItems -> {
            if (cartItems != null) {
                cartAdapter.updateCartItems(cartItems);
            }
        });

//        Button btnCheckout = findViewById(R.id.btnCheckout);
//        btnCheckout.setOnClickListener(v -> {
//            Toast.makeText(this, "Proceeding to checkout...", Toast.LENGTH_SHORT).show();
//        });

//        setupToolbar();
    }

    private void setupToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shopping Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}