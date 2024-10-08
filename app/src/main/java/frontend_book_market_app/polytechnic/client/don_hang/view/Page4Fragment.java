package frontend_book_market_app.polytechnic.client.don_hang.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.don_hang.adapter.P4AdapterOrderDaHuy;
import frontend_book_market_app.polytechnic.client.don_hang.viewmodel.DonHangUserViewModel;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class Page4Fragment extends Fragment {
    private DonHangUserViewModel viewModel;
    private RecyclerView recyclerView;
    private P4AdapterOrderDaHuy adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SkeletonAdapter skeletonAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DonHangUserViewModel.class);

        LinearLayout emptyLayout = view.findViewById(R.id.emptyLayout);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        skeletonAdapter = new SkeletonAdapter(4);
        recyclerView.setAdapter(skeletonAdapter);

        adapter = new P4AdapterOrderDaHuy();

        viewModel.getTab4().observe(getViewLifecycleOwner(), orderResponse -> {
            if (orderResponse != null && orderResponse.getCode() == 0 && !orderResponse.getOrders().isEmpty()) {
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
            viewModel.getCancelledOrders();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        viewModel.getCancelledOrders();
        super.onResume();
    }
}