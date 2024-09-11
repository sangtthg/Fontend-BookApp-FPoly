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
import frontend_book_market_app.polytechnic.client.profile.model.District;  // Make sure you have this model

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.ViewHolder> {

    private List<District> districts;
    private OnItemClickListener onItemClickListener;
    private List<District> originalDistrictList;  // Danh sách gốc cho chức năng tìm kiếm

    public DistrictAdapter(List<District> districts, OnItemClickListener onItemClickListener) {
        this.districts = districts != null ? districts : new ArrayList<>();
        this.originalDistrictList = new ArrayList<>(this.districts);  // Lưu trữ danh sách gốc
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_district, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        District district = districts.get(position);
        holder.textViewItem2.setText(district.getName());

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(district);
            }
        });
    }

    @Override
    public int getItemCount() {
        return districts.size();
    }

    // Method to update the data in the adapter
    public void updateDistricts(List<District> newDistricts) {
        if (originalDistrictList == null || originalDistrictList.isEmpty()) {
            originalDistrictList = new ArrayList<>(newDistricts); // Cập nhật danh sách gốc nếu chưa có
        }
        this.districts = newDistricts != null ? newDistricts : new ArrayList<>();
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem2;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewItem2 = itemView.findViewById(R.id.textViewItem2);
        }
    }

    // Define an interface for click handling
    public interface OnItemClickListener {
        void onItemClick(District district);
    }
    // Method for filtering the list of districts
    public void filter(String text) {
        List<District> filteredList = new ArrayList<>();
        for (District district : originalDistrictList) {
            if (district.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(district);
            }
        }
        updateDistricts(filteredList);  // Cập nhật danh sách hiển thị
    }
}
