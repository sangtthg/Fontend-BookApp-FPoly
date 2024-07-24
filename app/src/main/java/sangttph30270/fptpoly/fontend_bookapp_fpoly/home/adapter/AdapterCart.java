package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.MyApp.getContext;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder> {

    private final List<CartListResponse.CartItemDetail> cartItemList;

    private OnItemCheckedChangeListener onItemCheckedBoxChangeListener;
    private boolean showCheckbox = true;

    public AdapterCart(List<CartListResponse.CartItemDetail> cartItemList) {
        this.cartItemList = cartItemList;
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
        holder.bookTitle.setText(cartItemDetail.getBook().getTitle());
        holder.tvQuantity.setText(cartItemDetail.getQuantity()+ "");
        holder.tvtacGiaSach.setText(cartItemDetail.getBook().getAuthorName());

        holder.bookPrice.setText(String.format("%s", CurrencyFormatter.toVND(cartItemDetail.getBook().getNewPrice())));
        holder.bookOldPrice.setText(String.format("%s", CurrencyFormatter.toVND(cartItemDetail.getBook().getOldPrice())));
        holder.bookOldPrice.setPaintFlags(holder.bookOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(getContext())
                .load(cartItemDetail.getBook().getBookAvatar())
                .placeholder(R.drawable.loading_book)
                .error(R.drawable.ic_error_photo)
                .centerCrop()
                .into(holder.bookImage);

        //checkbox
        holder.bookCheckbox.setVisibility(showCheckbox ? View.VISIBLE : View.GONE);
        holder.bookCheckbox.setOnCheckedChangeListener(null);
        holder.bookCheckbox.setChecked(false);
        holder.bookCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(onItemCheckedBoxChangeListener != null) {
                int bookID = cartItemDetail.getBook().getBookId();
                String bookTitle = cartItemDetail.getBook().getTitle();
                int cartId = cartItemDetail.getCartId();
                onItemCheckedBoxChangeListener.onItemCheckedChange(position, isChecked, bookID, bookTitle, cartId);
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                currentQuantity++;
                holder.tvQuantity.setText(String.valueOf(currentQuantity));
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    holder.tvQuantity.setText(String.valueOf(currentQuantity));
                }
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

        TextView btnIncrease = itemView.findViewById(R.id.btnCong);
        TextView btnDecrease = itemView.findViewById(R.id.btnTru);
        TextView tvQuantity = itemView.findViewById(R.id.tvQuantity);


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_image_detail);
            bookTitle = itemView.findViewById(R.id.book_title_detail);
            bookPrice = itemView.findViewById(R.id.tvGiaSach);
            bookOldPrice = itemView.findViewById(R.id.tvGiaSachCu);
            tvtacGiaSach = itemView.findViewById(R.id.tvtacGiaSach);
            bookCheckbox = itemView.findViewById(R.id.book_checkbox);
        }
    }
}
