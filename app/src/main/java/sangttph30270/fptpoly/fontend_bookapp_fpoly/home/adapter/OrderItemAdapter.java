package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.OrderResponseHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.CurrencyFormatter;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private final List<OrderResponseHome.Item> items;

    public OrderItemAdapter(List<OrderResponseHome.Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderResponseHome.Item item = items.get(position);
        holder.titleBook.setText(item.getTitle());
        holder.giaMoi.setText(String.valueOf(CurrencyFormatter.toVND(item.getNewPrice())));
        holder.giaCu.setText(String.valueOf(CurrencyFormatter.toVND(item.getOldPrice())));
        holder.giaCu.setPaintFlags( holder.giaCu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.itemSoLuong.setText(String.format("x%s", item.getQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(item.getBookAvatar())
                .placeholder(R.drawable.loading_book)
                .centerCrop()
                .into(holder.imgBookOrder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleBook, giaMoi, giaCu, itemSoLuong;
        ImageView imgBookOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleBook = itemView.findViewById(R.id.itemTitle);
            giaMoi = itemView.findViewById(R.id.itemGiaMoi);
            imgBookOrder = itemView.findViewById(R.id.imgBookOrder);
            giaCu = itemView.findViewById(R.id.itemGiaCu);
            itemSoLuong = itemView.findViewById(R.id.itemSoLuong);
        }
    }


}