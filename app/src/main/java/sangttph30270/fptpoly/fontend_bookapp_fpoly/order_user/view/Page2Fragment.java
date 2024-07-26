package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.adapter.OrderAdapter2;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderResponse2;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.viewmodel.OrderUserViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Page2Fragment extends Fragment {
    private OrderUserViewModel viewModel;
    private RecyclerView recyclerView;
    private OrderAdapter2 adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OrderUserViewModel.class);
        viewModel.getWaiForDeliverytOrders();

        TextView emptyTextView = view.findViewById(R.id.emptyTextView);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SkeletonAdapter skeletonAdapter = new SkeletonAdapter(4);
        recyclerView.setAdapter(skeletonAdapter);

        adapter = new OrderAdapter2();

        viewModel.getOrdersLiveData2().observe(getViewLifecycleOwner(), new Observer<OrderResponse2>() {
            @Override
            public void onChanged(OrderResponse2 orderResponse) {
                System.out.println(orderResponse.getCode() + "kkkkk");
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