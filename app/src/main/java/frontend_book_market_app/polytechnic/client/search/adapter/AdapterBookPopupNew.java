package frontend_book_market_app.polytechnic.client.search.adapter;

import android.annotation.SuppressLint;
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
import frontend_book_market_app.polytechnic.client.home.adapter.AdapteHomerSach;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookModel;

public class AdapterBookPopupNew extends RecyclerView.Adapter<AdapterBookPopupNew.BookViewHolder> {
    private List<HomeBookModel> bookModelList;
    private final AdapteHomerSach.OnItemClickListener listener;

    public AdapterBookPopupNew(List<HomeBookModel> bookModelList, AdapteHomerSach.OnItemClickListener listener) {
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
        Glide.with(holder.itemView.getContext())
                .load(bookModel.getBookAvatar())
                .placeholder(R.drawable.loading_book)
                .fitCenter()
                .into(holder.imageViewBookSearch);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
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
        TextView textViewBookSearch;
        ImageView imageViewBookSearch;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBookSearch = itemView.findViewById(R.id.textViewBookSearch);
            imageViewBookSearch = itemView.findViewById(R.id.imageViewBookSearch);
        }
    }
}
