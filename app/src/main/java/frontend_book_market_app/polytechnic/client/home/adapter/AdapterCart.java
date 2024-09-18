package frontend_book_market_app.polytechnic.client.home.adapter;

import static frontend_book_market_app.polytechnic.client.core.MyApp.getContext;

import com.apachat.swipereveallayout.core.SwipeLayout;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.SwipeLayout;
import com.bumptech.glide.Glide;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.home.model.CartDeleteRequest;
import frontend_book_market_app.polytechnic.client.home.view.CartActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.model.CartListResponse;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;

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

        int currentQuantity1 = Integer.parseInt(holder.tvQuantity.getText().toString());
        int bookQuantity1 = Integer.parseInt(cartItemDetail.getBook().getQuantity());

        holder.bookCheckbox.setVisibility(showCheckbox ? View.VISIBLE : View.GONE);
        holder.bookCheckbox.setOnCheckedChangeListener(null);
        holder.bookCheckbox.setChecked(false);
        holder.bookCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onItemCheckedBoxChangeListener != null) {
                if (currentQuantity1 > bookQuantity1){
                    holder.bookCheckbox.setChecked(false);
                    Toast.makeText(getContext(), "Đã đạt giới hạn của sách này trong kho", Toast.LENGTH_SHORT).show();
                } else{
                    int bookID = cartItemDetail.getBook().getBookId();
                    String bookTitle = cartItemDetail.getBook().getTitle();
                    int cartId = cartItemDetail.getCartId();
                    onItemCheckedBoxChangeListener.onItemCheckedChange(holder.getAdapterPosition(), isChecked, bookID, bookTitle, cartId);
                }

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
            int bookQuantity = Integer.parseInt(cartItemDetail.getBook().getQuantity());
            if (currentQuantity > bookQuantity) {
                Toast.makeText(getContext(), "Đã đạt giới hạn của sách này trong kho", Toast.LENGTH_SHORT).show();
                return;
            }

            updateQuantity(holder, cartItemDetail, ++currentQuantity);
        });

        holder.swipeLayout.open(true);
        holder.swipeLayout.close(true);

        holder.deleted.setOnClickListener(v -> {
            int currentPosition = holder.getBindingAdapterPosition();
            MessageDialog.show("Xác nhận xoá", "Bạn có chắc bạn muốn xóa mục này khỏi giỏ hàng?")
                    .setOkButton("Đồng ý", new OnDialogButtonClickListener() {
                        @Override
                        public boolean onClick(BaseDialog baseDialog, View v) {
                            List<CartDeleteRequest.CartItemDelete> cartItemsToDelete = new ArrayList<>();
                            cartItemsToDelete.add(new CartDeleteRequest.CartItemDelete(cartItemDetail.getCartId()));
                            homeViewModel.deleteCartItems(cartItemsToDelete);
                            cartItemList.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                            notifyItemRangeChanged(currentPosition, cartItemList.size());
                            return false;
                        }
                    })
                    .setCancelButton("Không xoá", new OnDialogButtonClickListener() {
                        @Override
                        public boolean onClick(BaseDialog baseDialog, View v) {
                            return false;
                        }
                    });
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
        FrameLayout deleted;
        TextView bookTitle, bookPrice, bookOldPrice, quantity;
        MaterialCheckBox bookCheckbox;
        ProgressBar progressBar;
        com.apachat.swipereveallayout.core.SwipeLayout swipeLayout;

        boolean isUpdating = false; //có thể tăng giảm

        TextView btnIncrease = itemView.findViewById(R.id.btnCong);
        TextView btnDecrease = itemView.findViewById(R.id.btnTru);
        TextView tvQuantity = itemView.findViewById(R.id.tvQuantity);

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            bookImage = itemView.findViewById(R.id.book_image_detail);
            bookTitle = itemView.findViewById(R.id.book_title_detail2);
            bookPrice = itemView.findViewById(R.id.tvGiaSach);
            bookOldPrice = itemView.findViewById(R.id.tvGiaSachCu);
            bookCheckbox = itemView.findViewById(R.id.book_checkbox);
            progressBar = itemView.findViewById(R.id.progressBar);
            deleted = itemView.findViewById(R.id.deleted);
        }
    }
}