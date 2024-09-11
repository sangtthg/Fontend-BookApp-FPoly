package frontend_book_market_app.polytechnic.client.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.profile.adapter.CityAdapter;
import frontend_book_market_app.polytechnic.client.profile.adapter.DistrictAdapter;
import frontend_book_market_app.polytechnic.client.profile.adapter.WardAdapter;
import frontend_book_market_app.polytechnic.client.profile.model.District;
import frontend_book_market_app.polytechnic.client.profile.model.Province;
import frontend_book_market_app.polytechnic.client.profile.model.Ward;

public class AddCityActivity extends AppCompatActivity implements CityAdapter.OnItemClickListener, DistrictAdapter.OnItemClickListener, WardAdapter.OnItemClickListener {

    private ImageView btnBackAddCity;
    private TextView txtProvinceLabel, txtDistrictLabel, txtWardLabel;
    private EditText edtProvince, edtDistrict, edtWard, edtSearchProvince;
    private RecyclerView recyclerViewCity;

    private CityAdapter cityAdapter;
    private DistrictAdapter districtAdapter;
    private WardAdapter wardAdapter;
    private List<Province> provinceList;
    private List<District> districtList;
    private List<Ward> wardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        btnBackAddCity = findViewById(R.id.btnBackAddCity);
        txtProvinceLabel = findViewById(R.id.txtProvinceLabel);
        edtProvince = findViewById(R.id.edtProvince);
        txtDistrictLabel = findViewById(R.id.txtDistrictLabel);
        edtDistrict = findViewById(R.id.edtDistrict);
        txtWardLabel = findViewById(R.id.txtWardLabel);
        edtWard = findViewById(R.id.edtWard);
        edtSearchProvince = findViewById(R.id.edtSearchProvince); // Search bar for province

        recyclerViewCity = findViewById(R.id.recyclerViewCity);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this));
        cityAdapter = new CityAdapter(new ArrayList<>(), this);
        districtAdapter = new DistrictAdapter(new ArrayList<>(), this);
        wardAdapter = new WardAdapter(new ArrayList<>(), this);
        recyclerViewCity.setAdapter(cityAdapter);
        recyclerViewCity.setVisibility(View.GONE);
        loadProvinces();


        edtSearchProvince.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cityAdapter.filter(s.toString());
                districtAdapter.filter(s.toString());
                wardAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        edtProvince.setOnClickListener(v -> {
            loadProvinces();
        });

        edtDistrict.setOnClickListener(v -> {
            String selectedProvince = edtProvince.getText().toString();
            if (!selectedProvince.isEmpty()) {
                loadDistricts(selectedProvince);
            } else {
                Toast.makeText(this, "Please select a province first", Toast.LENGTH_SHORT).show();
            }
        });

        edtWard.setOnClickListener(v -> {
            String selectedDistrict = edtDistrict.getText().toString();
            if (!selectedDistrict.isEmpty()) {
                loadWards(selectedDistrict);
            } else {
                Toast.makeText(this, "Please select a district first", Toast.LENGTH_SHORT).show();
            }
        });
        btnBackAddCity.setOnClickListener(v -> finish());
    }

    private void loadProvinces() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.vietnam_address);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Province>>() {
            }.getType();
            provinceList = gson.fromJson(json, listType);
            recyclerViewCity.setAdapter(cityAdapter);
            cityAdapter.updateProvinces(provinceList);
            recyclerViewCity.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDistricts(String selectedProvince) {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.vietnam_address);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type addressDataType = new TypeToken<List<Province>>() {
            }.getType();
            List<Province> provinces = gson.fromJson(json, addressDataType);

            districtList = new ArrayList<>();
            for (Province province : provinces) {
                if (province.getName().equals(selectedProvince)) {
                    districtList = province.getDistricts();
                    break;
                }
            }

            recyclerViewCity.setAdapter(districtAdapter);
            districtAdapter.updateDistricts(districtList);
            recyclerViewCity.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadWards(String selectedDistrict) {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.vietnam_address);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type addressDataType = new TypeToken<List<Province>>() {
            }.getType();
            List<Province> provinces = gson.fromJson(json, addressDataType);

            for (Province province : provinces) {
                for (District district : province.getDistricts()) {
                    if (district.getName().equals(selectedDistrict)) {
                        wardList = district.getWards();
                        break;
                    }
                }
            }
            recyclerViewCity.setAdapter(wardAdapter);
            wardAdapter.updateWards(wardList);
            recyclerViewCity.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(Province province) {
        edtProvince.setText(province.getName());
        recyclerViewCity.setVisibility(View.GONE);
        edtDistrict.setText("");
        edtWard.setText("");
        edtSearchProvince.setText("");
        String selectedProvince = edtProvince.getText().toString();
        if (!selectedProvince.isEmpty()) {
            loadDistricts(selectedProvince);
        } else {
            Toast.makeText(this, "Please select a province first", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(District district) {
        edtDistrict.setText(district.getName());
        recyclerViewCity.setVisibility(View.GONE);
        edtWard.setText("");
        edtSearchProvince.setText("");
        String selectedDistrict = edtDistrict.getText().toString();
        if (!selectedDistrict.isEmpty()) {
            loadWards(selectedDistrict);
        } else {
            Toast.makeText(this, "Please select a district first", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClick(Ward ward) {
        edtWard.setText(ward.getName());
        recyclerViewCity.setVisibility(View.GONE);
        edtSearchProvince.setText("");  // Xóa nội dung của ô tìm kiếm tỉnh
        String provinceName = edtProvince.getText().toString();
        String districtName = edtDistrict.getText().toString();
        String wardName = ward.getName();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("PROVINCE_NAME", provinceName);
        resultIntent.putExtra("DISTRICT_NAME", districtName);
        resultIntent.putExtra("WARD_NAME", wardName);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
