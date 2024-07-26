package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderResponse2;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.network.RepositoryOrderUser;

public class OrderUserViewModel extends ViewModel {
    private final MutableLiveData<OrderResponse2> ordersLiveData;
    private final MutableLiveData<OrderResponse2> ordersLiveData2;
    private final MutableLiveData<OrderResponse2> ordersLiveData3;
    private final MutableLiveData<OrderResponse2> ordersLiveData4;
    private final RepositoryOrderUser repository;

    public OrderUserViewModel() {
        ordersLiveData = new MutableLiveData<>();
        ordersLiveData2 = new MutableLiveData<>();
        ordersLiveData3 = new MutableLiveData<>();
        ordersLiveData4 = new MutableLiveData<>();
        repository = new RepositoryOrderUser();
    }

    public LiveData<OrderResponse2> getOrdersLiveData() {
        return ordersLiveData;
    }

    public MutableLiveData<OrderResponse2> getOrdersLiveData2() {
        return ordersLiveData2;
    }

    public MutableLiveData<OrderResponse2> getOrdersLiveData3() {
        return ordersLiveData3;
    }

    public MutableLiveData<OrderResponse2> getOrdersLiveData4() {
        return ordersLiveData4;
    }

    public void fetchPendingOrders() {
        repository.fetchPendingOrders(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void getWaiForDeliverytOrders() {
        repository.getWaiForDeliverytOrders(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersLiveData2.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void getDelivredtOrders() {
        repository.getDelivredtOrders(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersLiveData3.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void geCanlledOrders() {
        repository.geCanlledOrders(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersLiveData4.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                // Handle failure
            }
        });
    }
}

