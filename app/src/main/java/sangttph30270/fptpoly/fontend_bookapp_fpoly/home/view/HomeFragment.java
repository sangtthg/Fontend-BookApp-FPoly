package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        homeViewModel.getNewBookList().observe(this, homeBookModels -> {
            if (!homeBookModels.isEmpty()) {
                Toast.makeText(getActivity(), "First new book title: " + homeBookModels.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        homeViewModel.getBestSellerBookList().observe(this, homeBookModels -> {
            if (!homeBookModels.isEmpty()) {
                Toast.makeText(getActivity(), "First best seller book title: " + homeBookModels.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button buttonClick = view.findViewById(R.id.button_click);

        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.fetchFirstApiProducts();
            }
        });

        return view;
    }
}
