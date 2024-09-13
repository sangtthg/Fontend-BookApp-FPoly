package frontend_book_market_app.polytechnic.client.profile.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import frontend_book_market_app.polytechnic.client.MainActivity;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.profile.adapter.AddressAdapter;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryAddress;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModel;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModelFactory;
import frontend_book_market_app.polytechnic.client.setting.view.SettingActivity;

public class AddressListActivity extends AppCompatActivity {
    private ImageView btnBackThanhToan;
    private RecyclerView recyclerViewAddressList;
    private Button btnAddAddress;
    private AddressViewModel addressViewModel;
    private AddressAdapter addressAdapter;
    private static final int REQUEST_CODE_ADD_ADDRESS = 2;
    private static final int REQUEST_CODE_UPDATE_ADDRESS = 2; // You can choose any unique integer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_address_list);
        btnBackThanhToan = findViewById(R.id.btnBackThanhToan);
        recyclerViewAddressList = findViewById(R.id.recyclerViewAddressList);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        RepositoryAddress repositoryAddress = new RepositoryAddress(); // Ensure proper initialization
        AddressViewModelFactory factory = new AddressViewModelFactory(sharedPreferences, repositoryAddress);
        addressViewModel = new ViewModelProvider(this, factory).get(AddressViewModel.class);

        addressAdapter = new AddressAdapter(new ArrayList<>(), new AddressAdapter.OnAddressActionListener() {
            @Override
            public void onEditAddress(AddressModel address) {
                Intent intent = new Intent(AddressListActivity.this, UpdateAddressActivity.class);
                intent.putExtra("address_id", address.getAddress_id());
                intent.putExtra("full_name", address.getName());
                intent.putExtra("phone_number", address.getPhone());
                intent.putExtra("delivery_address", address.getAddress());
                intent.putExtra("address_type", address.getAddress_type());
                intent.putExtra("is_default", address.isIs_default());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_ADDRESS);
            }

            @Override
            public void onDeleteAddress(AddressModel address) {
                addressViewModel.deleteAddress(address.getAddress_id());
                Toast.makeText(AddressListActivity.this, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                addressViewModel.loadAddresses();
                loadAddressList();

            }
        });

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ADDRESS);
            }
        });

        recyclerViewAddressList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAddressList.setAdapter(addressAdapter);
        addressViewModel.getAddressList().observe(this, addresses -> {
            if (addresses != null) {
                addressAdapter.setAddressList(addresses);
            }
        });
        addressViewModel.loadAddresses();

        btnBackThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ADDRESS && resultCode == RESULT_OK && requestCode == REQUEST_CODE_UPDATE_ADDRESS) {
            loadAddressList();
        }
    }

    private void loadAddressList() {
        addressViewModel.loadAddresses();

        addressViewModel.getAddressList().observe(this, addresses -> {
            if (addresses != null && !addresses.isEmpty()) {
                addressAdapter.setAddressList(addresses);
            } else {
                Toast.makeText(AddressListActivity.this, "Không có địa chỉ nào", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
