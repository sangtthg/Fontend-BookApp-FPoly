package frontend_book_market_app.polytechnic.client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.core.MyApp;
import frontend_book_market_app.polytechnic.client.profile.model.UpdateAddressModel;
import frontend_book_market_app.polytechnic.client.setting.model.AddressRequestModel;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel; // Correct import

public class SharedPreferencesHelper {
    private static final String PREFS_NAME = "MyPreferences";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_RESET_CODE = "reset_code";
    private static final String KEY_USER_STATUS = "user_status";
    private static final String KEY_ROLE = "role";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_DEFAULT_ADDRESS = "default_address";
    private static final String ADDRESSES_LIST_KEY = "addresses_list"; // Key for the list of addresses
    private static final String ADDRESS_OBJECT_KEY = "address_object"; // Key for the address object

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Gson gson;

    public SharedPreferencesHelper(Context context) {
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        } else {
            sharedPreferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    // Save user data
    public void saveUserData(int userId, String username, String email, String avatar, String authToken, String resetCode, int userStatus, String role, String token, String defaultAddress) {
        editor.putInt(KEY_USER_ID, userId)
                .putString(KEY_USERNAME, username)
                .putString(KEY_EMAIL, email)
                .putString(KEY_AVATAR, avatar)
                .putString(KEY_AUTH_TOKEN, authToken)
                .putString(KEY_RESET_CODE, resetCode)
                .putInt(KEY_USER_STATUS, userStatus)
                .putString(KEY_ROLE, role)
                .putString(KEY_TOKEN, token)
                .putString(KEY_DEFAULT_ADDRESS, defaultAddress);

        boolean isSaved = editor.commit(); // Use commit() to immediately save the data

        // Log saved data
        if (isSaved) {
            Log.d("SharedPreferencesHelper", "User data saved successfully.");
            Log.d("SharedPreferencesHelper", "UserID: " + userId);
            Log.d("SharedPreferencesHelper", "Username: " + username);
            Log.d("SharedPreferencesHelper", "Email: " + email);
            Log.d("SharedPreferencesHelper", "Avatar: " + avatar);
            Log.d("SharedPreferencesHelper", "AuthToken: " + authToken);
            Log.d("SharedPreferencesHelper", "ResetCode: " + resetCode);
            Log.d("SharedPreferencesHelper", "UserStatus: " + userStatus);
            Log.d("SharedPreferencesHelper", "Role: " + role);
            Log.d("SharedPreferencesHelper", "Token: " + token);
            Log.d("SharedPreferencesHelper", "DefaultAddress: " + defaultAddress);
        } else {
            Log.e("SharedPreferencesHelper", "Failed to save user data.");
        }
    }



    public void saveAddress(AddressModel addressModel) {
        List<AddressModel> addresses = getAddresses(); // Get current addresses
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        addresses.add(addressModel); // Add new address
        saveAddresses(addresses); // Save updated list
    }

    public void saveAddresses(List<AddressModel> addresses) {
        String json = gson.toJson(addresses);
        Log.d("SharedPreferencesHelper", "Saving addresses: " + json); // Log the JSON string
        editor.putString(ADDRESSES_LIST_KEY, json).apply();
    }

    // Get a single address
    public AddressRequestModel getAddress() {
        String addressJson = sharedPreferences.getString(ADDRESS_OBJECT_KEY, null);
        return gson.fromJson(addressJson, AddressRequestModel.class);
    }

    public List<AddressModel> getAddresses() {
        String json = sharedPreferences.getString(ADDRESSES_LIST_KEY, null);
        Log.d("SharedPreferencesHelper", "Retrieved JSON: " + json); // Log the retrieved JSON string
        Type type = new TypeToken<List<AddressModel>>() {}.getType();
        if (json == null) {
            return new ArrayList<>(); // Return an empty list if no addresses are found
        }
        return gson.fromJson(json, type);
    }

    public void updateAvatar(String newAvatarUrl) {
        editor.putString(KEY_AVATAR, newAvatarUrl);
    }

    public void saveAddressUpdate(UpdateAddressModel updateAddressModel) {
        // Tạo đối tượng AddressModel từ UpdateAddressModel
        AddressModel addressModel = new AddressModel(
                updateAddressModel.getId(),  // ID từ UpdateAddressModel
                updateAddressModel.getName(),
                updateAddressModel.getPhone(),
                updateAddressModel.getAddress(),
                updateAddressModel.getAddress_type(),
                updateAddressModel.isIs_default()
        );

        // Lấy danh sách địa chỉ hiện tại từ SharedPreferences
        List<AddressModel> addresses = getAddresses();
        if (addresses == null) {
            addresses = new ArrayList<>();
        }

        // Cập nhật hoặc thêm địa chỉ vào danh sách
        boolean addressUpdated = false;
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).getAddress_id() == addressModel.getAddress_id()) {
                addresses.set(i, addressModel); // Cập nhật địa chỉ đã tồn tại
                addressUpdated = true;
                break;
            }
        }

        if (!addressUpdated) {
            addresses.add(addressModel); // Thêm địa chỉ mới nếu không có địa chỉ với id tương ứng
        }

        // Lưu danh sách địa chỉ đã cập nhật vào SharedPreferences
        saveAddresses(addresses);

        // Ghi log danh sách địa chỉ đã lưu để kiểm tra
        List<AddressModel> savedAddresses = getAddresses();
        Log.d("SharedPreferencesHelper", "Saved addresses: " + savedAddresses);

        // Ghi log thông tin địa chỉ đã cập nhật
        Log.d("SharedPreferencesHelper", "Address updated:");
        Log.d("SharedPreferencesHelper", "ID: " + addressModel.getAddress_id());
        Log.d("SharedPreferencesHelper", "Name: " + addressModel.getName());
        Log.d("SharedPreferencesHelper", "Phone: " + addressModel.getPhone());
        Log.d("SharedPreferencesHelper", "Address: " + addressModel.getAddress());
        Log.d("SharedPreferencesHelper", "Address Type: " + addressModel.getAddress_type());
        Log.d("SharedPreferencesHelper", "Is Default: " + addressModel.isIs_default());
    }


    // Get user data methods
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getAvatar() {
        return sharedPreferences.getString(KEY_AVATAR, "");
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, "");
    }

    public String getResetCode() {
        return sharedPreferences.getString(KEY_RESET_CODE, "");
    }

    public int getUserStatus() {
        return sharedPreferences.getInt(KEY_USER_STATUS, -1);
    }

    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, "");
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, "");
    }

    public String getDefaultAddress() {
        return sharedPreferences.getString(KEY_DEFAULT_ADDRESS, "");
    }

    // Clear all stored preferences
    public void clear() {
        editor.clear().apply();
    }
    public void saveProfileImage(String imageUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profile_image_url", imageUrl);
        editor.apply();
    }

    public String getProfileImage() {
        return sharedPreferences.getString("profile_image_url", "");
    }

}
