package sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.view.LoginScreen;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.view.DonHangActivity;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.model.ProfileModel;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ProfileViewHolder> {

    private List<ProfileModel> profileList;
    private OnLogoutClickListener onLogoutClickListener;

    public AdapterProfile(List<ProfileModel> profileList, OnLogoutClickListener onLogoutClickListener) {
        this.profileList = profileList;
        this.onLogoutClickListener = onLogoutClickListener;
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
        holder.txtDiaChi.setText(profile.getDefault_address());
        holder.txtTenNguoiDung.setText(profile.getUsername());
        holder.txtEmail.setText(profile.getEmail());
        holder.txtDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginStatus(view.getContext(), () -> {
                    Intent intent = new Intent(view.getContext(), ChangePasswordActivity.class);
                    view.getContext().startActivity(intent);
                });
            }
        });
        holder.txtHoSoCuaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginStatus(view.getContext(), () -> {
                    Toast.makeText(view.getContext(), "Hồ sơ của tôi", Toast.LENGTH_SHORT).show();
                });
            }
        });

        // Mask email
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

        // Load avatar
        String avatarUrl = profile.getAvatar();
        Glide.with(holder.itemView.getContext())
                .load(avatarUrl != null && !avatarUrl.isEmpty() ? avatarUrl : R.drawable.avatar)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))  // Apply rounded corners
                .into(holder.imgAvatar);

        holder.imgChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginStatus(view.getContext(), () -> {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((Activity) view.getContext()).startActivityForResult(intent, 100); // 100 is a request code
                });
            }
        });

        holder.linearLayoutDonHangCuaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DonHangActivity.class);
                context.startActivity(intent);
            }
        });

        // Check token and set button behavior
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
        TextView txtDoiMatKhau, txtHoSoCuaToi, txtDiaChi;
        ImageView imgAvatar, imgChangeAvatar;
        Button btnLogoutProfile;
        LinearLayout linearLayoutDonHangCuaToi;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHoSoCuaToi = itemView.findViewById(R.id.txtHoSoCuaToi);
            txtDoiMatKhau = itemView.findViewById(R.id.txtDoiMatKhau);
            txtTenNguoiDung = itemView.findViewById(R.id.txtTenNguoiDung);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgChangeAvatar = itemView.findViewById(R.id.imgChangeAvatar);
            btnLogoutProfile = itemView.findViewById(R.id.btnLogoutProfile);
            linearLayoutDonHangCuaToi = itemView.findViewById(R.id.linearLayoutDonHangCuaToi);
        }
    }

    public interface OnLogoutClickListener {
        void onLogoutClick();
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
