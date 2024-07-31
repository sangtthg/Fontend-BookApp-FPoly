package frontend_book_market_app.polytechnic.client.order_user.adapter;

import android.content.Context;
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
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.order_user.model.Order;
import frontend_book_market_app.polytechnic.client.utils.CurrencyFormatter;

public class P3AdapterOrderDaGiaoHang extends RecyclerView.Adapter<P3AdapterOrderDaGiaoHang.OrderViewHolder> {
    private List<Order> orders = new ArrayList<>();
    private OnOrderClickListener listener;
    private HomeViewModel homeViewModel;

    public P3AdapterOrderDaGiaoHang(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public void setOnOrderClickListener(OnOrderClickListener listener) {
        this.listener = listener;
    }

    public void setHomeViewModel(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order3, parent, false);
        return new OrderViewHolder(view, homeViewModel);
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

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderIdTextView;
        private final TextView totalPriceTextView;
        private final TextView tvTrangThaiThanhToan;
        private final TextView tvTongThanhToanItemUserOrder;
        private final TextView tvGhiChuItemOrder;
        private final TextView tv5;
        private final RecyclerView itemsRecyclerView;
        private final P3AdapterOrderItemDaGiaoHang orderItemAdapter;
        private final Button btnDanhGia;
        private final TextView tvTongSanPhamItemUserOrder;

        public OrderViewHolder(@NonNull View itemView, HomeViewModel homeViewModel) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
            tvTrangThaiThanhToan = itemView.findViewById(R.id.tvTrangThaiThanhToan);
            tvTongThanhToanItemUserOrder = itemView.findViewById(R.id.tvTongThanhToanItemUserOrder);
            btnDanhGia = itemView.findViewById(R.id.btnDanhGiaSanPhamDaMua);
            tvGhiChuItemOrder = itemView.findViewById(R.id.tvGhiChuItemOrder);
            tv5 = itemView.findViewById(R.id.tv5);
            tvTongSanPhamItemUserOrder = itemView.findViewById(R.id.tvTongSanPhamItemUserOrder);

            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            orderItemAdapter = new P3AdapterOrderItemDaGiaoHang(new ArrayList<>(), homeViewModel);
            itemsRecyclerView.setAdapter(orderItemAdapter);

            btnDanhGia.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    Order clickedOrder = orders.get(position);
                    System.out.println(orders.get(position));
                    listener.onOrderClick(clickedOrder);
                }
            });
        }

        public void bind(Order order) {
            orderIdTextView.setText(String.valueOf(order.getId()));
            totalPriceTextView.setText(String.valueOf(order.getTotalPrice()));
            tvTongSanPhamItemUserOrder.setText(String.format("%d sản phẩm", order.getQuantity()));
            tvTrangThaiThanhToan.setText(order.getStatusShip());
            tvTongThanhToanItemUserOrder.setText(CurrencyFormatter.toVND(order.getTotalPrice() + ""));

            if (order.getItems() != null) {
                orderItemAdapter.setOrderItems(order.getItems(), order);
            } else {
                orderItemAdapter.setOrderItems(new ArrayList<>(), order);
            }
        }
    }
}