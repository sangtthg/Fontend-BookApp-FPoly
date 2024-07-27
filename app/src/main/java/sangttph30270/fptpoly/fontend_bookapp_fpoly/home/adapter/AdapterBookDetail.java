package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.core.MyApp.getContext;

import android.graphics.Paint;
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

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.ReviewResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.DateUtils;

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
        if (item instanceof ReviewResponse.Review) return TYPE_COMMENT_LIST;
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
            case TYPE_COMMENT_LIST:
                view = inflater.inflate(R.layout.item_comment, parent, false);
                return new CommentViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);
        if (holder instanceof BookDetailViewHolder) {
            ((BookDetailViewHolder) holder).bind((DetailBookResponse) item);
        } else if (holder instanceof CommentViewHolder) {
            ((CommentViewHolder) holder).bind((ReviewResponse.Review) item);
        }
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
        TextView tvDaBan;
        TextView tvPhanTramGiam;


        public BookDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            book_image_detail = itemView.findViewById(R.id.book_image_detail);
            tvBookTitleDetaill = itemView.findViewById(R.id.book_title_detail);
            tvGiaSach = itemView.findViewById(R.id.tvGiaSach);
            tvGiaSachCu = itemView.findViewById(R.id.tvGiaSachCu);
            tvTacGia = itemView.findViewById(R.id.tvTacGia);
            tvDaBan = itemView.findViewById(R.id.tvDaBan);
            tvPhanTramGiam = itemView.findViewById(R.id.tvPhanTramGiam);
        }

        public void bind(DetailBookResponse bookData) {
            if (bookData != null && bookData.getData() != null) {
                DetailBookResponse.BookData data = bookData.getData();
                tvBookTitleDetaill.setText(data.getTitle());
                tvGiaSach.setText(CurrencyFormatter.toVND(data.getNewPrice()));
                tvGiaSachCu.setText(CurrencyFormatter.toVND(data.getOldPrice()));
                tvGiaSachCu.setPaintFlags(tvGiaSachCu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvTacGia.setText(String.format("Tác giả: %s", data.getAuthorName()));
                tvDaBan.setText(String.valueOf(data.getPurchaseCount()));
                tvPhanTramGiam.setText(MessageFormat.format("-{0}%", data.getDiscountPercentage()));

                Glide.with(getContext())
                        .load(data.getBookAvatar())
                        .placeholder(R.drawable.loading_book)
                        .error(R.drawable.ic_error_photo)
                        .centerCrop()
                        .into(book_image_detail);
            } else {
                tvBookTitleDetaill.setText("Null");
                tvGiaSachCu.setText("Null");
            }
        }
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvReviewerName, tvReviewContent, tvThoiGianComment;
        ImageView avatar;
        LinearLayout starContainer;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewerName = itemView.findViewById(R.id.tvReviewerName);
            tvReviewContent = itemView.findViewById(R.id.tvReviewContent);
            avatar = itemView.findViewById(R.id.imgAvatarUserComment);
            starContainer = itemView.findViewById(R.id.starContainer);
            tvThoiGianComment = itemView.findViewById(R.id.tvThoiGianComment);

        }

        public void bind(ReviewResponse.Review review) {
            tvReviewerName.setText(review.getReviewerName());
            tvReviewContent.setText(review.getComment());
            tvThoiGianComment.setText(DateUtils.formatDate(review.getCreatedAt(), "dd-MM-yyyy  HH:mm"));

            Glide.with(itemView.getContext())
                    .load(review.getReviewerAvatar())
                    .placeholder(R.drawable.loading_book)
                    .centerCrop()
                    .into(avatar);

            starContainer.removeAllViews();
            int rating = review.getRating();
            int totalStars = 5;

            for (int i = 0; i < totalStars; i++) {
                ImageView star = new ImageView(itemView.getContext());
                if (i < rating) {
                    star.setImageResource(R.drawable.ic_2star);
                } else {
                    star.setImageResource(R.drawable.ic_1star);
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        getContext().getResources().getDimensionPixelSize(R.dimen.star_width),
                        getContext().getResources().getDimensionPixelSize(R.dimen.star_height)
                );
                params.setMargins(0, 0, getContext().getResources().getDimensionPixelSize(R.dimen.star_spacing), 0);
                star.setLayoutParams(params);
                starContainer.addView(star);
            }
        }
    }
}
