package frontend_book_market_app.polytechnic.client.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import frontend_book_market_app.polytechnic.client.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final String[] categories = {"Khoa học", "Tiếng Anh", "Giải trí", "Nấu ăn", "Chiêm tinh"};
    private final OnItemClickListener listener;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context, OnItemClickListener listener) {
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_category_home, parent, false);
        return new CategoryViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories[position]);
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final MaterialButton button;
        private OnItemClickListener listener;

        public CategoryViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.button = itemView.findViewById(R.id.materialButton);
            this.listener = listener;
            button.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
        }

        public void bind(String category) {
            button.setText(category);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}