package sangttph30270.fptpoly.fontend_bookapp_fpoly.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view.user_order.Page1Fragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view.user_order.Page2Fragment;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.profile.view.user_order.Page3Fragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Page1Fragment();
            case 1:
                return new Page2Fragment();
            case 2:
                return new Page3Fragment();
            default:
                return new Page1Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
