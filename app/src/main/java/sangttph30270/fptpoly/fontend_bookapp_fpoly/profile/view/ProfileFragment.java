package sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.model.ProfileModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;

public class ProfileFragment extends Fragment {
    private SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());

        List<ProfileModel> profileList = new ArrayList<>();
        profileList.add(new ProfileModel(
                sharedPreferencesHelper.getUserId(),
                sharedPreferencesHelper.getUsername(),
                sharedPreferencesHelper.getEmail(),
                sharedPreferencesHelper.getAvatar(),
                sharedPreferencesHelper.getAuthToken(),
                sharedPreferencesHelper.getResetCode(),
                sharedPreferencesHelper.getUserStatus(),
                sharedPreferencesHelper.getRole(),
                sharedPreferencesHelper.getToken()
        ));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfile);
        AdapterProfile adapter = new AdapterProfile(profileList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
