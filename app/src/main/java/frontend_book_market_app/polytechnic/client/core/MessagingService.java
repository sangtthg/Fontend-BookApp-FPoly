package frontend_book_market_app.polytechnic.client.core;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.notification.view.NotificationDetailActivity;
import frontend_book_market_app.polytechnic.client.utils.DateUtils;

public class MessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "all";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification = message.getNotification();
        long timestamp = System.currentTimeMillis();

        if (notification != null) {
            String imageUrl = notification.getImageUrl() != null ? String.valueOf(notification.getImageUrl().isHierarchical()) : null;
            getFirebaseMessage(notification.getTitle(), notification.getBody(), imageUrl, timestamp);
        } else {
            Log.e("MyFirebaseMessagingService", "Notification ERROR");
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("MyFirebaseMessagingService", "Refreshed token: " + token);
    }

    private void getFirebaseMessage(String title, String body, String image, long timestamp) {
        String formattedDate = DateUtils.formatTimestamp(timestamp);

        Intent intent = new Intent(this, NotificationDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("message", body);
        intent.putExtra("imageUrl", image);
        intent.putExtra("date", formattedDate);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_cart)
                .setContentTitle(title != null ? title : "")
                .setContentText(body != null ? body : "")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            managerCompat.notify(102, builder.build());
        } else {
            Log.e("MyFirebaseMessagingService", "Notification permission not granted");
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Channel";
            String description = "Channel for app notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("FirebaseLogs", "Fetching token failed", task.getException());
                return;
            }

            String token = task.getResult();

            Log.v("FirebaseLogs", "Device Token: " + token);
        });
    }
}