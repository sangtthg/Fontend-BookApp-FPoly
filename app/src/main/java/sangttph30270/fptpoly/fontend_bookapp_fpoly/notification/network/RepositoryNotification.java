package sangttph30270.fptpoly.fontend_bookapp_fpoly.notification.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.core.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.notification.model.NotificationModel;

import java.util.List;

import static sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.Common.API_URL;

public class RepositoryNotification {
    private final ApiServiceNotification apiService;

    public RepositoryNotification() {
        apiService = RetrofitManager.createService(ApiServiceNotification.class, API_URL, null);
    }

    public void fetchNotifications(Callback<List<NotificationModel>> callback) {
        Call<List<NotificationModel>> call = apiService.getNotifications();
        call.enqueue(callback);
    }

    public void markNotificationAsRead(int notificationId, Callback<Void> callback) {
        Call<Void> call = apiService.markNotificationAsRead(notificationId);
        call.enqueue(callback);
    }

    public void markAllNotificationsAsRead(Callback<Void> callback) {
        Call<Void> call = apiService.markAllNotificationsAsRead();
        call.enqueue(callback);
    }


}