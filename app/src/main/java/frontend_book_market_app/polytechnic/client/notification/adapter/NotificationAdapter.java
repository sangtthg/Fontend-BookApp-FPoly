package frontend_book_market_app.polytechnic.client.notification.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.notification.model.NotificationModel;
import frontend_book_market_app.polytechnic.client.notification.view.NotificationDetailActivity;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<NotificationModel> notifications = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

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
        String createdAtString = notification.getCreatedAt();


        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date;
        try {
            date = inputFormat.parse(createdAtString);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy '»' HH:mm a");

        outputFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String formattedDate = outputFormat.format(date);
        holder.notificationDate.setText(formattedDate);

        if (!notification.isRead()) {
            holder.linearLayoutParentNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorLightBlue));
            holder.mainNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorLightBlue));
            holder.imgNotification.setBackgroundResource(R.color.colorLightBlue);
            holder.unreadIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.linearLayoutParentNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
            holder.mainNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
            holder.imgNotification.setBackgroundResource(android.R.color.transparent);
            holder.unreadIndicator.setVisibility(View.INVISIBLE);
        }

        //Item Click
        holder.linearLayoutParentNotification.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                holder.imgNotification.setBackgroundResource(android.R.color.transparent);
                holder.linearLayoutParentNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
                holder.mainNotification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
                onItemClickListener.onItemClick(notification);
                holder.unreadIndicator.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(holder.itemView.getContext(), NotificationDetailActivity.class);
                intent.putExtra("type", notification.getType());
                intent.putExtra("title", notification.getTitle());
                intent.putExtra("message", notification.getMessage());
                intent.putExtra("date", formattedDate);
                intent.putExtra("imageUrl", notification.getImageUrl());
                holder.itemView.getContext().startActivity(intent);
            }
        });
        if (notification.getImageUrl() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(notification.getImageUrl())
                    .placeholder(R.drawable.loading_book)
                    .centerCrop()
                    .into(holder.imgNotification);
        } else {
            int errorImageResId;
            if (Objects.equals(notification.getType(), "cancelled")){
                errorImageResId = R.drawable.giao_that_bai;
            } else{
                errorImageResId = R.drawable.courier_1;
            }
            Glide.with(holder.itemView.getContext())
                    .load(errorImageResId)
                    .placeholder(R.drawable.loading_book)
                    .centerCrop()
                    .into(holder.imgNotification);
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

    public interface OnItemClickListener {
        void onItemClick(NotificationModel notification);
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView messageTextView;
        TextView notificationDate;
        CircleImageView imgNotification;
        LinearLayout linearLayoutParentNotification;
        LinearLayout mainNotification;
        View unreadIndicator;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notificationTitle);
            messageTextView = itemView.findViewById(R.id.notificationMessage);
            notificationDate = itemView.findViewById(R.id.notificationDate);
            linearLayoutParentNotification = itemView.findViewById(R.id.linearLayoutParentNotification);
            mainNotification = itemView.findViewById(R.id.mainNotification);
            imgNotification = itemView.findViewById(R.id.imgNotification);
            unreadIndicator = itemView.findViewById(R.id.unreadIndicator);

        }
    }
}