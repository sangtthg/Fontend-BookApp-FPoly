package frontend_book_market_app.polytechnic.client.order_user.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.order_user.adapter.P1AdapterOrderChuaThanhToan;
import frontend_book_market_app.polytechnic.client.order_user.model.Order;
import frontend_book_market_app.polytechnic.client.order_user.model.OrderUserResponse;
import frontend_book_market_app.polytechnic.client.order_user.viewmodel.OrderUserViewModel;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;


public class Page1Fragment extends Fragment {
    private OrderUserViewModel orderUserViewModel;
    private RecyclerView recyclerView;
    private P1AdapterOrderChuaThanhToan adapter;
    private HomeViewModel homeViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String discountCode = ""; // Initialize to an empty string or a default value


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

        LinearLayout emptyLayout = view.findViewById(R.id.emptyLayout);

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
                String token = getToken(); // Lấy token
                if (token.isEmpty()) {
                    // Xử lý trường hợp không có token
                    Toast.makeText(getContext(), "Token không hợp lệ, không thể thanh toán!", Toast.LENGTH_SHORT).show();
                    return;
                }
                homeViewModel.payOrder(getContext(), order.getId(), discountCode != null ? discountCode : "", token);
            }

            @Override
            public void huyDonHang(Order order) {
                MaterialDialog mDialog = new MaterialDialog.Builder(requireActivity())
                        .setTitle("Huỷ đơn hàng")
                        .setMessage("Bạn có chắc bạn muốn huỷ đơn hàng này?")
                        .setCancelable(false)
                        .setPositiveButton("Xác nhận huỷ", R.drawable.ic_check_24_default, new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                orderUserViewModel.cancelOrder(order.getId(), getContext());
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("KHÔNG", R.drawable.ic_close, new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })

                        .build();
                mDialog.show();
            }
        });

        orderUserViewModel.getTab1().observe(getViewLifecycleOwner(), new Observer<OrderUserResponse>() {
            @Override
            public void onChanged(OrderUserResponse orderResponse) {
                if (orderResponse != null && orderResponse.getCode() == 0 && !orderResponse.getOrders().isEmpty()) {
                    adapter.setDataOrdersUser(orderResponse.getOrders());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyLayout.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);
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

    private String getToken() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        return sharedPreferencesHelper.getToken();
    }

}