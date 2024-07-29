package frontend_book_market_app.polytechnic.client.utils;

import android.graphics.Paint;
import android.widget.TextView;

public class TextV {
    public static void setStrikeThrough(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}