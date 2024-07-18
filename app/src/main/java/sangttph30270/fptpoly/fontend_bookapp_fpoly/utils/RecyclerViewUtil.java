package sangttph30270.fptpoly.fontend_bookapp_fpoly.utils;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.ItemOffsetDecoration;

public class RecyclerViewUtil {

    public static void setupLinear(Context context, RecyclerView recyclerView, int offset, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(offset));
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    public static void setupGrid(Context context, RecyclerView recyclerView, int offset, int spanCount, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(offset));
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
        recyclerView.setAdapter(adapter);
    }
}


//RecyclerViewUtil.setupGrid(getActivity(), recycler3, offset, 3, adapterSachBanChay);
//RecyclerViewUtil.setupGrid(getActivity(), recycler2, offset, 2, adapterSachMoiCapNhat);

