package frontend_book_market_app.polytechnic.client.home.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeImageView;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapteHomerSachBanChay;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapteHomerSach;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapteHomerSachHomeGoiY;
import frontend_book_market_app.polytechnic.client.home.adapter.CategoryAdapter;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.search.view.SearchActivity;
import frontend_book_market_app.polytechnic.client.utils.RecyclerViewUtil;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerSachBanChay;
    private RecyclerView recyclerSachMoiCapNhat;
    private RecyclerView recyclerSachRanDom;
    private RecyclerView recyclerNhieuLuotXemNhat;
    private RecyclerView categoryRecyclerView;

    private AdapteHomerSachBanChay adapteHomerSachBanChay;
    private AdapteHomerSach adapterSachMoiCapNhat;
    private AdapteHomerSachHomeGoiY adapterSachRanDom;
    private AdapteHomerSach adapterNhieuLuotXemNhat;

    private SwipeRefreshLayout swipeRefreshLayout;
    SkeletonAdapter skeletonAdapter;
    private ImageSlider imageSlider2;


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
        sharedPreferencesHelper = new SharedPreferencesHelper(null);

        homeViewModel.fetchHomeBookAPI();
        setupCategoryRecyclerView(view);

        setupSearchView(view);
        initView(view);
        imageSl(view);
        initRecyclerView();
        setupSwipeRefresh(view);
        observeViewModel(view);



    }

    private void imageSl(View view) {
        //Model slider1
        List<SlideModel> slideModelMain1 = new ArrayList<>();
        slideModelMain1.add(new SlideModel(R.drawable.img1, "", ScaleTypes.FIT));
        slideModelMain1.add(new SlideModel(R.drawable.img2, "", ScaleTypes.FIT));
        slideModelMain1.add(new SlideModel(R.drawable.img3, "", ScaleTypes.FIT));
        slideModelMain1.add(new SlideModel(R.drawable.img4, "", ScaleTypes.FIT));
        slideModelMain1.add(new SlideModel(R.drawable.img5, "", ScaleTypes.FIT));
        slideModelMain1.add(new SlideModel(R.drawable.img6, "", ScaleTypes.FIT));

        ImageSlider imageSlider = view.findViewById(R.id.slider);
        imageSlider.setImageList(slideModelMain1, ScaleTypes.FIT);

    }


    private void initView(View view) {
        recyclerSachBanChay = view.findViewById(R.id.recyclerSachBanChay);
        recyclerSachMoiCapNhat = view.findViewById(R.id.recyclerSachMoiCapNhat);
        recyclerSachRanDom = view.findViewById(R.id.recyclerSachRanDom);
        recyclerNhieuLuotXemNhat = view.findViewById(R.id.recyclerNhieuLuotXemNhat);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        nestedScrollView = view.findViewById(R.id.nestedScrollViewHome);
        nestedScrollView = view.findViewById(R.id.nestedScrollViewHome);
        ImageView imgCup = view.findViewById(R.id.imgCup);
        ImageView imgShoppingBag1 = view.findViewById(R.id.imgShoppingBag1);
        Glide.with(this).load(R.drawable.sun).centerCrop().into(imgCup);
        Glide.with(this).load(R.drawable.shopping1_bag).centerCrop().into(imgShoppingBag1);
    }

    private void initRecyclerView() {
        int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        skeletonAdapter = new SkeletonAdapter(9);

        AdapteHomerSachBanChay.OnItemClickListener itemClickListener1 = bookID -> {
            Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
            intent.putExtra("bookID", bookID);
            startActivity(intent);
        };

        AdapteHomerSach.OnItemClickListener itemClickListener2 = bookID -> {
            Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
            intent.putExtra("bookID", bookID);
            startActivity(intent);
        };

        AdapteHomerSachHomeGoiY.OnItemClickListener itemClickListenerHomeGoiY = bookID -> {
            Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
            intent.putExtra("bookID", bookID);
            startActivity(intent);
        };

        adapteHomerSachBanChay = new AdapteHomerSachBanChay(new ArrayList<>(), itemClickListener1);
        adapterNhieuLuotXemNhat = new AdapteHomerSach(new ArrayList<>(), itemClickListener2);
        adapterSachMoiCapNhat = new AdapteHomerSach(new ArrayList<>(), itemClickListener2);
        adapterSachRanDom = new AdapteHomerSachHomeGoiY(new ArrayList<>(), itemClickListenerHomeGoiY);

        RecyclerViewUtil.setupLinear(getActivity(), recyclerSachBanChay, offset, adapteHomerSachBanChay);
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
            homeViewModel.fetchTotalItemInCart();
        });
    }

    private void observeViewModel(View view) {
        homeViewModel.getBestSellerBookList().observe(getViewLifecycleOwner(), homeBookModels -> {
            if (homeBookModels != null && !homeBookModels.isEmpty()) {
                adapteHomerSachBanChay.updateData(homeBookModels);
                recyclerSachBanChay.setAdapter(adapteHomerSachBanChay);
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

        homeViewModel.getCartItemCount().observe(getViewLifecycleOwner(), itemCount -> {
            Log.d("HomeFragment", "Updating badge count: " + itemCount);
//            new QBadgeView(getContext())
//                    .bindTarget(view.findViewById(R.id.btnCart))
//                    .setBadgeNumber(itemCount)
//                    .setBadgeBackgroundColor(Color.RED)
//                    .setShowShadow(false)
//                    .setBadgeTextColor(Color.WHITE)
//                    .setGravityOffset(0, -1, true)
//                    .setBadgeGravity(Gravity.END | Gravity.TOP);
        });

        homeViewModel.getCartItemCount().observe(getViewLifecycleOwner(), itemCount -> {
            Log.d("HomeFragment", "Updating badge count: " + itemCount);
            @SuppressLint("WrongViewCast") BGABadgeImageView badgeImageView = view.findViewById(R.id.btnCart); // Thay đổi này
            badgeImageView.showCirclePointBadge();
            badgeImageView.showTextBadge(String.valueOf(itemCount));
            badgeImageView.getBadgeViewHelper().setBadgeBgColorInt(Color.RED);
            badgeImageView.getBadgeViewHelper().setBadgeTextColorInt(Color.WHITE);
        });

        view.findViewById(R.id.btnCart).setOnClickListener(v -> {
            if (checkUserLogin(view)) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
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
        homeViewModel.fetchTotalItemInCart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            homeViewModel.fetchTotalItemInCart();
        }
    }

    //-----------------------------CONFIG INFO----------------------------------

    private void setupSearchView(View view) {
        SearchView searchView = view.findViewById(R.id.search_view);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(true); // Đưa SearchView về trạng thái bị thu gọn
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
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

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setFocusable(false);
                searchView.setIconified(true);
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
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

    private boolean checkUserLogin(View view) {
        String token = sharedPreferencesHelper.getToken();
        if (token == null || token.isEmpty()) {
            // User is not logged in
            Log.d("HomeFragment", "User is not logged in.");
            promptLogin();
            return false;
        } else {
            // User is logged in
            Log.d("HomeFragment", "User is logged in.");
            displayUserData(view);
            return true;
        }
    }

    private void displayUserData(View view) {
        String[] userData = {
                sharedPreferencesHelper.getUsername(),
                sharedPreferencesHelper.getEmail(),
                sharedPreferencesHelper.getAvatar(),
                sharedPreferencesHelper.getAuthToken(),
                sharedPreferencesHelper.getResetCode(),
                String.valueOf(sharedPreferencesHelper.getUserStatus()),
                sharedPreferencesHelper.getRole(),
                sharedPreferencesHelper.getToken(),
                sharedPreferencesHelper.getDefaultAddress()
        };

        String[] labels = {
                "Username", "Email", "Avatar", "AuthToken", "ResetCode",
                "UserStatus", "Role", "Token", "Address"
        };

        for (int i = 0; i < userData.length; i++) {
            Log.d("SharedPreferencesHelper", labels[i] + ": " + userData[i]);
        }
    }
}
