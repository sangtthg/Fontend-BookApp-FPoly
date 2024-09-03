package frontend_book_market_app.polytechnic.client.coupon.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import frontend_book_market_app.polytechnic.client.R;

public class ChiTietCouponActivity extends AppCompatActivity {
    private TextView voucher_title_chitiet, txtMoTaChiTietCoupon, txtHetHan, txtHanSuDungCoupon;
    private ImageView btnBackMaGiamGia; // Sử dụng ImageView cho nút quay lại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_coupon);

        voucher_title_chitiet = findViewById(R.id.voucher_title_chitiet);
        txtMoTaChiTietCoupon = findViewById(R.id.txtMoTaChiTietCoupon);
        txtHetHan = findViewById(R.id.txtHetHan);
        txtHanSuDungCoupon = findViewById(R.id.txtHanSuDungCoupon);
        btnBackMaGiamGia = findViewById(R.id.btnBackMaGiamGia);

        int couponId = getIntent().getIntExtra("couponId", -1);
        String couponCode = getIntent().getStringExtra("couponCode");
        String couponDescription = getIntent().getStringExtra("couponDescription");
        String couponStartDate = getIntent().getStringExtra("couponStartDate");
        String couponEndDate = getIntent().getStringExtra("couponEndDate");
        double couponDiscount = getIntent().getDoubleExtra("couponDiscount", 0.0);

        // Log the data
        Log.d(TAG, "Coupon ID: " + couponId);
        Log.d(TAG, "Coupon Code: " + couponCode);
        Log.d(TAG, "Coupon Description: " + couponDescription);
        Log.d(TAG, "Coupon Start Date: " + couponStartDate);
        Log.d(TAG, "Coupon End Date: " + couponEndDate);
        Log.d(TAG, "Coupon Discount: " + couponDiscount);

        voucher_title_chitiet.setText(couponCode);
        txtMoTaChiTietCoupon.setText(couponDescription);

        // Định dạng ngày giờ
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        // Thêm giờ mặc định cho ngày bắt đầu và ngày kết thúc
        String startDateTimeStr = couponStartDate + " 00:00:00";
        String endDateTimeStr = couponEndDate + " 23:59:59";

        try {
            // Phân tích chuỗi ngày giờ
            Date startDateTime = dateTimeFormat.parse(startDateTimeStr);
            Date endDateTime = dateTimeFormat.parse(endDateTimeStr);
            Date currentDate = new Date(); // Thời gian hiện tại

            if (startDateTime != null && endDateTime != null) {
                txtHanSuDungCoupon.setText(dateTimeFormat.format(startDateTime) + " - " + dateTimeFormat.format(endDateTime));

                // Tính toán sự chênh lệch theo mili giây
                long differenceInMillis = endDateTime.getTime() - currentDate.getTime();

                // Chuyển đổi mili giây thành ngày, giờ, phút
                long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
                long hoursDifference = TimeUnit.MILLISECONDS.toHours(differenceInMillis) % 24;
                long minutesDifference = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis) % 60;
                Log.d("DateDifference", "Days: " + daysDifference + ", Hours: " + hoursDifference + ", Minutes: " + minutesDifference);

                // Hiển thị kết quả
                if (differenceInMillis > 0) {
                    txtHetHan.setText("Hết hạn trong: " + daysDifference + " ngày, " + hoursDifference + " giờ, và " + minutesDifference + " phút");
                } else {
                    txtHetHan.setText("Mã giảm giá đã hết hạn.");
                }
            } else {
                txtHetHan.setText("Không thể tính toán hạn sử dụng");
            }

        } catch (ParseException e) {
            Log.e(TAG, "ParseException: " + e.getMessage());
            txtHetHan.setText("Không thể tính toán hạn sử dụng");
        }

        btnBackMaGiamGia.setOnClickListener(v -> {
            Intent intent = new Intent(ChiTietCouponActivity.this, CouponActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
