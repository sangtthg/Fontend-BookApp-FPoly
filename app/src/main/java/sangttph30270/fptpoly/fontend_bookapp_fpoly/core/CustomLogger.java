package sangttph30270.fptpoly.fontend_bookapp_fpoly.core;

import okhttp3.logging.HttpLoggingInterceptor;

public class CustomLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        System.out.println("API LOG: " + message);
    }
}