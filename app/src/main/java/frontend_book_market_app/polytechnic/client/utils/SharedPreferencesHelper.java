package frontend_book_market_app.polytechnic.client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.auth.login.model.AddressModel;
import frontend_book_market_app.polytechnic.client.core.MyApp;
import frontend_book_market_app.polytechnic.client.setting.model.AddressRequestModel;

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
    private static final String ADDRESSES_KEY = "address";
    private static final String ADDRESSES_LIST_KEY = "addresses_list"; // Key cho danh sách địa chỉ
    private static final String ADDRESS_OBJECT_KEY = "address_object"; // Key cho đối tượng địa chỉ
    private final SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
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


    public void saveUserData(int userId, String username, String email, String avatar, String authToken, String resetCode, int userStatus, String role, String token, String defaultAddress) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_AVATAR, avatar);
        editor.putString(KEY_AUTH_TOKEN, authToken);
        editor.putString(KEY_RESET_CODE, resetCode);
        editor.putInt(KEY_USER_STATUS, userStatus);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_DEFAULT_ADDRESS, defaultAddress);
        boolean isSaved = editor.commit(); // use commit() to immediately save the data

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
            Log.d("SharedPreferencesHelper", "Failed to save user data.");
        }
    }


    public void saveAddresses(List<AddressModel> addresses) {
        String json = gson.toJson(addresses);
        editor.putString(ADDRESSES_LIST_KEY, json);
        editor.apply();
    }

    public void saveAddress(AddressRequestModel addressRequestModel) {
        String addressJson = gson.toJson(addressRequestModel);
        editor.putString(ADDRESS_OBJECT_KEY, addressJson);
        editor.apply();
    }

    public AddressRequestModel getAddress() {
        String addressJson = sharedPreferences.getString(ADDRESS_OBJECT_KEY, null);
        return gson.fromJson(addressJson, AddressRequestModel.class);
    }

    public List<AddressModel> getAddresses() {
        String json = sharedPreferences.getString(ADDRESSES_LIST_KEY, null);
        Type type = new TypeToken<List<AddressModel>>() {}.getType();
        if (json == null) {
            return new ArrayList<>(); // Return an empty list if no addresses are found
        }
        return gson.fromJson(json, type);
    }



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


    public void clear() {
        editor.clear();
        editor.apply();
    }
}
