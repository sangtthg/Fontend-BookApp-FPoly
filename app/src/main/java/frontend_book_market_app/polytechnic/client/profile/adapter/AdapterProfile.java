package frontend_book_market_app.polytechnic.client.profile.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.order_user.view.DonHangActivity;
import frontend_book_market_app.polytechnic.client.profile.model.ProfileModel;
import frontend_book_market_app.polytechnic.client.profile.view.AddressListActivity;
import frontend_book_market_app.polytechnic.client.profile.view.ChangePasswordActivity;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ProfileViewHolder> {

    private List<ProfileModel> profileList;
    private OnLogoutClickListener onLogoutClickListener;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private Fragment fragment; // Change to Fragment reference
    private OnImageSelectedListener onImageSelectedListener;

    public AdapterProfile(List<ProfileModel> profileList, OnLogoutClickListener onLogoutClickListener, SharedPreferencesHelper sharedPreferencesHelper, Fragment fragment, OnImageSelectedListener onImageSelectedListener) {
        this.profileList = profileList;
        this.onLogoutClickListener = onLogoutClickListener;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.fragment = fragment; // Initialize Fragment reference
        this.onImageSelectedListener = onImageSelectedListener;
    }
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_profile, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        ProfileModel profile = profileList.get(position);
        holder.txtTenNguoiDung.setText(profile.getUsername());
        holder.txtEmail.setText(profile.getEmail());

        holder.txtDoiMatKhau.setOnClickListener(view -> checkLoginStatus(view.getContext(), () -> {
            Intent intent = new Intent(view.getContext(), ChangePasswordActivity.class);
            view.getContext().startActivity(intent);
        }));

        String value = profile.getEmail();
        if (value.length() > 5) {
            int atPosition = value.indexOf("@");
            if (atPosition > 2) {
                String maskedEmail = value.substring(0, 2) + "******" + value.substring(atPosition - 2);
                holder.txtEmail.setText(maskedEmail);
            } else {
                holder.txtEmail.setText(value);
            }
        } else {
            holder.txtEmail.setText(value);
        }

        String avatarUrl = profile.getAvatar();
//        Glide.with(holder.itemView.getContext())
//                .load(avatarUrl != null && !avatarUrl.isEmpty() ? avatarUrl : R.drawable.avatar)
//                .placeholder(R.drawable.avatar)
//                .error(R.drawable.avatar)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))  // Apply rounded corners
//                .into(holder.imgAvatar);

//        holder.imgChangeAvatar.setOnClickListener(view -> checkLoginStatus(view.getContext(), () -> {
//            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            fragment.startActivityForResult(intent, 100); // Use the Fragment reference
//        }));



        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token != null && !token.isEmpty()) {
            holder.btnLogoutProfile.setText("Đăng xuất");
            holder.btnLogoutProfile.setOnClickListener(view -> showLogoutDialog(view.getContext(), holder));
        } else {
            holder.btnLogoutProfile.setText("Đăng nhập");
            holder.btnLogoutProfile.setOnClickListener(view -> showLoginDialog(view.getContext()));
            holder.itemView.setOnClickListener(view -> checkLoginStatus(view.getContext(), () -> {
                Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
            }));
        }

        holder.txtDiaChi.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddressListActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public void updateProfileList(List<ProfileModel> newProfileList) {
        profileList.clear();
        profileList.addAll(newProfileList);
        notifyDataSetChanged();
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenNguoiDung, txtEmail;
        TextView txtDoiMatKhau, txtDiaChi, btnLogoutProfile;
        ImageView imgAvatar, imgChangeAvatar;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDoiMatKhau = itemView.findViewById(R.id.txtDoiMatKhau);
            txtTenNguoiDung = itemView.findViewById(R.id.txtTenNguoiDung);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtEmail = itemView.findViewById(R.id.txtEmail);
//            imgAvatar = itemView.findViewById(R.id.imgAvatar);
//            imgChangeAvatar = itemView.findViewById(R.id.imgChangeAvatar);
            btnLogoutProfile = itemView.findViewById(R.id.btnLogoutProfile);
        }
    }

    public interface OnLogoutClickListener {
        void onLogoutClick();
    }
    public interface OnImageSelectedListener {
        void onImageSelected(Uri imageUri);
    }
    private void checkLoginStatus(Context context, Runnable onSuccess) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token == null || token.isEmpty()) {
            showLoginDialog(context);
        } else {
            onSuccess.run();
        }
    }

    private void showLoginDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_custom_login, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

        AppCompatButton btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancel);
        AppCompatButton btnDialogOk = dialogView.findViewById(R.id.btnDialogOk);
        btnDialogCancel.setOnClickListener(v -> dialog.dismiss());
        btnDialogOk.setOnClickListener(v -> {
            Intent intent = new Intent(context, LoginScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });

        dialog.show();
    }

    private void showLogoutDialog(Context context, ProfileViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_logout_prompt, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

        AppCompatButton btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancel);
        AppCompatButton btnDialogOk = dialogView.findViewById(R.id.btnDialogOk);
        btnDialogCancel.setOnClickListener(v -> dialog.dismiss());
        btnDialogOk.setOnClickListener(v -> {
            holder.btnLogoutProfile.setText("Đăng nhập");
            if (onLogoutClickListener != null) {
                onLogoutClickListener.onLogoutClick();
            }
            dialog.dismiss();
        });

        dialog.show();
    }



}
