package frontend_book_market_app.polytechnic.client.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.search.model.Book;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;

public class AdapterSearchView extends RecyclerView.Adapter<AdapterSearchView.SachHomeViewHolder> {

    private List<Book> bookList;
    private final OnItemClickListener listener;

    public AdapterSearchView(List<Book> bookList, OnItemClickListener listener) {
        this.bookList = bookList != null ? bookList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public SachHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_search_view, parent, false);
        return new SachHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachHomeViewHolder holder, int position) {
        Book book = bookList.get(position);
        book.calculateDiscountPercentage(); // Tính toán tỷ lệ giảm giá

        holder.tvSearchTitle.setText(book.getTitle());
        holder.tv_search_best_seller.setVisibility(View.GONE);

        String newPrice = book.getNew_price();
        if (newPrice != null) {
            holder.tvSearchPrice.setText(CurrencyFormatter.toVND(newPrice));
        } else {
            holder.tvSearchPrice.setText("Giá chưa có");
        }
        holder.tvSearchSold.setText(String.valueOf(book.getPurchase_count()));

        holder.tvSearchDiscount.setText(MessageFormat.format("-{0}%", (int) book.getDiscountPercentage()));

        // Kiểm tra giá trị null hoặc đường dẫn không hợp lệ
        String bookAvatar = book.getBook_avatar();
        Glide.with(holder.itemView.getContext())
                .load(bookAvatar)
                .placeholder(R.drawable.loading_book)
                .centerCrop()
                .into(holder.imgSearchCover);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(book.getBook_id()));
    }


    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }

    public void updateData(List<Book> newData) {
        this.bookList = newData != null ? newData : new ArrayList<>();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int bookId);
    }

    public static class SachHomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvSearchTitle;
        TextView tvSearchPrice;
        TextView tvSearchSold;
        TextView tvSearchDiscount,tv_search_best_seller;
        ImageView imgSearchCover;

        public SachHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_search_best_seller = itemView.findViewById(R.id.tv_search_best_seller);
            tvSearchTitle = itemView.findViewById(R.id.tv_search_title);
            tvSearchPrice = itemView.findViewById(R.id.tv_search_price);
            imgSearchCover = itemView.findViewById(R.id.imageView_search_cover);
            tvSearchSold = itemView.findViewById(R.id.tv_search_sold);
            tvSearchDiscount = itemView.findViewById(R.id.tv_search_discount);
        }
    }
}
