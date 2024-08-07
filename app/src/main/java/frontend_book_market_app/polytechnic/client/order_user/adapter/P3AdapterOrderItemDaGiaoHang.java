package frontend_book_market_app.polytechnic.client.order_user.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.kongzue.dialogx.dialogs.MessageDialog;

import org.aviran.cookiebar2.CookieBar;

import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.order_user.model.Order;
import frontend_book_market_app.polytechnic.client.order_user.model.OrderItem;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;
import frontend_book_market_app.polytechnic.client.utils.TextV;

public class P3AdapterOrderItemDaGiaoHang extends RecyclerView.Adapter<P3AdapterOrderItemDaGiaoHang.OrderItemViewHolder> {
    private List<OrderItem> orderItems;
    private static Order order;
    private final HomeViewModel homeViewModel;

    public P3AdapterOrderItemDaGiaoHang(List<OrderItem> orderItems, HomeViewModel homeViewModel) {
        this.orderItems = orderItems;
        this.homeViewModel = homeViewModel;
    }

    public void setOrderItems(List<OrderItem> orderItems, Order order) {
        this.orderItems = orderItems;
        this.order = order;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_item3, parent, false);
        return new OrderItemViewHolder(view, homeViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);
        holder.bind(orderItem);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView tvGiaCuOrderItem;
        private final TextView tvDaDanhGia;
        private final TextView tvGiaMoiOrderItem;
        private final TextView tv_soLuong_itemOrder_item;
        private final TextView tvTenTacGiaItemOrder;
        private final ImageView bookImageViewOrderItem;
//        private final CheckBox checkBox;
        private final HomeViewModel homeViewModel;
        private final LinearLayout mainItemOrder3;

        public OrderItemViewHolder(@NonNull View itemView, HomeViewModel homeViewModel) {
            super(itemView);
            this.homeViewModel = homeViewModel;
            titleTextView = itemView.findViewById(R.id.titleTextView);
            bookImageViewOrderItem = itemView.findViewById(R.id.bookImageViewOrderItem);
            tvGiaCuOrderItem = itemView.findViewById(R.id.tvGiaCuOrderItem);
            tvGiaMoiOrderItem = itemView.findViewById(R.id.tvGiaMoiOrderItem);
            tvDaDanhGia = itemView.findViewById(R.id.tvDaDanhGia);
            tv_soLuong_itemOrder_item = itemView.findViewById(R.id.tv_soLuong_itemOrder_item);
            tvTenTacGiaItemOrder = itemView.findViewById(R.id.tvTenTacGiaItemOrder);
            mainItemOrder3 = itemView.findViewById(R.id.mainItemOrder3);




//            checkBox = itemView.findViewById(R.id.checkBoxOrderItem);

//            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                OrderItem orderItem = (OrderItem) buttonView.getTag();
//                if (isChecked) {
//                    homeViewModel.addItem(orderItem);
//                } else {
//                    homeViewModel.removeItem(orderItem);
//                }
//            });
        }

        public void bind(OrderItem orderItem) {
            titleTextView.setText(orderItem.getTitle());
            tvDaDanhGia.setText(orderItem.isReview() ? "Đã đánh giá" : "Chưa đánh giá");
            tvTenTacGiaItemOrder.setText(orderItem.getAuthor_name());
            tv_soLuong_itemOrder_item.setText(String.format("x%d", orderItem.getQuantity()));
            tvGiaMoiOrderItem.setText(CurrencyFormatter.toVNDWithSymbol(String.valueOf(orderItem.getNew_price())));
            tvGiaCuOrderItem.setText(CurrencyFormatter.toVNDWithSymbol(String.valueOf(orderItem.getOld_price())));
            TextV.setStrikeThrough(tvGiaCuOrderItem);

//            checkBox.setTag(orderItem);

            Glide.with(bookImageViewOrderItem.getContext())
                    .load(orderItem.getBook_avatar())
                    .centerCrop()
                    .into(bookImageViewOrderItem);

            mainItemOrder3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (orderItem.isReview()){

                        CookieBar.build((Activity) itemView.getContext())
                                .setTitle("Thông báo")
                                .setMessage("Bạn đã đánh giá sản phẩm này")
                                .setCookiePosition(CookieBar.TOP)
                                .setBackgroundColor(R.color.color1)
                                .show();


                    } else{
                        showReviewDialog(orderItem.book_id);
                    }
                    return false;
                }
            });
        }

        private void showReviewDialog(int bookID) {
            Dialog dialog = new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.dialog_review);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
            EditText editTextComment = dialog.findViewById(R.id.editTextComment);
            MaterialButton buttonSubmit = dialog.findViewById(R.id.buttonSubmit);

//            //ID book
//            List<Integer> selectedItemIds = homeViewModel.getSelectedItemIds();
//            System.out.println("Selected item IDs: " + selectedItemIds);

            buttonSubmit.setOnClickListener(v -> {
                int rating = (int) ratingBar.getRating();
                String comment = editTextComment.getText().toString();
                homeViewModel.submitReview(bookID, order.id, rating, comment, itemView.getContext());
                dialog.dismiss();
            });

            dialog.show();
        }
    }
}