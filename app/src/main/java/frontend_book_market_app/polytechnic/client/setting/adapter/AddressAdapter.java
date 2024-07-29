package frontend_book_market_app.polytechnic.client.setting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.login.model.AddressModel;
import frontend_book_market_app.polytechnic.client.setting.viewmodel.SettingViewModel;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private final Context context;
    private final List<AddressModel> addressList;
    private final SettingViewModel settingViewModel;
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public AddressAdapter(Context context, List<AddressModel> addressList, SettingViewModel settingViewModel, SharedPreferencesHelper sharedPreferencesHelper) {
        this.context = context;
        this.addressList = addressList;
        this.settingViewModel = settingViewModel;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AddressModel address = addressList.get(position);
        holder.tvUserName.setText(address.getName());
        holder.tvPhone.setText(address.getPhone());
        holder.tvAddress.setText(address.getAddress());
        holder.tvEmail.setText(sharedPreferencesHelper.getEmail());
        if (address.isDefault()) {
            holder.tvDefaultAddress.setVisibility(View.VISIBLE);
        } else {
            holder.tvDefaultAddress.setVisibility(View.GONE);
        }
        holder.btnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit address action
                // You can start an activity or show a dialog for editing the address
            }
        });

        holder.btnDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete address action
                int addressId = address.getAddressId(); // Assuming AddressModel has a method to get the ID
                String token = sharedPreferencesHelper.getToken();

                settingViewModel.deleteAddress(token, addressId);
                // Observe ViewModel for result and update UI accordingly
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvPhone, tvAddress, tvEmail,tvDefaultAddress;
        Button btnEditAddress, btnDeleteAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDefaultAddress = itemView.findViewById(R.id.tvDefaultTag);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnEditAddress = itemView.findViewById(R.id.btnEditAddress);
            btnDeleteAddress = itemView.findViewById(R.id.btnDeleteAddress);
        }
    }

    public void updateData(List<AddressModel> newAddressList) {
        this.addressList.clear();
        this.addressList.addAll(newAddressList);
        notifyDataSetChanged();
    }
}
