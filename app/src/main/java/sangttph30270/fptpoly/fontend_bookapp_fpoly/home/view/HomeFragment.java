package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.ItemOffsetDecoration;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerSachBanChay;
    private AdapterSachBanChay adapterSachBanChay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        homeViewModel.getNewBookList().observe(getViewLifecycleOwner(), new Observer<List<HomeBookModel>>() {
            @Override
            public void onChanged(List<HomeBookModel> homeBookModels) {
                if (!homeBookModels.isEmpty()) {
                    Toast.makeText(getActivity(), "First new book title: " + homeBookModels.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeViewModel.getBestSellerBookList().observe(getViewLifecycleOwner(), homeBookModels -> {
            if (!homeBookModels.isEmpty()) {
                Toast.makeText(getActivity(), "First best seller book title: " + homeBookModels.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

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

        recyclerSachBanChay = view.findViewById(R.id.recyclerSachBanChay);
        recyclerSachBanChay.setHasFixedSize(true);
        recyclerSachBanChay.setNestedScrollingEnabled(false);

        int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerSachBanChay.addItemDecoration(new ItemOffsetDecoration(offset));
        recyclerSachBanChay.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        adapterSachBanChay = new AdapterSachBanChay(new ArrayList<>(), position -> {
            Toast.makeText(getActivity(), "Click item at position: " + position, Toast.LENGTH_SHORT).show();
        });

        recyclerSachBanChay.setAdapter(adapterSachBanChay);

        view.findViewById(R.id.myButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.fetchHomeBookAPI();
            }
        });



        homeViewModel.getBestSellerBookList().observe(getViewLifecycleOwner(), new Observer<List<HomeBookModel>>() {
            @Override
            public void onChanged(List<HomeBookModel> homeBookModels) {
                if (!homeBookModels.isEmpty()) {
                    Toast.makeText(getActivity(), "First new book title: " + homeBookModels.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                    adapterSachBanChay.updateData(homeBookModels);
                }
            }
        });


    }
}