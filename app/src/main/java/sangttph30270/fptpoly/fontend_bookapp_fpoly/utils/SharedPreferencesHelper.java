package sangttph30270.fptpoly.fontend_bookapp_fpoly.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String PREFS_NAME = "MyPreferences";
    private static final String KEY_NAME = "name";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void save(String name) {
        sharedPreferences.edit().putString(KEY_NAME, name).apply();
    }

    public String getValue() {
        return sharedPreferences.getString(KEY_NAME, "");
    }
}

///--
//SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);

//Ghi
//sharedPreferencesHelper.save("Token người dùng");

//Đọc
//String name = sharedPreferencesHelper.getValue();

