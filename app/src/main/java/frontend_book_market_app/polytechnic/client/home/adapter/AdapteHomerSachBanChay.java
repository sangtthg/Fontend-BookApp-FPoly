package frontend_book_market_app.polytechnic.client.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookModel;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;

public class AdapteHomerSachBanChay extends RecyclerView.Adapter<AdapteHomerSachBanChay.SachBanChayViewHolder> {

    private List<HomeBookModel> bookModelList;
    private final OnItemClickListener listener;

    public AdapteHomerSachBanChay(List<HomeBookModel> bookModelList, OnItemClickListener listener) {
        this.bookModelList = bookModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapteHomerSachBanChay.SachBanChayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_home_sach_ban_chay, parent, false);
        return new SachBanChayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapteHomerSachBanChay.SachBanChayViewHolder holder, int position) {
        HomeBookModel bookModel = bookModelList.get(position);
        holder.tvTenSach.setText(bookModel.getTitle());
        holder.tvGiaSach.setText(CurrencyFormatter.toVND(bookModel.getNewPrice()));
        holder.tvDaBan.setText(String.valueOf(bookModel.getPurchaseCount()));
        holder.tvDanhGia.setText(String.valueOf(bookModel.getRateBook()));

        if (bookModel.getDiscountPercentage() > 0){
            holder.tvPhanTramGiamHome.setText(MessageFormat.format("-{0}%", bookModel.getDiscountPercentage()));
        } else{
            holder.tvPhanTramGiamHome.setVisibility(View.GONE);
        }


        if (bookModel.getRateBook() < 1){
            holder.linear_rating_2.setVisibility(View.GONE);
        } else{
            holder.linear_rating_2.setVisibility(View.VISIBLE);
        }

        Glide.with(holder.itemView.getContext())
                .load(bookModel.getBookAvatar())
                .placeholder(R.drawable.loading_book)
                .fitCenter()
                .into(holder.imgAnhBia);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(bookModel.getBookId()));
    }

    @Override
    public int getItemCount() {
        return bookModelList.size();
    }

    public static class SachBanChayViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach;
        TextView tvGiaSach;
        ImageView imgAnhBia;
        TextView tvDaBan;
        TextView tvPhanTramGiamHome;
        TextView tvDanhGia;
        LinearLayout linear_rating_2;


        public SachBanChayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tv_tenSach);
            tvGiaSach = itemView.findViewById(R.id.tv_giaSach);
            imgAnhBia = itemView.findViewById(R.id.imageView_anh_bia);
            tvDaBan = itemView.findViewById(R.id.tvDaBan);
            tvPhanTramGiamHome = itemView.findViewById(R.id.tvPhanTramGiamHome);
            tvDanhGia = itemView.findViewById(R.id.tvDanhGia);
            linear_rating_2 = itemView.findViewById(R.id.linear_rating_2);

        }
    }

    public void updateData(List<HomeBookModel> newData) {
        this.bookModelList = newData;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int bookID);
    }
//    public void addItem(HomeBookModel newItem) {
//        bookModelList.add(newItem);
//        notifyItemInserted(bookModelList.size() - 1);
//    }
//    public void removeItem(int position) {
//        if (position >= 0 && position < bookModelList.size()) {
//            bookModelList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
}
