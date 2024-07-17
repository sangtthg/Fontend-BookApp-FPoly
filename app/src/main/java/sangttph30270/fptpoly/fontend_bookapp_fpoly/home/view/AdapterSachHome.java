package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;

public class AdapterSachHome extends RecyclerView.Adapter<AdapterSachHome.SachHomeViewHolder> {

    private  List<HomeBookModel> bookModelList;
    private final OnItemClickListener listener;

    public AdapterSachHome(List<HomeBookModel> bookModelList, OnItemClickListener listener) {
        this.bookModelList = bookModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SachHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_home_sach_ban_chay, parent, false);
        return new SachHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachHomeViewHolder holder, int position) {
        HomeBookModel bookModel = bookModelList.get(position);
        holder.tvTenSach.setText(bookModel.getTitle());
        holder.tvGiaSach.setText(CurrencyFormatter.toVND(bookModel.getNewPrice()));
        holder.tvSachBanChay.setVisibility(View.INVISIBLE);
        Glide.with(holder.itemView.getContext())
                .load(bookModel.getBookAvatar())
                .centerCrop()
                .placeholder(R.drawable.home)
                .into(holder.imgAnhBia);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return bookModelList.size();
    }

    public static class SachHomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach;
        TextView tvGiaSach;
        TextView tvSachBanChay;
        ImageView imgAnhBia;
        public SachHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tv_tenSach);
            tvGiaSach = itemView.findViewById(R.id.tv_giaSach);
            imgAnhBia = itemView.findViewById(R.id.imageView_anh_bia);
            tvSachBanChay = itemView.findViewById(R.id.tv_sachBanChay);
        }
    }

    public void updateData(List<HomeBookModel> newData) {
        this.bookModelList = newData;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
