package sangttph30270.fptpoly.fontend_bookapp_fpoly.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.MyApp;

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

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        } else {
            sharedPreferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
    }

    public void saveUserData(int userId, String username, String email, String avatar, String authToken, String resetCode, int userStatus, String role, String token) {
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
        } else {
            Log.d("SharedPreferencesHelper", "Failed to save user data.");
        }
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
}

///--
//SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);

//Ghi
//sharedPreferencesHelper.save("Token người dùng");

//Đọc
//String name = sharedPreferencesHelper.getValue();

