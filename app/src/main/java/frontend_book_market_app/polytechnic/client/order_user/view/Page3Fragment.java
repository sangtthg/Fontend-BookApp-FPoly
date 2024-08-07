package frontend_book_market_app.polytechnic.client.order_user.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.order_user.adapter.P3AdapterOrderDaGiaoHang;
import frontend_book_market_app.polytechnic.client.order_user.viewmodel.OrderUserViewModel;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class Page3Fragment extends Fragment {
    private OrderUserViewModel viewModel;
    private RecyclerView recyclerView;
    private P3AdapterOrderDaGiaoHang adapter;
    private int bookID;
    private HomeViewModel homeViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SkeletonAdapter skeletonAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel = new ViewModelProvider(this).get(OrderUserViewModel.class);

        LinearLayout emptyLayout = view.findViewById(R.id.emptyLayout);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        skeletonAdapter = new SkeletonAdapter(4);
        recyclerView.setAdapter(skeletonAdapter);

        adapter = new P3AdapterOrderDaGiaoHang(homeViewModel);

        viewModel.getTab3().observe(getViewLifecycleOwner(), orderResponse -> {
            if (orderResponse != null && orderResponse.getCode() == 0) {
                adapter.setDataOrdersUser(orderResponse.getOrders());
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerView.setAdapter(skeletonAdapter);
            viewModel.getDeliveredOrders();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        viewModel.getDeliveredOrders();
        super.onResume();
    }
}