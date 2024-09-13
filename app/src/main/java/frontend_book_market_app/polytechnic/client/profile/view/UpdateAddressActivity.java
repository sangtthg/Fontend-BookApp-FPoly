package frontend_book_market_app.polytechnic.client.profile.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.util.Arrays;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;
import frontend_book_market_app.polytechnic.client.profile.model.UpdateAddressModel;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryAddress;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModel;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModelFactory;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class UpdateAddressActivity extends AppCompatActivity {

    // Declare UI components
    private ImageView btnBackUpdateAddress;
    private EditText edtFullNameUpdate, edtPhoneNumberUpdate, edtDeliveryAddressUpdate, edtStreetNameUpdate;
    private Spinner spinnerAddressTypeUpdate;
    private Switch switchDefaultAddressUpdate;
    private Button btnSaveAddressUpdate;
    private static final int REQUEST_CODE_UPDATE_CITY = 1;
    private String chuoi;
    private AddressViewModel addressViewModel;
    private int addressId; // Variable to store the address ID
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        btnBackUpdateAddress = findViewById(R.id.btnBackUpdateAddress);
        edtFullNameUpdate = findViewById(R.id.edtFullNameUpdate);
        edtPhoneNumberUpdate = findViewById(R.id.edtPhoneNumberUpdate);
        edtDeliveryAddressUpdate = findViewById(R.id.edtDeliveryAddressUpdate);
        edtStreetNameUpdate = findViewById(R.id.edtStreetNameUpdate);
        spinnerAddressTypeUpdate = findViewById(R.id.spinnerAddressTypeUpdate);
        switchDefaultAddressUpdate = findViewById(R.id.switchDefaultAddressUpdate);
        btnSaveAddressUpdate = findViewById(R.id.btnSaveAddressUpdate);
         sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        gson = new Gson();
        RepositoryAddress repositoryAddress = new RepositoryAddress(); // or initialize it appropriately
        AddressViewModelFactory factory = new AddressViewModelFactory(sharedPreferences, repositoryAddress);
        addressViewModel = new ViewModelProvider(this, factory).get(AddressViewModel.class);
        Intent intent = getIntent();
        if (intent != null) {
            String fullName = intent.getStringExtra("full_name");
            String phoneNumber = intent.getStringExtra("phone_number");
            String deliveryAddress = intent.getStringExtra("delivery_address");
            String addressType = intent.getStringExtra("address_type");
            boolean isDefault = intent.getBooleanExtra("is_default", false);
            addressId = intent.getIntExtra("address_id", -1); // Retrieve the address ID
            Log.d("UpdateAddressActivity", "Address ID: " + addressId);
            edtFullNameUpdate.setText(fullName);
            edtPhoneNumberUpdate.setText(phoneNumber);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.address_type_options,
                    R.layout.spinner_item); // Use the custom layout for items

//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAddressTypeUpdate.setAdapter(adapter);
            int addressTypePosition = adapter.getPosition(addressType);
            spinnerAddressTypeUpdate.setSelection(addressTypePosition);
            switchDefaultAddressUpdate.setChecked(isDefault);
            if (deliveryAddress != null && !deliveryAddress.isEmpty()) {
                String[] addressParts = deliveryAddress.split("\n");
                if (addressParts.length >= 4) {
                    edtStreetNameUpdate.setText(addressParts[0]); // Tên đường
                    edtDeliveryAddressUpdate.setText(addressParts[3] + "\n" + addressParts[2] + "\n" + addressParts[1]);
                } else {
                    Log.d("UpdateAddressActivity", "Độ dài các phần địa chỉ nhỏ hơn mong đợi");
                    edtStreetNameUpdate.setText("");
                    edtDeliveryAddressUpdate.setText(deliveryAddress);
                }
            } else {
                edtStreetNameUpdate.setText("");
                edtDeliveryAddressUpdate.setText("");
            }


        }

        btnSaveAddressUpdate.setOnClickListener(v -> {
            String updatedFullName = edtFullNameUpdate.getText().toString().trim();
            String updatedPhoneNumber = edtPhoneNumberUpdate.getText().toString().trim();
            String updatedStreetName = edtStreetNameUpdate.getText().toString().trim();
            String updatedDeliveryAddress = edtDeliveryAddressUpdate.getText().toString().trim();

            if (!validateFullName(updatedFullName) || !validatePhoneNumber(updatedPhoneNumber) || !validateAddress(updatedStreetName, updatedDeliveryAddress)) {
                return;
            }
            String updatedCombinedAddress = updatedStreetName + ", " + updatedDeliveryAddress;
            String updatedAddressType = spinnerAddressTypeUpdate.getSelectedItem().toString();
            boolean updatedIsDefault = switchDefaultAddressUpdate.isChecked();
            UpdateAddressModel updatedAddress = new UpdateAddressModel(
                    addressId,
                    updatedFullName,
                    updatedPhoneNumber,
                    updatedCombinedAddress,
                    updatedAddressType,
                    updatedIsDefault
            );

            addressViewModel.updateAddress(updatedAddress);
            addressViewModel.getAddressUpdateSuccess().observe(this, isSuccess -> {
                if (isSuccess != null && isSuccess) {
                    Toast.makeText(UpdateAddressActivity.this, "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
            addressViewModel.getErrorMessage().observe(this, errorMessage -> {
                if (errorMessage != null) {
                    Toast.makeText(UpdateAddressActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
        btnBackUpdateAddress.setOnClickListener(v -> finish());
        edtDeliveryAddressUpdate.setOnClickListener(v -> {
            Intent updateCityIntent = new Intent(UpdateAddressActivity.this, UpdateCityActivity.class);
            startActivityForResult(updateCityIntent, REQUEST_CODE_UPDATE_CITY);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_CITY && resultCode == RESULT_OK && data != null) {
            String province = data.getStringExtra("PROVINCE_NAME");
            String district = data.getStringExtra("DISTRICT_NAME");
            String ward = data.getStringExtra("WARD_NAME");

            if (province != null && district != null && ward != null) {
                String combinedAddress = ward + "\n" + district + "\n" + province;
                chuoi = combinedAddress;
                edtDeliveryAddressUpdate.setText(combinedAddress);
            }
        }
    }

    private boolean validateFullName(String fullName) {
        if (fullName.isEmpty()) {
            edtFullNameUpdate.setError("Họ và tên không được để trống");
            edtFullNameUpdate.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            edtPhoneNumberUpdate.setError("Số điện thoại không được để trống");
            edtPhoneNumberUpdate.requestFocus();
            return false;
        }

        if (!phoneNumber.matches("^\\+?[0-9]{10,13}$")) {
            edtPhoneNumberUpdate.setError("Số điện thoại không hợp lệ");
            edtPhoneNumberUpdate.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateAddress(String streetName, String deliveryAddress) {
        if (streetName.isEmpty()) {
            edtStreetNameUpdate.setError("Tên đường không được để trống");
            edtStreetNameUpdate.requestFocus();
            return false;
        }
        if (deliveryAddress.isEmpty()) {
            edtDeliveryAddressUpdate.setError("Địa chỉ giao hàng không được để trống");
            edtDeliveryAddressUpdate.requestFocus();
            return false;
        }
        return true;
    }


}
