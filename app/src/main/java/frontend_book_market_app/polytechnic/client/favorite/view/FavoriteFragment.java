package frontend_book_market_app.polytechnic.client.favorite.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.order_user.view.ViewPagerAdapter;

public class FavoriteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Window window = getActivity().getWindow();
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            TextView tabTextView = new TextView(requireContext());
            tabTextView.setTypeface(typeface, Typeface.NORMAL);
            tabTextView.setGravity(Gravity.CENTER);
            tabTextView.setSingleLine(true);

            switch (position) {
                case 0:
                    tabTextView.setText("Chưa thanh toán");
                    break;
                case 1:
                    tabTextView.setText("Chờ vận chuyển");
                    break;
                case 2:
                    tabTextView.setText("Đã giao hàng");
                    break;
                case 3:
                    tabTextView.setText("Đơn đã huỷ");
                    break;
            }
            tab.setCustomView(tabTextView);
        }).attach();

        tabLayout.getTabAt(0).select();
        TextView firstTabTextView = (TextView) tabLayout.getTabAt(0).getCustomView();
        firstTabTextView.setTextColor(Color.parseColor("#aa0116"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tabTextView = (TextView) tab.getCustomView();
                tabTextView.setTextColor(Color.parseColor("#aa0116"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tabTextView = (TextView) tab.getCustomView();
                tabTextView.setTextColor(Color.BLACK);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden) {
//            System.out.println("111111111");
//        } else {
//            notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
//            notificationViewModel.fetchNotifications();
//            System.out.println("111111111");
//        }
//    }
}