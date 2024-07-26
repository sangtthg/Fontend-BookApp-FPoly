package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.Order;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;

public class OrderAdapter2 extends RecyclerView.Adapter<OrderAdapter2.OrderViewHolder> {
    private List<Order> orders = new ArrayList<>();

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setDataOrdersUser(List<Order> orders) {
        if (orders!=null){
            this.orders = orders;
            notifyDataSetChanged();
        }
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderIdTextView;
        private final TextView totalPriceTextView;
        private final RecyclerView itemsRecyclerView;
        private final OrderItemAdapter2 orderItemAdapter;
        private final TextView tvTrangThaiThanhToan;
        private final TextView tvTongThanhToanItemUserOrder;
        private final TextView tvTongSanPhamItemUserOrder;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
            tvTrangThaiThanhToan = itemView.findViewById(R.id.tvTrangThaiThanhToan);
            tvTongThanhToanItemUserOrder = itemView.findViewById(R.id.tvTongThanhToanItemUserOrder);
            tvTongSanPhamItemUserOrder = itemView.findViewById(R.id.tvTongSanPhamItemUserOrder);

            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            orderItemAdapter = new OrderItemAdapter2(new ArrayList<>());
            itemsRecyclerView.setAdapter(orderItemAdapter);
        }

        public void bind(Order order) {
            orderIdTextView.setText(String.valueOf(order.getId()));
            totalPriceTextView.setText(String.valueOf(order.getTotalPrice()));
            tvTongSanPhamItemUserOrder.setText(String.format("%d sản phẩm", order.getQuantity()));
            tvTrangThaiThanhToan.setText(order.getOrderStatus());
            tvTongThanhToanItemUserOrder.setText(CurrencyFormatter.toVND(order.getTotalPrice() + ""));

            if (order.getItems() != null) {
                orderItemAdapter.setOrderItems(order.getItems());
            } else {
                orderItemAdapter.setOrderItems(new ArrayList<>());
            }
        }

    }
}