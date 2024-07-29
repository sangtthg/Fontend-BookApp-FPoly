package frontend_book_market_app.polytechnic.client.notification.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import frontend_book_market_app.polytechnic.client.notification.model.NotificationModel;

import java.util.List;

public interface ApiServiceNotification {
    @GET("notification/notifications")
    Call<List<NotificationModel>> getNotifications();

    @PATCH("notification/notifications/{id}/read")
    Call<Void> markNotificationAsRead(@Path("id") int id);

    @PATCH("notification/notifications/read-all")
    Call<Void> markAllNotificationsAsRead();

}
