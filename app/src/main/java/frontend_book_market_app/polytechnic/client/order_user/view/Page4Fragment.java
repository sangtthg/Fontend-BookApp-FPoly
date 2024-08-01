package frontend_book_market_app.polytechnic.client.order_user.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.order_user.adapter.P4AdapterOrderDaHuy;
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

public class Page4Fragment extends Fragment {
    private OrderUserViewModel viewModel;
    private RecyclerView recyclerView;
    private P4AdapterOrderDaHuy adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderUserViewModel.class);
        viewModel.getCancelledOrders();

        TextView emptyTextView = view.findViewById(R.id.emptyTextViewPage1);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SkeletonAdapter skeletonAdapter = new SkeletonAdapter(4);
        recyclerView.setAdapter(skeletonAdapter);

        adapter = new P4AdapterOrderDaHuy();

        viewModel.getTab4().observe(getViewLifecycleOwner(), new Observer<OrderUserResponse>() {
            @Override
            public void onChanged(OrderUserResponse orderResponse) {
                if (orderResponse != null && orderResponse.getCode() == 0) {
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