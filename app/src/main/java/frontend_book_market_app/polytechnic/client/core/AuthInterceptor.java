package frontend_book_market_app.polytechnic.client.core;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final String authToken;

    public AuthInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();

        if (authToken != null && !authToken.isEmpty()) {
            System.out.println("Auth Token: " + authToken);
            builder.header("Authorization", "Bearer " + authToken);
        }

        Request request = builder.build();
        return chain.proceed(request);
    }}
