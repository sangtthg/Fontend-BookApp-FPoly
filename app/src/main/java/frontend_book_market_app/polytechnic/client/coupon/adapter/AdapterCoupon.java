package frontend_book_market_app.polytechnic.client.coupon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.coupon.model.CouponRequestModel;
import frontend_book_market_app.polytechnic.client.coupon.view.ChiTietCouponActivity;

public class AdapterCoupon extends RecyclerView.Adapter<AdapterCoupon.CouponViewHolder> {

    private static final String TAG = "AdapterCoupon";
    private List<CouponRequestModel> couponList;
    private int selectedPosition = -1; // Keep track of selected item position
    private int selectedCouponId = -1;
    private String selectedCouponCode = "";
    private double selectedCouponDiscount = 0;
    public static final int REQUEST_CODE_COUPON = 100; // Bạn có thể sử dụng một giá trị phù hợp
    private SharedPreferences sharedPreferences;
    private Context context;


    public AdapterCoupon(Context context, List<CouponRequestModel> couponList) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.couponList = couponList;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coupon, parent, false);
        return new CouponViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CouponRequestModel coupon = couponList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String startDate = coupon.getValidFrom() != null ? sdf.format(coupon.getValidFrom()) : null;
        String endDate = coupon.getValidTo() != null ? sdf.format(coupon.getValidTo()) : null;

        Log.d(TAG, "ID: " + coupon.getId());
        Log.d(TAG, "Code: " + coupon.getCode());
        Log.d(TAG, "Description: " + coupon.getDescription());
        Log.d(TAG, "Quantity: " + coupon.getQuantity());
        Log.d(TAG, "Start Date: " + coupon.getValidFrom());
        Log.d(TAG, "End Date: " + coupon.getValidTo());
        Log.d(TAG, "Discount: " + coupon.getDiscountAmount());
        Log.d(TAG, "Status: " + coupon.getStatus());
        Log.d(TAG, "Actions: [Edit/Delete]");

        holder.txtMoTaCoupon.setText(coupon.getDescription());
        holder.voucherTitle.setText(coupon.getCode());
        holder.voucherCheckbox.setChecked(position == selectedPosition);
        holder.voucherCheckbox.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (position != selectedPosition) {
                notifyItemChanged(selectedPosition);
                selectedPosition = position;
                notifyItemChanged(selectedPosition);
                selectedCouponId = coupon.getId();
                selectedCouponCode = coupon.getCode();
                selectedCouponDiscount = coupon.getDiscountAmount();
                //backup
//                editor.putInt("selectedCouponId", selectedCouponId);
//                editor.putString("selectedCouponCode", selectedCouponCode);
//                editor.putFloat("selectedCouponDiscount", (float) selectedCouponDiscount);
//                editor.apply();
            } else {
                selectedPosition = -1;
                selectedCouponId = -1;
                selectedCouponCode = "";
                selectedCouponDiscount = 0;
                // Remove selected coupon details
                editor.remove("selectedCouponId");
                editor.remove("selectedCouponCode");
                editor.remove("selectedCouponDiscount");
                editor.apply();
            }
        });


        int maxQuantity = 100;
        int progress = coupon.getQuantity();
        holder.voucherProgressBar.setMax(maxQuantity);
        holder.voucherProgressBar.setProgress(progress);

        int percentage = (int) ((float) progress / maxQuantity * 100);
        holder.progressText.setText(percentage + "%");

        holder.txtDieuKienCoupon.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChiTietCouponActivity.class);
            intent.putExtra("couponId", coupon.getId());
            intent.putExtra("couponCode", coupon.getCode());
            intent.putExtra("couponDescription", coupon.getDescription());
            intent.putExtra("couponsoluong", coupon.getQuantity());
            intent.putExtra("couponStartDate", startDate);
            intent.putExtra("couponEndDate", endDate);
            intent.putExtra("couponDiscount", coupon.getDiscountAmount());
            v.getContext().startActivity(intent);
        });
        if (progress == 0) {
            holder.btnDaHetMa.setVisibility(View.VISIBLE);
            holder.voucher_image.setImageResource(R.drawable.couponmagiamgia);
            holder.voucherCheckbox.setVisibility(View.GONE);
            holder.txtMoTaCoupon.setTextColor(R.color.texthethan);
            holder.voucherTitle.setTextColor(R.color.texthethan);
            holder.txtDieuKienCoupon.setTextColor(R.color.texthethan);
            holder.progressText.setTextColor(R.color.texthethan);
            holder.voucherProgressBar.setBackgroundColor(R.color.texthethan);
            holder.magiamgiaconlai.setTextColor(R.color.texthethan);

        }


    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public void setCouponList(List<CouponRequestModel> couponList) {
        this.couponList = couponList;
        notifyDataSetChanged();
    }

    public void saveVoucher() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("selectedCouponId", selectedCouponId);
        editor.putString("selectedCouponCode", selectedCouponCode);
        editor.putFloat("selectedCouponDiscount", (float) selectedCouponDiscount);
        editor.apply();
    }


    static class CouponViewHolder extends RecyclerView.ViewHolder {
        TextView voucherTitle, txtMoTaCoupon, txtDieuKienCoupon, magiamgiaconlai;
        ProgressBar voucherProgressBar;
        TextView progressText;
        MaterialCheckBox voucherCheckbox;
        ImageView voucher_image;
        Button btnDaHetMa;

        @SuppressLint("WrongViewCast")
        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            voucherCheckbox = itemView.findViewById(R.id.voucher_checkbox);
            txtDieuKienCoupon = itemView.findViewById(R.id.txtDieuKienCoupon);
            txtMoTaCoupon = itemView.findViewById(R.id.txtMoTaCoupon);
            voucherTitle = itemView.findViewById(R.id.voucher_title);
            voucherProgressBar = itemView.findViewById(R.id.voucher_progress_bar);
            progressText = itemView.findViewById(R.id.progress_text);
            voucher_image = itemView.findViewById(R.id.voucher_image);
            magiamgiaconlai = itemView.findViewById(R.id.magiamgiaconlai);
            btnDaHetMa = itemView.findViewById(R.id.btnDaHetMa);
        }
    }

    public void showToast1() {
        Toast.makeText(context, "ID: " + selectedCouponCode, Toast.LENGTH_SHORT).show();
    }


    public int getSelectedCouponId() {
        return selectedCouponId;
    }

    public String getSelectedCouponCode() {
        return selectedCouponCode;
    }

    public double getSelectedCouponDiscount() {
        return selectedCouponDiscount;
    }

    public void setSelectedCouponId(int couponId) {
        for (int i = 0; i < couponList.size(); i++) {
            if (couponList.get(i).getId() == couponId) {
                selectedPosition = i;
                notifyItemChanged(selectedPosition);
                break;
            }
        }
    }


}
