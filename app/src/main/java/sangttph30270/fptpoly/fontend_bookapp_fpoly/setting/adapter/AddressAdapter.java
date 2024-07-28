package sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.adapter;

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

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.auth.login.model.AddressModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.viewmodel.SettingViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SharedPreferencesHelper;

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
        TextView tvUserName, tvPhone, tvAddress, tvEmail;
        Button btnEditAddress, btnDeleteAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
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
