package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.adapter.P1AdapterOrderChuaThanhToan;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderUserResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.viewmodel.OrderUserViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Page1Fragment extends Fragment {
    private OrderUserViewModel viewModel;
    private RecyclerView recyclerView;
    private P1AdapterOrderChuaThanhToan adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView emptyTextView = view.findViewById(R.id.emptyTextView);

        viewModel = new ViewModelProvider(this).get(OrderUserViewModel.class);
        viewModel.fetchPendingOrders();


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SkeletonAdapter skeletonAdapter = new SkeletonAdapter(4);
        recyclerView.setAdapter(skeletonAdapter);

        adapter = new P1AdapterOrderChuaThanhToan();

        viewModel.getOrdersLiveData().observe(getViewLifecycleOwner(), new Observer<OrderUserResponse>() {
            @Override
            public void onChanged(OrderUserResponse orderResponse) {
                if (orderResponse != null && orderResponse.getCode() == 0) {
                    adapter.setDataOrdersUser(orderResponse.getOrders());
                    recyclerView.setAdapter(adapter);
                    emptyTextView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}