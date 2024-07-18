package sangttph30270.fptpoly.fontend_bookapp_fpoly;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static Retrofit retrofit = null;

    public static <T> T createService(Class<T> serviceClass, String baseUrl, String token) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (token != null && !token.isEmpty()) {
            httpClientBuilder.addInterceptor(new AuthInterceptor(token));
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new CustomLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);

        OkHttpClient okHttpClient = httpClientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(serviceClass);
    }
}

