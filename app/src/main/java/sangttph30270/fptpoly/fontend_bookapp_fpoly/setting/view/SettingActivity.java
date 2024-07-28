package sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.MainActivity;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.model.AddressModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.adapter.AddressAdapter;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.model.AddressRequestModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.viewmodel.SettingViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.viewmodel.SettingViewModelFactory;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;

public class SettingActivity extends AppCompatActivity {
    private ImageButton btnBackThongTinChung;
    private EditText edtTenNguoiDungCapNhat, edtSoDienThoaiCapNhat, edtEmailCapNhat, edtDiaChiCapNhat;
    private Button btnCapNhatThongTin;
    private RecyclerView recyclerViewAddresses;
    private AddressAdapter addressAdapter;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private SettingViewModel settingViewModel;
    private List<AddressModel> addressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        // Khai báo các ID
        btnBackThongTinChung = findViewById(R.id.btnBackThongTinChung);
        edtTenNguoiDungCapNhat = findViewById(R.id.edtTenNguoiDungCapNhat);
        edtSoDienThoaiCapNhat = findViewById(R.id.edtSoDienThoaiCapNhat);
        edtEmailCapNhat = findViewById(R.id.edtEmailCapNhat);
        edtDiaChiCapNhat = findViewById(R.id.edtDiaChiCapNhat);
        btnCapNhatThongTin = findViewById(R.id.btnCapNhatThongTin);
        recyclerViewAddresses = findViewById(R.id.recyclerViewAddresses);

        // Khởi tạo SharedPreferencesHelper và SettingViewModel
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        SettingViewModelFactory factory = new SettingViewModelFactory(sharedPreferencesHelper);
        settingViewModel = new ViewModelProvider(this, factory).get(SettingViewModel.class);

        addressAdapter = new AddressAdapter(this, addressList, settingViewModel, sharedPreferencesHelper);
        recyclerViewAddresses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAddresses.setAdapter(addressAdapter);
        loadAddresses();

        // Lấy thông tin người dùng từ SharedPreferencesHelper
        String tenNguoiDung = sharedPreferencesHelper.getUsername();
        String email = sharedPreferencesHelper.getEmail();
        AddressModel defaultAddressModel = getDefaultAddress();
        String diaChi = defaultAddressModel != null ? defaultAddressModel.getAddress() : "Chưa có địa chỉ";
        String soDienThoai = defaultAddressModel != null ? defaultAddressModel.getPhone() : "Chưa có số điện thoại";

        // Thiết lập giá trị cho các EditText
        edtTenNguoiDungCapNhat.setText(tenNguoiDung);
        edtSoDienThoaiCapNhat.setText(soDienThoai);
        edtEmailCapNhat.setText(email);
        edtDiaChiCapNhat.setText(diaChi);

        // Xử lý sự kiện click cho nút "Cập nhật thông tin"
        btnCapNhatThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy giá trị từ các EditText
                String name = edtTenNguoiDungCapNhat.getText().toString();
                String phone = edtSoDienThoaiCapNhat.getText().toString();
                String address = edtDiaChiCapNhat.getText().toString();
                String addressType = "nhà riêng"; // Bạn có thể thay đổi loại địa chỉ nếu cần
                boolean isDefault = true;

                // Tạo AddressRequestModel
                AddressRequestModel addressRequestModel = new AddressRequestModel(name, phone, address, addressType, isDefault);

                // Gọi hàm addAddress của SettingViewModel
                String token = sharedPreferencesHelper.getToken();
                settingViewModel.addAddress(token, addressRequestModel);

                // Lắng nghe kết quả từ ViewModel
                settingViewModel.getAddAddressResponse().observe(SettingActivity.this, response -> {
                    if (response.equals("Thêm địa chỉ thành công")) {
                        Log.d("SettingActivity", "Địa chỉ mới đã được thêm thành công:");
                        Log.d("SettingActivity", "Tên: " + name);
                        Log.d("SettingActivity", "Số điện thoại: " + phone);
                        Log.d("SettingActivity", "Địa chỉ: " + address);
                        Log.d("SettingActivity", "Loại địa chỉ: " + addressType);
                        Log.d("SettingActivity", "Mặc định: " + isDefault);


                        Toast.makeText(SettingActivity.this, "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                        loadAddresses();
                    } else {
                        Log.e("SettingActivity", "Thêm địa chỉ thất bại: " + response);
                        // Hiển thị thông báo thất bại
                        Toast.makeText(SettingActivity.this, "Thêm địa chỉ thất bại: " + response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Xử lý sự kiện click cho nút "Back"
        btnBackThongTinChung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadAddresses(); // Gọi lại để làm mới dữ liệu
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

    private void loadAddresses() {
        List<AddressModel> addresses = sharedPreferencesHelper.getAddresses();
        if (addresses != null) {
            // Log thông tin chi tiết của từng đối tượng
            for (AddressModel address : addresses) {
                Log.d("SettingActivity", "Địa chỉ: " + address.toString());
            }
            updateData(addresses);
        } else {
            Log.d("SettingActivity", "Không có dữ liệu địa chỉ.");
        }
    }


    private void updateData(List<AddressModel> newAddressList) {
        addressList.clear();
        addressList.addAll(newAddressList);
        addressAdapter.notifyDataSetChanged();
    }



}
