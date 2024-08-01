package frontend_book_market_app.polytechnic.client.order_user.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.order_user.adapter.P1AdapterOrderChuaThanhToan;
import frontend_book_market_app.polytechnic.client.order_user.model.Order;
import frontend_book_market_app.polytechnic.client.order_user.model.OrderUserResponse;
import frontend_book_market_app.polytechnic.client.order_user.viewmodel.OrderUserViewModel;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Page1Fragment extends Fragment {
    private OrderUserViewModel orderUserViewModel;
    private RecyclerView recyclerView;
    private P1AdapterOrderChuaThanhToan adapter;
    private HomeViewModel homeViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = requireActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        TextView emptyTextView = view.findViewById(R.id.emptyTextViewPage1);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        orderUserViewModel = new ViewModelProvider(this).get(OrderUserViewModel.class);
        orderUserViewModel.fetchPendingOrders();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SkeletonAdapter skeletonAdapter = new SkeletonAdapter(4);
        recyclerView.setAdapter(skeletonAdapter);

        adapter = new P1AdapterOrderChuaThanhToan(new P1AdapterOrderChuaThanhToan.OnItemClickListener() {
            @Override
            public void thanhToanDonHang(Order order) {
                homeViewModel.payOrder(getContext(), order.getId());
            }

            @Override
            public void huyDonHang(Order order) {
                orderUserViewModel.cancelOrder(order.getId(), getContext());
            }
        });

        orderUserViewModel.getTab1().observe(getViewLifecycleOwner(), new Observer<OrderUserResponse>() {
            @Override
            public void onChanged(OrderUserResponse orderResponse) {
                if (orderResponse != null && orderResponse.getCode() == 0 && !orderResponse.getOrders().isEmpty()) {
                    adapter.setDataOrdersUser(orderResponse.getOrders());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyTextView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerView.setAdapter(skeletonAdapter);
            orderUserViewModel.fetchPendingOrders();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        orderUserViewModel.fetchPendingOrders();
        super.onResume();
    }
}