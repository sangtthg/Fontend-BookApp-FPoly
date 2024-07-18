package sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import de.hdodenhof.circleimageview.CircleImageView;

import androidx.fragment.app.Fragment;


import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;

public class ProfileFragment extends Fragment {
    CircleImageView imgProfile;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        String avatarUrl = sharedPreferencesHelper.getAvatar();
//        if (avatarUrl != null && !avatarUrl.isEmpty()) {
//            Picasso.get()
//                    .load(avatarUrl) // Replace with avatar URL from SharedPreferences
//                    .placeholder(R.drawable.ic_launcher_background) // Placeholder image while loading
//                    .error(R.drawable.ic_launcher_background) // Error image if loading fails
//                    .into(imgProfile);
//        } else {
//            // Load a default image if avatar URL is not available
//            Picasso.get()
//                    .load(R.drawable.ic_launcher_background)
//                    .into(imgProfile);
//        }

        // Log user information

        return view;
    }

    private void logUserInfo() {
        int userId = sharedPreferencesHelper.getUserId();
        String username = sharedPreferencesHelper.getUsername();
        String email = sharedPreferencesHelper.getEmail();
        String avatar = sharedPreferencesHelper.getAvatar();
        String authToken = sharedPreferencesHelper.getAuthToken();
        String resetCode = sharedPreferencesHelper.getResetCode();
        int userStatus = sharedPreferencesHelper.getUserStatus();
        String role = sharedPreferencesHelper.getRole();
        String token = sharedPreferencesHelper.getToken();

        Log.d("ProfileFragment", "UserID: " + userId);
        Log.d("ProfileFragment", "Username: " + username);
        Log.d("ProfileFragment", "Email: " + email);
        Log.d("ProfileFragment", "Avatar: " + avatar);
        Log.d("ProfileFragment", "AuthToken: " + authToken);
        Log.d("ProfileFragment", "ResetCode: " + resetCode);
        Log.d("ProfileFragment", "UserStatus: " + userStatus);
        Log.d("ProfileFragment", "Role: " + role);
        Log.d("ProfileFragment", "Token: " + token);
    }
}
