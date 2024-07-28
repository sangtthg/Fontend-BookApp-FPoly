package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.core.MyApp.getContext;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder> {

    private final List<CartListResponse.CartItemDetail> cartItemList;
    private final HomeViewModel homeViewModel;

    private OnItemCheckedChangeListener onItemCheckedBoxChangeListener;
    private boolean showCheckbox = true;

    public AdapterCart(List<CartListResponse.CartItemDetail> cartItemList, HomeViewModel homeViewModel) {
        this.cartItemList = cartItemList;
        this.homeViewModel = homeViewModel;
    }

    public void setOnItemCheckedBoxChangeListener(OnItemCheckedChangeListener listener) {
        this.onItemCheckedBoxChangeListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartListResponse.CartItemDetail cartItemDetail = cartItemList.get(position);
        holder.bookTitle.setText(cartItemDetail.getBook_title_in_cart());
        holder.tvQuantity.setText(String.format("%d", cartItemDetail.getQuantity()));
        holder.bookPrice.setText(String.format("%s", CurrencyFormatter.toVND(cartItemDetail.getBook().getNewPrice())));
        holder.bookOldPrice.setText(String.format("%s", CurrencyFormatter.toVND(cartItemDetail.getBook().getOldPrice())));
        holder.bookOldPrice.setPaintFlags(holder.bookOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(getContext())
                .load(cartItemDetail.getBook().getBookAvatar())
                .placeholder(R.drawable.loading_book)
                .error(R.drawable.ic_error_photo)
                .centerCrop()
                .into(holder.bookImage);

        holder.bookCheckbox.setVisibility(showCheckbox ? View.VISIBLE : View.GONE);
        holder.bookCheckbox.setOnCheckedChangeListener(null);
        holder.bookCheckbox.setChecked(false);
        holder.bookCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onItemCheckedBoxChangeListener != null) {
                int bookID = cartItemDetail.getBook().getBookId();
                String bookTitle = cartItemDetail.getBook().getTitle();
                int cartId = cartItemDetail.getCartId();
                onItemCheckedBoxChangeListener.onItemCheckedChange(position, isChecked, bookID, bookTitle, cartId);
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (holder.isUpdating) return;

            int currentQuantity = Integer.parseInt(holder.tvQuantity.getText().toString());
            if (currentQuantity > 1) {
                updateQuantity(holder, cartItemDetail, --currentQuantity);
            }
        });

        holder.btnIncrease.setOnClickListener(v -> {
            if (holder.isUpdating) return;

            int currentQuantity = Integer.parseInt(holder.tvQuantity.getText().toString());
            updateQuantity(holder, cartItemDetail, ++currentQuantity);
        });
    }

    private void updateQuantity(CartViewHolder holder, CartListResponse.CartItemDetail cartItemDetail, int newQuantity) {
        holder.isUpdating = true;
        holder.progressBar.setVisibility(View.VISIBLE);
        homeViewModel.updateCartQuantity(cartItemDetail.getCartId(), newQuantity, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.isUpdating = false;
                    holder.tvQuantity.setText(String.valueOf(newQuantity));
                } else {
                    Log.e("AdapterCart", "Failed to update cart quantity");
                    holder.isUpdating = false;
                    holder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                holder.progressBar.setVisibility(View.GONE);
                holder.isUpdating = false;
                Log.e("AdapterCart", "Error updating cart quantity", t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public void updateCartItems(List<CartListResponse.CartItemDetail> newCartItems) {
        this.cartItemList.clear();
        this.cartItemList.addAll(newCartItems);
        notifyDataSetChanged();
    }

    public void toggleCheckbox() {
        showCheckbox = !showCheckbox;
        notifyDataSetChanged();
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChange(int position, boolean isChecked, int bookID, String bookTitle, int cartID);
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView bookTitle, bookPrice, bookOldPrice, quantity, tvtacGiaSach;
        CheckBox bookCheckbox;
        ProgressBar progressBar;

        boolean isUpdating = false;

        TextView btnIncrease = itemView.findViewById(R.id.btnCong);
        TextView btnDecrease = itemView.findViewById(R.id.btnTru);
        TextView tvQuantity = itemView.findViewById(R.id.tvQuantity);

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_image_detail);
            bookTitle = itemView.findViewById(R.id.book_title_detail2);
            bookPrice = itemView.findViewById(R.id.tvGiaSach);
            bookOldPrice = itemView.findViewById(R.id.tvGiaSachCu);
            tvtacGiaSach = itemView.findViewById(R.id.tvtacGiaSach);
            bookCheckbox = itemView.findViewById(R.id.book_checkbox);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}