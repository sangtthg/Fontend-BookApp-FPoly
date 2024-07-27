package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderUserResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.network.RepositoryOrderUser;

public class OrderUserViewModel extends ViewModel {
    private final MutableLiveData<OrderUserResponse> ordersLiveData;
    private final MutableLiveData<OrderUserResponse> ordersLiveData2;
    private final MutableLiveData<OrderUserResponse> ordersLiveData3;
    private final MutableLiveData<OrderUserResponse> ordersLiveData4;
    private final RepositoryOrderUser repository;

    public OrderUserViewModel() {
        ordersLiveData = new MutableLiveData<>();
        ordersLiveData2 = new MutableLiveData<>();
        ordersLiveData3 = new MutableLiveData<>();
        ordersLiveData4 = new MutableLiveData<>();
        repository = new RepositoryOrderUser();
    }

    public LiveData<OrderUserResponse> getOrdersLiveData() {
        return ordersLiveData;
    }

    public MutableLiveData<OrderUserResponse> getOrdersLiveData2() {
        return ordersLiveData2;
    }

    public MutableLiveData<OrderUserResponse> getOrdersLiveData3() {
        return ordersLiveData3;
    }

    public MutableLiveData<OrderUserResponse> getOrdersLiveData4() {
        return ordersLiveData4;
    }

    public void fetchPendingOrders() {
        repository.fetchPendingOrders(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void getWaiForDeliverytOrders() {
        repository.getWaiForDeliverytOrders(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersLiveData2.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void getDelivredtOrders() {
        repository.getDelivredtOrders(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersLiveData3.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void geCanlledOrders() {
        repository.geCanlledOrders(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersLiveData4.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }
}

