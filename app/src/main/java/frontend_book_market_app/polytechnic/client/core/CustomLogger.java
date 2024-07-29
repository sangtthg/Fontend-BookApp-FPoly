package frontend_book_market_app.polytechnic.client.core;

import okhttp3.logging.HttpLoggingInterceptor;

public class CustomLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        System.out.println("API LOG: " + message);
    }
}