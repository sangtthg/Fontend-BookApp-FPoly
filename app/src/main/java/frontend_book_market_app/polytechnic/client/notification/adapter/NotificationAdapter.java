package frontend_book_market_app.polytechnic.client.notification.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.notification.model.NotificationModel;
import frontend_book_market_app.polytechnic.client.utils.DateUtils;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<NotificationModel> notifications = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(NotificationModel notification);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notification = notifications.get(position);
        holder.titleTextView.setText(notification.getTitle());
        holder.messageTextView.setText(notification.getMessage());
        holder.notificationDate.setText(DateUtils.formatDate(notification.getCreatedAt(), "dd-MM-yyyy  HH:mm"));
        if (!notification.isRead()) {
            holder.linearLayoutParentNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorLightBlue));
        } else {
            holder.linearLayoutParentNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
        }

        holder.linearLayoutParentNotification.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                holder.linearLayoutParentNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
                onItemClickListener.onItemClick(notification);
            }
        });

        if (notification.getImageUrl() != null){
            Glide.with(holder.itemView.getContext())
                    .load(notification.getImageUrl())
                    .placeholder(R.drawable.loading_book)
                    .centerCrop()
                    .into(holder.imgNotification);
        } else {
            holder.imgNotification.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(List<NotificationModel> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView messageTextView;
        TextView notificationDate;
        ImageView imgNotification;
        LinearLayout linearLayoutParentNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notificationTitle);
            messageTextView = itemView.findViewById(R.id.notificationMessage);
            notificationDate = itemView.findViewById(R.id.notificationDate);
            linearLayoutParentNotification = itemView.findViewById(R.id.linearLayoutParentNotification);
            imgNotification = itemView.findViewById(R.id.imgNotification);
        }
    }
}