package frontend_book_market_app.polytechnic.client.don_hang.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.don_hang.model.OrderUserResponse;
import frontend_book_market_app.polytechnic.client.don_hang.network.RepositoryDonHangUser;

public class DonHangUserViewModel extends ViewModel {
    private final MutableLiveData<OrderUserResponse> tab1, tab2, tab3, tab4;
    private final RepositoryDonHangUser repository;

    public DonHangUserViewModel() {
        tab1 = new MutableLiveData<>();
        tab2 = new MutableLiveData<>();
        tab3 = new MutableLiveData<>();
        tab4 = new MutableLiveData<>();
        repository = new RepositoryDonHangUser();
    }

    public LiveData<OrderUserResponse> getTab1() { return tab1; }
    public MutableLiveData<OrderUserResponse> getTab2() { return tab2; }
    public MutableLiveData<OrderUserResponse> getTab3() { return tab3; }
    public MutableLiveData<OrderUserResponse> getTab4() { return tab4; }

    public void fetchPendingOrders() {
        repository.fetchPendingOrders(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                tab1.postValue(response.isSuccessful() && response.body() != null ? response.body() : null);
            }
            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                tab1.postValue(null);
            }
        });
    }

    public void getWaitForDeliveryOrders() {
        repository.getWaiForDeliverytOrders(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                tab2.postValue(response.isSuccessful() && response.body() != null ? response.body() : null);
            }
            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                tab2.postValue(null);
            }
        });
    }

    public void getDeliveredOrders() {
        repository.getDelivredtOrders(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                tab3.postValue(response.isSuccessful() && response.body() != null ? response.body() : null);
            }
            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                tab3.postValue(null);
            }
        });
    }

    public void getCancelledOrders() {
        repository.geCanlledOrders(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                tab4.postValue(response.isSuccessful() && response.body() != null ? response.body() : null);
            }
            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                tab4.postValue(null);
            }
        });
    }

    public void cancelOrder(int orderId, Context context) {
        Toast.makeText(context, "Đang huỷ đơn hàng...", Toast.LENGTH_SHORT).show();
        repository.cancelOrder(orderId, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchPendingOrders();
                    getCancelledOrders();
                    Toast.makeText(context, "Huỷ đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("OrderUserViewModel", "Đã xảy ra lỗi khi huỷ đơn hàng");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("OrderUserViewModel", "Đã xảy ra lỗi khi huỷ đơn hàng do: " + t.getMessage());
            }
        });
    }
}
