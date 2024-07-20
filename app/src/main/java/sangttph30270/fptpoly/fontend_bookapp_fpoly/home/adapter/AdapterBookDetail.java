package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.MyApp.getContext;

import android.graphics.Paint;
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
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;

public class AdapterBookDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BOOK_DETAIL = 1, TYPE_COMMENT_LIST = 2;
    private final List<Object> items;

    public AdapterBookDetail(List<Object> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        if (item instanceof DetailBookResponse) return TYPE_BOOK_DETAIL;
        // if (item instanceof CommentListItem) return TYPE_COMMENT_LIST;
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case TYPE_BOOK_DETAIL:
                view = inflater.inflate(R.layout.item_book_detail, parent, false);
                return new BookDetailViewHolder(view);
            // case TYPE_COMMENT_LIST:
            //     view = inflater.inflate(R.layout.item_image, parent, false);
            //     return new ImageViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);
        if (holder instanceof BookDetailViewHolder) ((BookDetailViewHolder) holder).bind((DetailBookResponse) item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class BookDetailViewHolder extends RecyclerView.ViewHolder {
    ImageView book_image_detail;
    TextView tvBookTitleDetaill;
    TextView tvGiaSach;
    TextView tvGiaSachCu;
    TextView tvTacGia;


        public BookDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        book_image_detail = itemView.findViewById(R.id.book_image_detail);
        tvBookTitleDetaill = itemView.findViewById(R.id.book_title_detail);
        tvGiaSach = itemView.findViewById(R.id.tvGiaSach);
        tvGiaSachCu = itemView.findViewById(R.id.tvGiaSachCu);
        tvTacGia = itemView.findViewById(R.id.tvTacGia);
    }

    public void bind(DetailBookResponse BookData) {
        if (BookData != null && BookData.getData() != null) {
            tvBookTitleDetaill.setText(BookData.getData().getTitle());
            tvGiaSach.setText(CurrencyFormatter.toVND(BookData.getData().getNewPrice()));
            tvGiaSachCu.setText(CurrencyFormatter.toVND(BookData.getData().getOldPrice()));
            tvGiaSachCu.setText(CurrencyFormatter.toVND(BookData.getData().getOldPrice()));
            tvGiaSachCu.setPaintFlags(tvGiaSachCu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvTacGia.setText(String.format("Tác giả: %s", BookData.getData().getAuthorName()));

            Glide.with(getContext())
                    .load(BookData.getData().getBookAvatar())
                    .placeholder(R.drawable.loading_book)
                    .fitCenter()
                    .into(book_image_detail);
        } else {
            tvBookTitleDetaill.setText("Null");
            tvGiaSachCu.setText("Null");
        }
    }
}
}