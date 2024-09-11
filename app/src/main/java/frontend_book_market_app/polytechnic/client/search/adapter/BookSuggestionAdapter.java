package frontend_book_market_app.polytechnic.client.search.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookModel;

public class BookSuggestionAdapter extends RecyclerView.Adapter<BookSuggestionAdapter.ViewHolder> {

    private List<HomeBookModel> bookSuggestions;
    private String searchQuery;
    private OnItemClickListener onItemClickListener;

    public BookSuggestionAdapter(List<HomeBookModel> bookSuggestions, String searchQuery, OnItemClickListener onItemClickListener) {
        this.bookSuggestions = bookSuggestions;
        this.searchQuery = searchQuery;
        this.onItemClickListener = onItemClickListener; // Lưu trữ listener
    }

    public void setQuery(String query) {
        this.searchQuery = query;
        Log.d("BookSuggestionAdapter", "Search Query Updated: " + searchQuery);
//        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeBookModel book = bookSuggestions.get(position);
        holder.titleTextView.setText(getHighlightedText(book.getTitle(), searchQuery));

        // Set click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(book.getTitle());
            }
        });
    }

    private Spannable getHighlightedText(String text, String query) {
        Spannable spannable = new SpannableString(text);
        if (text != null && query != null && !query.isEmpty()) {
            String lowerCaseText = text.toLowerCase();
            String lowerCaseQuery = query.toLowerCase();

            Log.d("Highlight", "Text: " + text);
            Log.d("Highlight", "Query: " + query);

            int startIndex = lowerCaseText.indexOf(lowerCaseQuery);
            while (startIndex >= 0) {
                int endIndex = startIndex + query.length();
                Log.d("Highlight", "Start: " + startIndex + ", End: " + endIndex);

                if (endIndex <= text.length()) {
                    spannable.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannable.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                startIndex = lowerCaseText.indexOf(lowerCaseQuery, endIndex);
            }
        }
        return spannable;
    }




    @Override
    public int getItemCount() {
        return bookSuggestions.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String title);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle1);
        }
    }
}