package frontend_book_market_app.polytechnic.client.profile.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryAddress;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModel;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AddressViewModelFactory;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class AddAddressActivity extends AppCompatActivity {
    private ImageView btnBackAddAddress;
    private TextView txtFullNameLabel, txtPhoneNumberLabel, txtDeliveryAddressLabel, txtAddressTypeLabel, txtDefaultAddress;
    private EditText edtFullName, edtPhoneNumber, edtDeliveryAddress, edtTenduong;
    private Spinner spinnerAddressType;
    private Switch switchDefaultAddress;
    private Button btnSaveAddress;
    public static final int REQUEST_CODE_ADD_CITY = 1;
    private String fullAddress2;
    private Toolbar toolbarqldc;

    private SharedPreferencesHelper sharedPreferencesHelper; // Thêm dòng này

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_add_address);
        btnBackAddAddress = findViewById(R.id.btnBackAddAddress);
        edtFullName = findViewById(R.id.edtFullName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtDeliveryAddress = findViewById(R.id.edtDeliveryAddress);
        edtTenduong = findViewById(R.id.edtTenduong);
        spinnerAddressType = findViewById(R.id.spinnerAddressType);
        switchDefaultAddress = findViewById(R.id.switchDefaultAddress);
        btnSaveAddress = findViewById(R.id.btnSaveAddress);
        sharedPreferencesHelper = new SharedPreferencesHelper(this); // Khởi tạo SharedPreferencesHelper


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.address_type_options,
                R.layout.spinner_item); // Layout cho item của spinner

        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerAddressType.setAdapter(adapter);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        RepositoryAddress repositoryAddress = new RepositoryAddress(); // hoặc cách khởi tạo phù hợp
        AddressViewModelFactory factory = new AddressViewModelFactory(sharedPreferences, repositoryAddress);
        AddressViewModel addressViewModel = new ViewModelProvider(this, factory).get(AddressViewModel.class);


//        // Retrieve data from Intent
//        Intent intent = getIntent();
//        String provinceName = intent.getStringExtra("PROVINCE_NAME");
//        String districtName = intent.getStringExtra("DISTRICT_NAME");
//        String wardName = intent.getStringExtra("WARD_NAME");
//
//        if (provinceName != null && !provinceName.isEmpty() &&
//                districtName != null && !districtName.isEmpty() &&
//                wardName != null && !wardName.isEmpty()) {
//            String fullAddress = provinceName + "\n" + districtName + "\n" + wardName;
//            edtDeliveryAddress.setText(fullAddress);
//        } else {
//            edtDeliveryAddress.setText("");
//        }
        btnBackAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Set up the click listener for edtDeliveryAddress
        edtDeliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddCityActivity
                Intent intent = new Intent(AddAddressActivity.this, AddCityActivity.class);

                // Lưu tạm dữ liệu của các trường vào Intent
                intent.putExtra("FULL_NAME", edtFullName.getText().toString());
                intent.putExtra("PHONE_NUMBER", edtPhoneNumber.getText().toString());
                intent.putExtra("ADDRESS_TYPE", spinnerAddressType.getSelectedItem().toString());
                intent.putExtra("DEFAULT_ADDRESS", switchDefaultAddress.isChecked());

                startActivityForResult(intent, REQUEST_CODE_ADD_CITY);
            }
        });


        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtFullName.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String tenduong = edtTenduong.getText().toString().trim();
                String addressType = spinnerAddressType.getSelectedItem().toString();
                boolean isDefaultAddress = switchDefaultAddress.isChecked();
                String combinedAddress = tenduong + ", " + fullAddress2;

                // Kiểm tra nếu bất kỳ trường nào bị bỏ trống
                if (fullName.isEmpty()) {
                    edtFullName.setError("Họ và tên không được để trống");
                    edtFullName.requestFocus();
                    return;
                }

                if (phoneNumber.isEmpty()) {
                    edtPhoneNumber.setError("Số điện thoại không được để trống");
                    edtPhoneNumber.requestFocus();
                    return;
                }

                if (tenduong.isEmpty()) {
                    edtTenduong.setError("Tên đường không được để trống");
                    edtTenduong.requestFocus();
                    return;
                }

                if (fullAddress2 == null || fullAddress2.isEmpty()) {
                    edtDeliveryAddress.setError("Địa chỉ giao hàng không được để trống");
                    edtDeliveryAddress.requestFocus();
                    return;
                }

                // Kiểm tra số điện thoại có đúng định dạng Việt Nam không
                if (!isValidPhoneNumber(phoneNumber)) {
                    edtPhoneNumber.setError("Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại Việt Nam.");
                    edtPhoneNumber.requestFocus();
                    return;
                }

                AddressModel addressModel = new AddressModel(fullName, phoneNumber, combinedAddress, addressType, isDefaultAddress);
                addressViewModel.addAddress(addressModel);
                addressViewModel.getAddressAddSuccess().observe(AddAddressActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean success) {
                        if (success) {

                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK, resultIntent);
                            Toast.makeText(AddAddressActivity.this, "Đã thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

                addressViewModel.getErrorMessage().observe(AddAddressActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String error) {
                        Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String provinceName = data.getStringExtra("PROVINCE_NAME");
                String districtName = data.getStringExtra("DISTRICT_NAME");
                String wardName = data.getStringExtra("WARD_NAME");
                if (provinceName != null && districtName != null && wardName != null) {
                    String fullAddress = provinceName + "\n" + districtName + "\n" + wardName;
                     fullAddress2 = wardName + ", " + districtName + ", " + provinceName;
                    edtDeliveryAddress.setText(fullAddress);
                }
            }
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Số điện thoại Việt Nam bắt đầu bằng 0 hoặc +84, theo sau là 9 hoặc 10 chữ số.
        return phoneNumber.matches("(0|\\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-6|8-9]|9[0-9])[0-9]{7}");
    }

}
