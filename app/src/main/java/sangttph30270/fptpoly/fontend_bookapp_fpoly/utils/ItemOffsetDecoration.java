package sangttph30270.fptpoly.fontend_bookapp_fpoly.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public ItemOffsetDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        if (position == itemCount - 1) {
            // Mục cuối cùng: không có phần bù bên phải/cuối
            outRect.set(offset, offset, 0, offset);
        } else if (position == 0) {
            // Mục đầu tiên: không có offset trái/bắt đầu
            outRect.set(0, offset, offset, offset);
        } else {
            // Mục giữa: bù đắp ở tất cả các bên
            outRect.set(offset, offset, offset, offset);
        }
    }
}