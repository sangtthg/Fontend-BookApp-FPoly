package frontend_book_market_app.polytechnic.client.notification.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.notification.model.NotificationModel;
import frontend_book_market_app.polytechnic.client.notification.network.RepositoryNotification;

import java.util.List;

public class NotificationViewModel extends ViewModel {
    private final MutableLiveData<List<NotificationModel>> notificationsLiveData;
    private final RepositoryNotification repository;

    public NotificationViewModel() {
        notificationsLiveData = new MutableLiveData<>();
        repository = new RepositoryNotification();
    }

    public LiveData<List<NotificationModel>> getNotificationsLiveData() {
        return notificationsLiveData;
    }

    public void fetchNotifications() {
        repository.fetchNotifications(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    notificationsLiveData.postValue(response.body());
                } else {
                    System.err.println("Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void markNotificationAsRead(int notificationId) {
        repository.markNotificationAsRead(notificationId, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchNotifications();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void markAllNotificationsAsRead(Context context) {
        repository.markAllNotificationsAsRead(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Đã xem toàn bộ thông báo!", Toast.LENGTH_SHORT).show();
                    fetchNotifications();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
            }
        });
    }
}