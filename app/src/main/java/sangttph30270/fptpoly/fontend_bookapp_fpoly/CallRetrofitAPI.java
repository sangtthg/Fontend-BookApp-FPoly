package sangttph30270.fptpoly.fontend_bookapp_fpoly;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallRetrofitAPI {
    public static final String BASE_URL = "https://book-manager-phi.vercel.app/";
    private static Retrofit retrofit;

    public static Retrofit getCallRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder() //
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
