package frontend_book_market_app.polytechnic.client.profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<AddressModel> addressList;
    private final OnAddressActionListener onAddressActionListener;

    public AddressAdapter(List<AddressModel> addressList, OnAddressActionListener onAddressActionListener) {
        this.addressList = addressList != null ? addressList : new ArrayList<>();
        this.onAddressActionListener = onAddressActionListener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address_list, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel address = addressList.get(position);
        holder.txtAddressName.setText(address.getName());
        holder.txtPhoneNumber.setText(address.getPhone());
        holder.txtAddressDetails.setText(address.getAddress());

        // Set default address label visibility
        if (address.isIs_default()) {
            holder.txtDefaultAddress.setVisibility(View.VISIBLE);
        } else {
            holder.txtDefaultAddress.setVisibility(View.GONE);
        }

        // Set address type icon and label
        switch (address.getAddress_type()) {
            case "Nhà":
                holder.imgAddressTypeIcon.setImageResource(R.drawable.baseline_home_filled_24); // Thay thế ic_home bằng icon của bạn
                holder.txtAddressTypeLabel.setText("Nhà");
                break;
            case "Văn phòng":
                holder.imgAddressTypeIcon.setImageResource(R.drawable.baseline_apartment_24); // Thay thế ic_office bằng icon của bạn
                holder.txtAddressTypeLabel.setText("Văn phòng");
                break;
            default:
                holder.imgAddressTypeIcon.setVisibility(View.GONE);
                holder.txtAddressTypeLabel.setText("Không có sẵn");
                break;
        }

        holder.btnEditAddress.setOnClickListener(v -> {
            if (onAddressActionListener != null) {
                onAddressActionListener.onEditAddress(address);
            }
        });
        holder.btnDeleteAddress.setOnClickListener(v -> {
                if (onAddressActionListener != null) {
                    if (address.isIs_default()) {
                        Toast.makeText(holder.itemView.getContext(), "Không thể xóa địa chỉ mặc định", Toast.LENGTH_SHORT).show();
                    } else {
                        onAddressActionListener.onDeleteAddress(address);
                    }
                }

        });

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public void setAddressList(List<AddressModel> addressList) {
        this.addressList = addressList != null ? addressList : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {

        TextView txtAddressName;
        TextView txtPhoneNumber;
        TextView txtAddressDetails;
        TextView txtDefaultAddress;
        TextView txtAddressTypeLabel;
        ImageView imgAddressTypeIcon;
        Button btnEditAddress;
        Button btnDeleteAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddressName = itemView.findViewById(R.id.txtAddressName);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            txtAddressDetails = itemView.findViewById(R.id.txtAddressDetails);
            txtDefaultAddress = itemView.findViewById(R.id.txtDefaultAddress);
            txtAddressTypeLabel = itemView.findViewById(R.id.txtAddressTypeLabel);
            imgAddressTypeIcon = itemView.findViewById(R.id.imgAddressTypeIcon);
            btnEditAddress = itemView.findViewById(R.id.btnEditAddress);
            btnDeleteAddress = itemView.findViewById(R.id.btnDeleteAddress);
        }
    }

    public interface OnAddressActionListener {
        void onEditAddress(AddressModel address);
        void onDeleteAddress(AddressModel address);
    }
}
