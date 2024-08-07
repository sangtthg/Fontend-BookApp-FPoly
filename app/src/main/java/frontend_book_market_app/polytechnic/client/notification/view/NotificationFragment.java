package frontend_book_market_app.polytechnic.client.notification.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.notification.adapter.NotificationAdapter;
import frontend_book_market_app.polytechnic.client.notification.model.NotificationModel;
import frontend_book_market_app.polytechnic.client.notification.viewmodel.NotificationViewModel;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class NotificationFragment extends Fragment {
    private NotificationViewModel notificationViewModel;
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView noDataTextView;
    private PopupMenu popupMenu;
    SkeletonAdapter skeletonAdapter = new SkeletonAdapter(9);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(skeletonAdapter);

        notificationAdapter = new NotificationAdapter();

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        noDataTextView = view.findViewById(R.id.noDataTextView);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerView.setAdapter(skeletonAdapter);
            notificationViewModel.fetchNotifications();
        });

        view.findViewById(R.id.moreMenuNotification).setOnClickListener(this::showPopupMenu);

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        notificationViewModel.fetchNotifications();

        notificationViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), notifications -> {
            updateNotifications(notifications);
            recyclerView.setAdapter(notificationAdapter);
            swipeRefreshLayout.setRefreshing(false);
        });

        notificationAdapter.setOnItemClickListener(notification -> {
            notificationViewModel.markNotificationAsRead(notification.getId());
        });
    }

    private void updateNotifications(List<NotificationModel> notifications) {
        if (notifications == null || notifications.isEmpty()) {
            noDataTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noDataTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            notificationAdapter.setNotifications(notifications);
        }
    }

    private void showPopupMenu(View view) {
        if (popupMenu == null) {
            popupMenu = new PopupMenu(getContext(), view, Gravity.END);
            popupMenu.setGravity(Gravity.TOP | Gravity.END);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_notification, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_read_all) {
                    notificationViewModel.markAllNotificationsAsRead(getContext());
                    return true;
                } else if (item.getItemId() == R.id.menu_delete_all) {
                    Toast.makeText(getContext(), "Đã xoá tất cả thông báo", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            });

            popupMenu.setOnDismissListener(menu -> popupMenu = null);
            popupMenu.show();
        } else {
            popupMenu.dismiss();
            popupMenu = null;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) notificationViewModel.fetchNotifications();
        super.onHiddenChanged(hidden);
    }
}