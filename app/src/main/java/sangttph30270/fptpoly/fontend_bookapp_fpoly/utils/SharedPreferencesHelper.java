package sangttph30270.fptpoly.fontend_bookapp_fpoly.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String PREFS_NAME = "MyPreferences";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void save(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getValue(String key) {
        return sharedPreferences.getString(key, "");
    }
}


//public class Test extends AppCompatActivity {
//    private SharedPreferencesHelper sharedPreferencesHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test);
//
//        sharedPreferencesHelper = new SharedPreferencesHelper(this);
//
//        // Lưu
//        sharedPreferencesHelper.save("name", "John Doe");
//        sharedPreferencesHelper.save("age", "30");
//
//        // Truy xuất
//        String name = sharedPreferencesHelper.getValue("name");
//        String age = sharedPreferencesHelper.getValue("age");
//
//
//        Log.d("TestActivity", "Name: " + name);
//        Log.d("TestActivity", "Age: " + age);
//    }
//}


