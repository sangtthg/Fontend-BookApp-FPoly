package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.adapter;


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

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.Order;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;

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
        private final P1AdapterOrderItemChuaThanhToan orderItemAdapter;
        private final TextView tvTrangThaiThanhToan;
        private final TextView tvTongThanhToanItemUserOrder;
        private final TextView tvTongSanPhamItemUserOrder;
        private final TextView infoOrderUser;
        private final Button btnThanhToanDonChoXacNhan;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
            tvTrangThaiThanhToan = itemView.findViewById(R.id.tvTrangThaiThanhToan);
            tvTongThanhToanItemUserOrder = itemView.findViewById(R.id.tvTongThanhToanItemUserOrder);
            tvTongSanPhamItemUserOrder = itemView.findViewById(R.id.tvTongSanPhamItemUserOrder);
            infoOrderUser = itemView.findViewById(R.id.infoOrderUser);
            btnThanhToanDonChoXacNhan = itemView.findViewById(R.id.btnThanhToanDonChoXacNhan);

            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            orderItemAdapter = new P1AdapterOrderItemChuaThanhToan(new ArrayList<>());
            itemsRecyclerView.setAdapter(orderItemAdapter);
        }

        public void bind(Order order) {
            infoOrderUser.setText("Vui lòng chỉ nhấn 'Đã nhận được hàng' khi đơn hàng đã được giao dến bạn và sản phẩm nhận được không vấn đề gì");
            btnThanhToanDonChoXacNhan.setText("Đã nhận được hàng");

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