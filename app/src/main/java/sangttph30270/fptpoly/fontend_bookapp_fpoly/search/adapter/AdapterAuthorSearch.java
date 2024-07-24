package sangttph30270.fptpoly.fontend_bookapp_fpoly.search.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
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

public class AdapterAuthorSearch extends RecyclerView.Adapter<AdapterAuthorSearch.AuthorViewHolder> {
    private List<HomeBookModel>  authorModelList;
    private final OnItemClickListener listener;

    public AdapterAuthorSearch(List<HomeBookModel> authorModelList, OnItemClickListener listener) {
        this.authorModelList = authorModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_author_search, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HomeBookModel authorModel = authorModelList.get(position);
        holder.textViewAuthor.setText(authorModel.getAuthorName());
        Log.d("AdapterAuthorSearch", "Position: " + position + ", Author: " + authorModel.getAuthorName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return authorModelList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAuthor;


        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);

        }
    }
}
