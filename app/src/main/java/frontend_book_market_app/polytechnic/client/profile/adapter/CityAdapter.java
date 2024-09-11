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
import frontend_book_market_app.polytechnic.client.profile.model.Province;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<Province> provinces;
    private OnItemClickListener onItemClickListener;
    private List<Province> originalProvinceList;

    public CityAdapter(List<Province> provinces, OnItemClickListener onItemClickListener) {
        this.provinces = provinces != null ? provinces : new ArrayList<>();
        this.originalProvinceList = new ArrayList<>(this.provinces); // Lưu danh sách gốc
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Province province = provinces.get(position);
        holder.textViewItem.setText(province.getName());

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(province);
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinces.size();
    }


    public void updateProvinces(List<Province> newProvinces) {
        if (originalProvinceList == null || originalProvinceList.isEmpty()) {
            originalProvinceList = new ArrayList<>(newProvinces); // Chỉ gán khi originalProvinceList trống
        }
        this.provinces = newProvinces != null ? newProvinces : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.textViewItem);
        }
    }

    // Define an interface for click handling
    public interface OnItemClickListener {
        void onItemClick(Province province);
    }

    public void filter(String text) {
        List<Province> filteredList = new ArrayList<>();
        for (Province province : originalProvinceList) {
            if (province.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(province);
            }
        }
        updateProvinces(filteredList);  // Cập nhật danh sách hiển thị
    }


}
