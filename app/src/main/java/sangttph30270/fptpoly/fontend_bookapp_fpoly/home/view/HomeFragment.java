package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

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

        Button buttonClick = view.findViewById(R.id.button_click);
        buttonClick.setOnClickListener(v -> homeViewModel.fetchHomeBookAPI());
    }
}