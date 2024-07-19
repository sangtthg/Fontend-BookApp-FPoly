package sangttph30270.fptpoly.fontend_bookapp_fpoly;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static Retrofit retrofit = null;

    public static <T> T createService(Class<T> serviceClass, String baseUrl, String token) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywicm9sZSI6InVzZXIiLCJjcmVhdGVkX2F0IjoxNzIxMzE1MTQyNzIyLCJpYXQiOjE3MjEzMTUxNDJ9.gWF3paeaGIhuBshIix2wKFwU-iX7OKxRKTvAjkt8L_k";
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

