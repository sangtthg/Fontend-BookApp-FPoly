package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.view.LoginScreen;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterSachBanChay;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterSachHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterSachHomeGoiY;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.CategoryAdapter;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.RecyclerViewUtil;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerSachBanChay;
    private RecyclerView recyclerSachMoiCapNhat;
    private RecyclerView recyclerSachRanDom;
    private RecyclerView recyclerNhieuLuotXemNhat;
    private RecyclerView categoryRecyclerView;

    private AdapterSachBanChay adapterSachBanChay;
    private AdapterSachHome adapterSachMoiCapNhat;
    private AdapterSachHomeGoiY adapterSachRanDom;
    private AdapterSachHome adapterNhieuLuotXemNhat;

    private SwipeRefreshLayout swipeRefreshLayout;
    SkeletonAdapter skeletonAdapter;

    private int scrollPosition = 0;
    private NestedScrollView nestedScrollView;
    private SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());

        homeViewModel.fetchHomeBookAPI();
        setupCategoryRecyclerView(view);

        setupSearchView(view);
        initView(view);
        initRecyclerView();
        setupSwipeRefresh(view);
        observeViewModel();
//        displayUserData(view);

        checkUserLogin(view);
    }
    private void checkUserLogin(View view) {
        String token = sharedPreferencesHelper.getToken();
        if (token == null || token.isEmpty()) {
            // User is not logged in
            Log.d("HomeFragment", "User is not logged in.");
            displayUserData(view);
        } else {
            // User is logged in
            Log.d("HomeFragment", "User is logged in.");
            displayUserData(view);
        }
    }
    private void displayUserData(View view) {
        String username = sharedPreferencesHelper.getUsername();
        String email = sharedPreferencesHelper.getEmail();
        String avatar = sharedPreferencesHelper.getAvatar();
        String authToken = sharedPreferencesHelper.getAuthToken();
        String resetCode = sharedPreferencesHelper.getResetCode();
        int userStatus = sharedPreferencesHelper.getUserStatus();
        String role = sharedPreferencesHelper.getRole();
        String token = sharedPreferencesHelper.getToken();

        // Log dữ liệu
        Log.d("SharedPreferencesHelper", "Username: " + username);
        Log.d("SharedPreferencesHelper", "Email: " + email);
        Log.d("SharedPreferencesHelper", "Avatar: " + avatar);
        Log.d("SharedPreferencesHelper", "AuthToken: " + authToken);
        Log.d("SharedPreferencesHelper", "ResetCode: " + resetCode);
        Log.d("SharedPreferencesHelper", "UserStatus: " + userStatus);
        Log.d("SharedPreferencesHelper", "Role: " + role);
        Log.d("SharedPreferencesHelper", "Token: " + token);
    }


    private void setupSearchView(View view) {
        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), "Search for: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initView(View view) {
        recyclerSachBanChay = view.findViewById(R.id.recyclerSachBanChay);
        recyclerSachMoiCapNhat = view.findViewById(R.id.recyclerSachMoiCapNhat);
        recyclerSachRanDom = view.findViewById(R.id.recyclerSachRanDom);
        recyclerNhieuLuotXemNhat = view.findViewById(R.id.recyclerNhieuLuotXemNhat);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        nestedScrollView = view.findViewById(R.id.nestedScrollViewHome);
    }

    private void initRecyclerView() {
        int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        skeletonAdapter = new SkeletonAdapter(9);

        AdapterSachBanChay.OnItemClickListener itemClickListener1 = bookID -> {
            if (isUserLoggedIn()) {
                Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                intent.putExtra("bookID", bookID);
                startActivity(intent);
            } else {
                promptLogin();
            }
        };

        AdapterSachHome.OnItemClickListener itemClickListener2 = bookID -> {
            if (isUserLoggedIn()) {
                Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                intent.putExtra("bookID", bookID);
                startActivity(intent);
            } else {
                promptLogin();
            }
        };

        AdapterSachHomeGoiY.OnItemClickListener itemClickListenerHomeGoiY = bookID -> {
            if (isUserLoggedIn()) {
                Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                intent.putExtra("bookID", bookID);
                startActivity(intent);
            } else {
                promptLogin();
            }
        };

        adapterSachBanChay = new AdapterSachBanChay(new ArrayList<>(), itemClickListener1);
        adapterNhieuLuotXemNhat = new AdapterSachHome(new ArrayList<>(), itemClickListener2);
        adapterSachMoiCapNhat = new AdapterSachHome(new ArrayList<>(), itemClickListener2);
        adapterSachRanDom = new AdapterSachHomeGoiY(new ArrayList<>(), itemClickListenerHomeGoiY);

        RecyclerViewUtil.setupLinear(getActivity(), recyclerSachBanChay, offset, adapterSachBanChay);
        RecyclerViewUtil.setupLinear(getActivity(), recyclerNhieuLuotXemNhat, offset, adapterNhieuLuotXemNhat);
        RecyclerViewUtil.setupLinear(getActivity(), recyclerSachMoiCapNhat, offset, adapterSachMoiCapNhat);
        RecyclerViewUtil.setupGrid(getActivity(), recyclerSachRanDom, offset, 2, adapterSachRanDom);

        recyclerSachBanChay.setAdapter(skeletonAdapter);
        recyclerNhieuLuotXemNhat.setAdapter(skeletonAdapter);
        recyclerSachMoiCapNhat.setAdapter(skeletonAdapter);
        recyclerSachRanDom.setAdapter(skeletonAdapter);
    }

    private void setupSwipeRefresh(View view) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            homeViewModel.clearAllLists();
            recyclerSachBanChay.setAdapter(skeletonAdapter);
            recyclerNhieuLuotXemNhat.setAdapter(skeletonAdapter);
            recyclerSachMoiCapNhat.setAdapter(skeletonAdapter);
            recyclerSachRanDom.setAdapter(skeletonAdapter);
            homeViewModel.fetchHomeBookAPI();
            swipeRefreshLayout.setRefreshing(false);

            homeViewModel.fetchBookDetail(14);

        });
    }

    private void observeViewModel() {
        homeViewModel.getBestSellerBookList().observe(getViewLifecycleOwner(), homeBookModels -> {
            if (homeBookModels != null && !homeBookModels.isEmpty()) {
                adapterSachBanChay.updateData(homeBookModels);
                recyclerSachBanChay.setAdapter(adapterSachBanChay);
            }
        });

        homeViewModel.getMostViewBooksList().observe(getViewLifecycleOwner(), homeBookModels -> {
            if (homeBookModels != null && !homeBookModels.isEmpty()) {
                adapterNhieuLuotXemNhat.updateData(homeBookModels);
                recyclerNhieuLuotXemNhat.setAdapter(adapterNhieuLuotXemNhat);
            }
        });

        homeViewModel.getNewBookList().observe(getViewLifecycleOwner(), homeBookModels -> {
            if (homeBookModels != null && !homeBookModels.isEmpty()) {
                adapterSachMoiCapNhat.updateData(homeBookModels);
                recyclerSachMoiCapNhat.setAdapter(adapterSachMoiCapNhat);
            }
        });

        homeViewModel.getRandomBookList().observe(getViewLifecycleOwner(), homeBookModels -> {
            if (homeBookModels != null && !homeBookModels.isEmpty()) {
                adapterSachRanDom.updateData(homeBookModels);
                recyclerSachRanDom.setAdapter(adapterSachRanDom);
            }
        });
    }

    private void setupCategoryRecyclerView(View view) {
        RecyclerView categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter adapter = new CategoryAdapter(getContext(), position -> {
            Toast.makeText(getContext(), "position" + position, Toast.LENGTH_SHORT).show();
        });
        categoryRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        scrollPosition = nestedScrollView.getScrollY();
    }

    @Override
    public void onResume() {
        super.onResume();
        nestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.scrollTo(0, scrollPosition);
            }
        });
    }

    private void promptLogin() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_custom_login, null);

        Button btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancel);
        Button btnDialogOk = dialogView.findViewById(R.id.btnDialogOk);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        btnDialogOk.setOnClickListener(v -> {
            // Chuyển hướng đến màn hình đăng nhập
            Intent intent = new Intent(getActivity(), LoginScreen.class);
            startActivity(intent);
            dialog.dismiss();
        });

        btnDialogCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private boolean isUserLoggedIn() {
        String authToken = sharedPreferencesHelper.getToken();
        return authToken != null && !authToken.isEmpty();
    }
}
