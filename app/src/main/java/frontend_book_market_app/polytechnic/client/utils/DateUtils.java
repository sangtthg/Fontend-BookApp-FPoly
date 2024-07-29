package frontend_book_market_app.polytechnic.client.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String formatDate(String dateString, String outputFormat) {
        SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat displayDateFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
        try {
            Date date = apiDateFormat.parse(dateString);
            return displayDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }
}