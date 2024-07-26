package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.adapter;

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
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderItem;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.TextV;

public class OrderItemAdapter2 extends RecyclerView.Adapter<OrderItemAdapter2.OrderItemViewHolder> {
    private List<OrderItem> orderItems;

    public OrderItemAdapter2(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_item, parent, false);
        return new OrderItemViewHolder(view);
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
        private final TextView tvGiaMoiOrderItem;
        private final TextView tv_soLuong_itemOrder_item;
        private final ImageView bookImageViewOrderItem;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            bookImageViewOrderItem = itemView.findViewById(R.id.bookImageViewOrderItem);
            tvGiaCuOrderItem = itemView.findViewById(R.id.tvGiaCuOrderItem);
            tvGiaMoiOrderItem = itemView.findViewById(R.id.tvGiaMoiOrderItem);
            tv_soLuong_itemOrder_item = itemView.findViewById(R.id.tv_soLuong_itemOrder_item);
        }

        public void bind(OrderItem orderItem) {
            titleTextView.setText(orderItem.getTitle());
            tv_soLuong_itemOrder_item.setText(String.format("x%d", orderItem.getQuantity()));
            tvGiaMoiOrderItem.setText(CurrencyFormatter.toVNDWithSymbol(String.valueOf(orderItem.getNew_price())));
            tvGiaCuOrderItem.setText(CurrencyFormatter.toVNDWithSymbol(String.valueOf(orderItem.getOld_price())));
            TextV.setStrikeThrough(tvGiaCuOrderItem);

            Glide.with(bookImageViewOrderItem.getContext())
                    .load(orderItem.getBook_avatar())
                    .centerCrop()
                    .into(bookImageViewOrderItem);
        }
    }
}