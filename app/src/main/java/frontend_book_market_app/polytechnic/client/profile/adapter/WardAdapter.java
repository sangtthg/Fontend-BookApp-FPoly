package frontend_book_market_app.polytechnic.client.profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.profile.model.Ward;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.ViewHolder> {

    private List<Ward> wards;
    private OnItemClickListener onItemClickListener;
    private List<Ward> originalWardList;  // Danh sách gốc để tìm kiếm

    public WardAdapter(List<Ward> wards, OnItemClickListener onItemClickListener) {
        this.wards = wards != null ? wards : new ArrayList<>();
        this.originalWardList = new ArrayList<>(this.wards);  // Lưu trữ danh sách gốc
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ward, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ward ward = wards.get(position);
        holder.textViewWardName.setText(ward.getName());

        // Xử lý khi click vào item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(ward);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wards.size();
    }

    // Phương thức cập nhật dữ liệu trong adapter
    public void updateWards(List<Ward> newWards) {
        if (originalWardList == null || originalWardList.isEmpty()) {
            originalWardList = new ArrayList<>(newWards);  // Cập nhật danh sách gốc nếu chưa có
        }
        this.wards = newWards != null ? newWards : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWardName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewWardName = itemView.findViewById(R.id.textViewWardName);
        }
    }

    // Định nghĩa interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(Ward ward);
    }

    // Phương thức để lọc danh sách các phường theo từ khóa tìm kiếm
    public void filter(String text) {
        List<Ward> filteredList = new ArrayList<>();
        for (Ward ward : originalWardList) {
            if (ward.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(ward);
            }
        }
        updateWards(filteredList);  // Cập nhật danh sách hiển thị
    }
}
