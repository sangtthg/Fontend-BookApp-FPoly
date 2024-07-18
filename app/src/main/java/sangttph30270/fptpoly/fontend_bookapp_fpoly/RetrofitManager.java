package sangttph30270.fptpoly.fontend_bookapp_fpoly;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static Retrofit retrofit = null;

    //-- T đại diện cho kiểu dữ liệu không xác định để truyền vào
    public static <T> T createService(Class<T> serviceClass, String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(serviceClass);
    }
}

