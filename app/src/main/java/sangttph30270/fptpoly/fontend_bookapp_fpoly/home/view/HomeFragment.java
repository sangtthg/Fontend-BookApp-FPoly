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

import java.util.ArrayList;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.RecyclerViewUtil;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerSachBanChay;
    private RecyclerView recyclerSachMoiCapNhat;
    private AdapterSachBanChay adapterSachBanChay;
    private AdapterSachHome adapterSachMoiCapNhat;
    private SkeletonAdapter skeletonAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        homeViewModel.fetchHomeBookAPI();

        setupSearchView(view);
        initView(view);
        initRecyclerView();

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
    }

    private void initRecyclerView() {
        int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);

        skeletonAdapter = new SkeletonAdapter(5);

        adapterSachBanChay = new AdapterSachBanChay(new ArrayList<>(), bookName -> {
            Toast.makeText(getActivity(), "Book name: " + bookName, Toast.LENGTH_SHORT).show();
        });
        RecyclerViewUtil.setupLinear(getActivity(), recyclerSachBanChay, offset, adapterSachBanChay);


        adapterSachMoiCapNhat = new AdapterSachHome(new ArrayList<>(), bookName -> {
            Toast.makeText(getActivity(), "Book name: " + bookName, Toast.LENGTH_SHORT).show();
        });
        RecyclerViewUtil.setupLinear(getActivity(), recyclerSachMoiCapNhat, offset, adapterSachMoiCapNhat);

        recyclerSachBanChay.setAdapter(skeletonAdapter);
        recyclerSachMoiCapNhat.setAdapter(skeletonAdapter);
    }

    private void observeViewModel() {
        homeViewModel.getBestSellerBookList().observe(getViewLifecycleOwner(), homeBookModels -> {
            if (homeBookModels != null && !homeBookModels.isEmpty()) {
                adapterSachBanChay.updateData(homeBookModels);
                recyclerSachBanChay.setAdapter(adapterSachBanChay);
            }
        });

        homeViewModel.getNewBookList().observe(getViewLifecycleOwner(), homeBookModels -> {
            if (homeBookModels != null && !homeBookModels.isEmpty()) {
                adapterSachMoiCapNhat.updateData(homeBookModels);
                recyclerSachMoiCapNhat.setAdapter(adapterSachMoiCapNhat);
            }
        });
    }
}
