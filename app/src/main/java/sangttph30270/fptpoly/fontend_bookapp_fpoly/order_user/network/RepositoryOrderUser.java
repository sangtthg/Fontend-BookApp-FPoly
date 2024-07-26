package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.network;


import static sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.Common.API_URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.core.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderResponse2;

public class RepositoryOrderUser {
    private final ApiServiceOrderUser apiService;

    public RepositoryOrderUser() {
        apiService = RetrofitManager.createService(ApiServiceOrderUser.class, API_URL, null);
    }

    //
    public void fetchPendingOrders(Callback<OrderResponse2> callback) {
        Call<OrderResponse2> call = apiService.getPendingOrders();
        call.enqueue(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch orders"));
                }
            }

            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    //
    public void getWaiForDeliverytOrders(Callback<OrderResponse2> callback) {
        Call<OrderResponse2> call = apiService.getWaiForDeliverytOrders();
        call.enqueue(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch orders"));
                }
            }

            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    //
    public void getDelivredtOrders(Callback<OrderResponse2> callback) {
        Call<OrderResponse2> call = apiService.getDelivredtOrders();
        call.enqueue(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch orders"));
                }
            }

            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    //
    public void geCanlledOrders(Callback<OrderResponse2> callback) {
        Call<OrderResponse2> call = apiService.geCanlledOrders();
        call.enqueue(new Callback<OrderResponse2>() {
            @Override
            public void onResponse(Call<OrderResponse2> call, Response<OrderResponse2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch orders"));
                }
            }

            @Override
            public void onFailure(Call<OrderResponse2> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}

