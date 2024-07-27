package sangttph30270.fptpoly.fontend_bookapp_fpoly.notification.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.notification.model.NotificationModel;

import java.util.List;

public interface ApiServiceNotification {
    @GET("notification/notifications")
    Call<List<NotificationModel>> getNotifications();

    @PATCH("notification/notifications/{id}/read")
    Call<Void> markNotificationAsRead(@Path("id") int id);

    @PATCH("notification/notifications/read-all")
    Call<Void> markAllNotificationsAsRead();

}
