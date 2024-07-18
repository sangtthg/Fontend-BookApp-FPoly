package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.RecyclerViewUtil;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerSachBanChay;
    private RecyclerView recyclerSachMoiCapNhat;
    private RecyclerView recyclerSachRanDom;
    private RecyclerView recyclerNhieuLuotXemNhat;

    private AdapterSachBanChay adapterSachBanChay;
    private AdapterSachHome adapterSachMoiCapNhat;
    private AdapterSachHome adapterSachRanDom;
    private AdapterSachHome adapterNhieuLuotXemNhat;

    private SwipeRefreshLayout swipeRefreshLayout;
    SkeletonAdapter skeletonAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        homeViewModel.fetchHomeBookAPI();
        //Test
        homeViewModel.fetchBookDetail(14, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywicm9sZSI6InVzZXIiLCJjcmVhdGVkX2F0IjoxNzIxMzE1MTQyNzIyLCJpYXQiOjE3MjEzMTUxNDJ9.gWF3paeaGIhuBshIix2wKFwU-iX7OKxRKTvAjkt8L_k");

        setupSearchView(view);
        initView(view);
        initRecyclerView();
        setupSwipeRefresh(view);

        observeViewModel();
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
    }

    private void initRecyclerView() {
        int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        skeletonAdapter = new SkeletonAdapter(9);

        //--
        adapterSachBanChay = new AdapterSachBanChay(new ArrayList<>(), new AdapterSachBanChay.OnItemClickListener() {
            @Override
            public void onItemClick(String bookName) {
                Toast.makeText(HomeFragment.this.getActivity(), "Book name: " + bookName, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerViewUtil.setupLinear(getActivity(), recyclerSachBanChay, offset, adapterSachBanChay);


        //--
        adapterNhieuLuotXemNhat = new AdapterSachHome(new ArrayList<>(), new AdapterSachHome.OnItemClickListener() {
            @Override
            public void onItemClick(String bookName) {
                Toast.makeText(HomeFragment.this.getActivity(), "Book name: " + bookName, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerViewUtil.setupLinear(getActivity(), recyclerNhieuLuotXemNhat, offset, adapterNhieuLuotXemNhat);


        //--
        adapterSachMoiCapNhat = new AdapterSachHome(new ArrayList<>(), new AdapterSachHome.OnItemClickListener() {
            @Override
            public void onItemClick(String bookName) {
                Toast.makeText(HomeFragment.this.getActivity(), "Book name: " + bookName, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerViewUtil.setupLinear(getActivity(), recyclerSachMoiCapNhat, offset, adapterSachMoiCapNhat);

        //--
        adapterSachRanDom = new AdapterSachHome(new ArrayList<>(), new AdapterSachHome.OnItemClickListener() {
            @Override
            public void onItemClick(String bookName) {
                Toast.makeText(HomeFragment.this.getActivity(), "Book name: " + bookName, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerViewUtil.setupGrid(getActivity(), recyclerSachRanDom, offset, 3, adapterSachRanDom);


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

//            homeViewModel.fetchBookDetail(14, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywicm9sZSI6InVzZXIiLCJjcmVhdGVkX2F0IjoxNzIxMzE1MTQyNzIyLCJpYXQiOjE3MjEzMTUxNDJ9.gWF3paeaGIhuBshIix2wKFwU-iX7OKxRKTvAjkt8L_k");
//            swipeRefreshLayout.setRefreshing(false);

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
}
