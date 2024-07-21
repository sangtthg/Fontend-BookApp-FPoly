package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.MyApp.getContext;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder> {

    private List<CartListResponse.CartItemDetail> cartItemList;

    public AdapterCart(List<CartListResponse.CartItemDetail> cartItemList) {
        this.cartItemList = cartItemList;
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
        holder.bookTitle.setText(cartItemDetail.getBook().getTitle());
        holder.tvtacGiaSach.setText(cartItemDetail.getBook().getAuthorName());
        holder.bookPrice.setText(String.format("%sđ", cartItemDetail.getBook().getNewPrice()));
        holder.bookOldPrice.setText(String.format("%sđ", cartItemDetail.getBook().getOldPrice()));
        holder.bookOldPrice.setPaintFlags(holder.bookOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(getContext())
                .load(cartItemDetail.getBook().getBookAvatar())
                .placeholder(R.drawable.loading_book)
                .error(R.drawable.ic_error_photo)
                .centerCrop()
                .into(holder.bookImage);



        holder.quantity.setText(String.valueOf(cartItemDetail.getQuantity()));

        holder.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.quantity.getText().toString());
            currentQuantity++;
            holder.quantity.setText(String.valueOf(currentQuantity));
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.quantity.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity--;
                holder.quantity.setText(String.valueOf(currentQuantity));
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

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView bookTitle, bookPrice, bookOldPrice, quantity, tvtacGiaSach;
        MaterialButton btnIncrease, btnDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_image_detail);
            bookTitle = itemView.findViewById(R.id.book_title_detail);
            bookPrice = itemView.findViewById(R.id.tvGiaSach);
            bookOldPrice = itemView.findViewById(R.id.tvGiaSachCu);
            quantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnCong);
            btnDecrease = itemView.findViewById(R.id.btnTru);
            tvtacGiaSach = itemView.findViewById(R.id.tvtacGiaSach);
        }
    }
}