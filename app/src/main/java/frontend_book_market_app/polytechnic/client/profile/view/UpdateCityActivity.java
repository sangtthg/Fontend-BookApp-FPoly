package frontend_book_market_app.polytechnic.client.profile.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class UpdateCityActivity extends AppCompatActivity implements CityAdapter.OnItemClickListener, DistrictAdapter.OnItemClickListener, WardAdapter.OnItemClickListener {

    private Toolbar toolbar;
    private ImageView btnBackUpdateCity;
    private EditText edtProvinceUpdate, edtDistrictUpdate, edtWardUpdate, edtSearchProvinceUpdate;
    private RecyclerView recyclerViewCityUpdate;

    private CityAdapter cityAdapter;
    private DistrictAdapter districtAdapter;
    private WardAdapter wardAdapter;
    private List<Province> provinceList;
    private List<District> districtList;
    private List<Ward> wardList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_update_city);

        // Initialize views
        toolbar = findViewById(R.id.toolbarUpdateCity);
        btnBackUpdateCity = findViewById(R.id.btnBackUpdateCity);
        edtProvinceUpdate = findViewById(R.id.edtProvinceUpdate);
        edtDistrictUpdate = findViewById(R.id.edtDistrictUpdate);
        edtWardUpdate = findViewById(R.id.edtWardUpdate);
        edtSearchProvinceUpdate = findViewById(R.id.edtSearchProvinceUpdate);

        recyclerViewCityUpdate = findViewById(R.id.recyclerViewCityUpdate);
        recyclerViewCityUpdate.setLayoutManager(new LinearLayoutManager(this));
        cityAdapter = new CityAdapter(new ArrayList<>(), this);
        districtAdapter = new DistrictAdapter(new ArrayList<>(), this);
        wardAdapter = new WardAdapter(new ArrayList<>(), this);
        recyclerViewCityUpdate.setAdapter(cityAdapter);
        recyclerViewCityUpdate.setVisibility(View.GONE);

        btnBackUpdateCity.setOnClickListener(v -> finish());

        // Load initial province data
        loadProvinces();

        // Set up text change listener for search
        edtSearchProvinceUpdate.addTextChangedListener(new TextWatcher() {
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

        // Set up click listeners
        edtProvinceUpdate.setOnClickListener(v -> loadProvinces());
        edtDistrictUpdate.setOnClickListener(v -> {
            String selectedProvince = edtProvinceUpdate.getText().toString();
            if (!selectedProvince.isEmpty()) {
                loadDistricts(selectedProvince);
            } else {
                Toast.makeText(this, "Please select a province first", Toast.LENGTH_SHORT).show();
            }
        });
        edtWardUpdate.setOnClickListener(v -> {
            String selectedDistrict = edtDistrictUpdate.getText().toString();
            if (!selectedDistrict.isEmpty()) {
                loadWards(selectedDistrict);
            } else {
                Toast.makeText(this, "Please select a district first", Toast.LENGTH_SHORT).show();
            }
        });
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
            Type listType = new TypeToken<List<Province>>() {}.getType();
            provinceList = gson.fromJson(json, listType);
            recyclerViewCityUpdate.setAdapter(cityAdapter);

            cityAdapter.updateProvinces(provinceList);
            recyclerViewCityUpdate.setVisibility(View.VISIBLE);

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
            Type addressDataType = new TypeToken<List<Province>>() {}.getType();
            List<Province> provinces = gson.fromJson(json, addressDataType);

            districtList = new ArrayList<>();
            for (Province province : provinces) {
                if (province.getName().equals(selectedProvince)) {
                    districtList = province.getDistricts();
                    break;
                }
            }


            recyclerViewCityUpdate.setAdapter(districtAdapter);
            districtAdapter.updateDistricts(districtList);
            recyclerViewCityUpdate.setVisibility(View.VISIBLE);

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
            Type addressDataType = new TypeToken<List<Province>>() {}.getType();
            List<Province> provinces = gson.fromJson(json, addressDataType);

            for (Province province : provinces) {
                for (District district : province.getDistricts()) {
                    if (district.getName().equals(selectedDistrict)) {
                        wardList = district.getWards();
                        break;
                    }
                }
            }


            recyclerViewCityUpdate.setAdapter(wardAdapter);
            wardAdapter.updateWards(wardList);
            recyclerViewCityUpdate.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(Province province) {
        edtProvinceUpdate.setText(province.getName());
        recyclerViewCityUpdate.setVisibility(View.GONE);
        edtDistrictUpdate.setText("");
        edtWardUpdate.setText("");
        edtSearchProvinceUpdate.setText("");
        String selectedProvince = edtProvinceUpdate.getText().toString();
        if (!selectedProvince.isEmpty()) {
            loadDistricts(selectedProvince);
        } else {
            Toast.makeText(this, "Please select a province first", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(District district) {
        edtDistrictUpdate.setText(district.getName());
        recyclerViewCityUpdate.setVisibility(View.GONE);
        edtWardUpdate.setText("");
        edtSearchProvinceUpdate.setText("");
        String selectedDistrict = edtDistrictUpdate.getText().toString();
        if (!selectedDistrict.isEmpty()) {
            loadWards(selectedDistrict);
        } else {
            Toast.makeText(this, "Please select a district first", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Ward ward) {
        edtWardUpdate.setText(ward.getName());
        recyclerViewCityUpdate.setVisibility(View.GONE);
        edtSearchProvinceUpdate.setText("");

        // Return the selected address details to the previous activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("PROVINCE_NAME", edtProvinceUpdate.getText().toString());
        resultIntent.putExtra("DISTRICT_NAME", edtDistrictUpdate.getText().toString());
        resultIntent.putExtra("WARD_NAME", ward.getName());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
