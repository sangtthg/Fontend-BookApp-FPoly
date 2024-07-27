package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.adapter.P3AdapterOrderDaGiaoHang;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.Order;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderUserResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.viewmodel.OrderUserViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

public class Page3Fragment extends Fragment {
    private OrderUserViewModel viewModel;
    private RecyclerView recyclerView;
    private P3AdapterOrderDaGiaoHang adapter;
    private int bookID;
    private HomeViewModel homeViewModel;

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
        viewModel.getDelivredtOrders();

        TextView emptyTextView = view.findViewById(R.id.emptyTextView);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SkeletonAdapter skeletonAdapter = new SkeletonAdapter(4);
        recyclerView.setAdapter(skeletonAdapter);

        adapter = new P3AdapterOrderDaGiaoHang();

        adapter.setOnOrderClickListener(order -> {
            if (order.getItems() != null && !order.getItems().isEmpty()) {
                bookID = order.getItems().get(0).getBook_id();
                showReviewDialog();
//                Toast.makeText(getContext(), "BookID: " + bookID, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No items in this order", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getOrdersLiveData3().observe(getViewLifecycleOwner(), orderResponse -> {
            if (orderResponse != null && orderResponse.getCode() == 0) {
                adapter.setDataOrdersUser(orderResponse.getOrders());
                recyclerView.setAdapter(adapter);
                emptyTextView.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                emptyTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showReviewDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_review);

        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        EditText editTextComment = dialog.findViewById(R.id.editTextComment);
        MaterialButton buttonSubmit = dialog.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(v -> {
            int rating = (int) ratingBar.getRating();
            String comment = editTextComment.getText().toString();
            homeViewModel.submitReview(bookID, rating, comment, getContext());
            dialog.dismiss();
        });

        dialog.show();
    }
}