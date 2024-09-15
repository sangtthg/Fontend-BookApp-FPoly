package frontend_book_market_app.polytechnic.client.home.adapter;

import static frontend_book_market_app.polytechnic.client.core.MyApp.getContext;

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
import com.colormoon.readmoretextview.ReadMoreTextView;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.model.DetailBookResponse;
import frontend_book_market_app.polytechnic.client.home.model.ReviewResponse;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;

public class AdapterBookDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BOOK_DETAIL = 1, TYPE_COMMENT_LIST = 2, TYPE_EMPTY_STATE = 3;
    private static int commentCount = 0;
    private final List<Object> items;


    public AdapterBookDetail(List<Object> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        if (item instanceof DetailBookResponse) return TYPE_BOOK_DETAIL;
        if (item instanceof ReviewResponse.Review) return TYPE_COMMENT_LIST;
        if (item instanceof String && item.equals("EMPTY_STATE")) return TYPE_EMPTY_STATE;
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
            case TYPE_EMPTY_STATE:
                view = inflater.inflate(R.layout.item_empty_state, parent, false);
                return new EmptyStateViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);
        if (holder instanceof BookDetailViewHolder) {
            commentCount = 0;
            for (Object obj : items) {
                if (obj instanceof ReviewResponse.Review) {
                    commentCount++;
                }
            }
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
        TextView tvDanhGia;
        TextView tvLuotDanhGia;
        ReadMoreTextView tvMoTaNoiDung;
        LinearLayout layoutRate;


        public BookDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            book_image_detail = itemView.findViewById(R.id.book_image_detail);
            tvBookTitleDetaill = itemView.findViewById(R.id.book_title_detail);
            tvGiaSach = itemView.findViewById(R.id.tvGiaSach);
            tvGiaSachCu = itemView.findViewById(R.id.tvGiaSachCu);
            tvTacGia = itemView.findViewById(R.id.tvTacGia);
            tvDaBan = itemView.findViewById(R.id.tvDaBan);
            tvPhanTramGiam = itemView.findViewById(R.id.tvPhanTramGiam);
            tvMoTaNoiDung = itemView.findViewById(R.id.tvMoTaNoiDung);
            tvDanhGia = itemView.findViewById(R.id.tvDanhGia);
            tvLuotDanhGia = itemView.findViewById(R.id.tvLuotDanhGia);
            layoutRate = itemView.findViewById(R.id.layoutRate);
        }

        public void bind(DetailBookResponse bookData) {
            if (bookData != null && bookData.getData() != null) {
                if (commentCount > 0) layoutRate.setVisibility(View.VISIBLE);
                else layoutRate.setVisibility(View.GONE);

                DetailBookResponse.BookData data = bookData.getData();
                tvBookTitleDetaill.setText(data.getTitle());
                tvDanhGia.setText(String.format("%s / 5", data.getRateBook()));

                tvMoTaNoiDung.setCollapsedText("Xem thêm");
                tvMoTaNoiDung.setExpandedText("Thu gọn");
                tvMoTaNoiDung.setCollapsedTextColor(R.color.app_red);
                tvMoTaNoiDung.setExpandedTextColor(R.color.app_red);
                tvMoTaNoiDung.setTrimLines(3);
                tvMoTaNoiDung.setText(data.getDescription());
                tvLuotDanhGia.setText(String.format("(%d)", commentCount));
                tvGiaSach.setText(CurrencyFormatter.toVND(data.getNewPrice()));
                tvGiaSachCu.setText(CurrencyFormatter.toVND(data.getOldPrice()));
                tvGiaSachCu.setPaintFlags(tvGiaSachCu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvTacGia.setText(String.format("Tác giả: %s", data.getAuthorName()));
                tvDaBan.setText(String.valueOf(data.getPurchaseCount()));
                if (data.getDiscountPercentage() < 0) {
                    tvPhanTramGiam.setVisibility(View.GONE);
                } else {
                    tvPhanTramGiam.setText(MessageFormat.format("-{0}%", data.getDiscountPercentage()));
                }

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

            //----
            String createdAtString = review.getCreatedAt();
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date;
            try {
                date = inputFormat.parse(createdAtString);
            } catch (ParseException e) {
                e.printStackTrace();
                date = new Date();
            }
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            outputFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            String formattedDate = outputFormat.format(date);
            tvThoiGianComment.setText(formattedDate);
            //----


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

    static class EmptyStateViewHolder extends RecyclerView.ViewHolder {
        public EmptyStateViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
