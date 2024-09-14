package frontend_book_market_app.polytechnic.client.don_hang.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.don_hang.model.Order;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;

public class P4AdapterOrderDaHuy extends RecyclerView.Adapter<P4AdapterOrderDaHuy.OrderViewHolder> {
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
        if (orders != null) {
            this.orders = orders;
            notifyDataSetChanged();
        }
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderIdTextView;
        private final TextView totalPriceTextView;
        private final RecyclerView itemsRecyclerView;
        private final P4AdapterOrderItemDaHuy orderItemAdapter;
        private final TextView tvTrangThaiThanhToan;
        private final TextView tvTongThanhToanItemUserOrder;
        private final TextView tvTongSanPhamItemUserOrder;
        private final TextView txtInfoOrderUser;
        private final Button btnThanhToanDonChoXacNhan, btnHuyThanhToanOrder;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
            tvTrangThaiThanhToan = itemView.findViewById(R.id.tvTrangThaiThanhToan);
            tvTongThanhToanItemUserOrder = itemView.findViewById(R.id.tvTongThanhToanItemUserOrder);
            tvTongSanPhamItemUserOrder = itemView.findViewById(R.id.tvTongSanPhamItemUserOrder);
            btnThanhToanDonChoXacNhan = itemView.findViewById(R.id.btnThanhToanDonChoXacNhan);
            btnHuyThanhToanOrder = itemView.findViewById(R.id.btnHuyThanhToanOrder);
            txtInfoOrderUser = itemView.findViewById(R.id.txtInfoOrderUser);

            btnHuyThanhToanOrder.setVisibility(View.GONE);
            btnThanhToanDonChoXacNhan.setVisibility(View.GONE);
            txtInfoOrderUser.setVisibility(View.VISIBLE);


            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            orderItemAdapter = new P4AdapterOrderItemDaHuy(new ArrayList<>());
            itemsRecyclerView.setAdapter(orderItemAdapter);


        }

        public void bind(Order order) {
            txtInfoOrderUser.setText("Đơn hàng này của bạn đã bị huỷ");

            orderIdTextView.setText(String.valueOf(order.getId()));
            totalPriceTextView.setText(String.valueOf(order.getTotalPrice()));
            tvTongSanPhamItemUserOrder.setText(String.format("%d sản phẩm", order.getQuantity()));
            tvTrangThaiThanhToan.setText(order.getStatusShip());
            tvTongThanhToanItemUserOrder.setText(CurrencyFormatter.toVND(order.getTotalPrice() + ""));

            if (order.getItems() != null) {
                orderItemAdapter.setOrderItems(order.getItems());
            } else {
                orderItemAdapter.setOrderItems(new ArrayList<>());
            }


        }

    }
}