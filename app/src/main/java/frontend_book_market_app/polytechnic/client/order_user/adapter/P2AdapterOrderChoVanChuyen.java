package frontend_book_market_app.polytechnic.client.order_user.adapter;


import android.graphics.Color;
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
import frontend_book_market_app.polytechnic.client.order_user.model.Order;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;

public class P2AdapterOrderChoVanChuyen extends RecyclerView.Adapter<P2AdapterOrderChoVanChuyen.OrderViewHolder> {
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
        private final P2AdapterOrderItemChoVanChuyen orderItemAdapter;
        private final TextView tvTrangThaiThanhToan;
        private final TextView tvTongThanhToanItemUserOrder;
        private final TextView tvTongSanPhamItemUserOrder;
        private final TextView infoOrderUser;
        private final Button btnThanhToanDonChoXacNhan, btnHuyThanhToanOrder;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
            tvTrangThaiThanhToan = itemView.findViewById(R.id.tvTrangThaiThanhToan);
            tvTongThanhToanItemUserOrder = itemView.findViewById(R.id.tvTongThanhToanItemUserOrder);
            tvTongSanPhamItemUserOrder = itemView.findViewById(R.id.tvTongSanPhamItemUserOrder);
            infoOrderUser = itemView.findViewById(R.id.txtInfoOrderUser);
            btnThanhToanDonChoXacNhan = itemView.findViewById(R.id.btnThanhToanDonChoXacNhan);
            btnHuyThanhToanOrder = itemView.findViewById(R.id.btnHuyThanhToanOrder);

            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            orderItemAdapter = new P2AdapterOrderItemChoVanChuyen(new ArrayList<>());
            itemsRecyclerView.setAdapter(orderItemAdapter);

            btnHuyThanhToanOrder.setVisibility(View.GONE);
        }

        public void bind(Order order) {
            infoOrderUser.setText("Vui lòng chỉ nhấn 'Đã nhận được hàng' khi đơn hàng đã được giao dến bạn và sản phẩm nhận được không vấn đề gì");
            btnThanhToanDonChoXacNhan.setText("Đơn hàng đang trên đường giao tới bạn");
            btnThanhToanDonChoXacNhan.setVisibility(View.GONE);

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