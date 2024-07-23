package sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.view.LoginScreen;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.model.ProfileModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.viewmodel.ProfileViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;

public class ProfileFragment extends Fragment implements AdapterProfile.OnLogoutClickListener{
    private SharedPreferencesHelper sharedPreferencesHelper;
    private AdapterProfile adapter;
    private ProfileViewModel profileViewModel;
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
        logUserInfo();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfile);
         adapter = new AdapterProfile(profileList, (AdapterProfile.OnLogoutClickListener) this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


    @Override
    public void onLogoutClick() {
        sharedPreferencesHelper.clear();
        loadProfileData(); // Cập nhật dữ liệu khi đăng xuất


    }
    private void loadProfileData() {
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
        adapter.updateProfileList(profileList);
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
