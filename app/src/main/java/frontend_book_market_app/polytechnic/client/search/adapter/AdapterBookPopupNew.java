package frontend_book_market_app.polytechnic.client.search.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookModel;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;

public class AdapterBookPopupNew extends RecyclerView.Adapter<AdapterBookPopupNew.BookViewHolder> {
    private List<HomeBookModel> bookModelList;
    private final OnItemClickListener listener;

    public AdapterBookPopupNew(List<HomeBookModel> bookModelList, OnItemClickListener listener) {
        this.bookModelList = bookModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_popup_new, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HomeBookModel bookModel = bookModelList.get(position);
        holder.textViewBookSearch.setText(bookModel.getTitle());
        holder.textViewBookPrice2.setText(CurrencyFormatter.toVND(bookModel.getNewPrice()));
        holder.txtDaBan.setText(String.valueOf(bookModel.getPurchaseCount())); // Convert to string if necessary
        Glide.with(holder.itemView.getContext())
                .load(bookModel.getBookAvatar())
                .placeholder(R.drawable.loading_book)
                .fitCenter()
                .into(holder.imageViewBookSearch);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(bookModel.getBookId()));
    }

    @Override
    public int getItemCount() {
        return bookModelList.size();
    }

    public void updateBookList(List<HomeBookModel> newBookList) {
        this.bookModelList = newBookList;
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBookSearch, textViewBookPrice2, txtDaBan;
        ImageView imageViewBookSearch;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDaBan = itemView.findViewById(R.id.txtDaBan);
            textViewBookPrice2 = itemView.findViewById(R.id.textViewBookPrice2);
            textViewBookSearch = itemView.findViewById(R.id.textViewBookSearch);
            imageViewBookSearch = itemView.findViewById(R.id.imageViewBookSearch);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int bookID);
    }
}
