package sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.network;


import static sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.Common.API_URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.core.RetrofitManager;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.order_user.model.OrderUserResponse;

public class RepositoryOrderUser {
    private final ApiServiceOrderUser apiService;

    public RepositoryOrderUser() {
        apiService = RetrofitManager.createService(ApiServiceOrderUser.class, API_URL, null);
    }

    //
    public void fetchPendingOrders(Callback<OrderUserResponse> callback) {
        Call<OrderUserResponse> call = apiService.getPendingOrders();
        call.enqueue(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch orders"));
                }
            }

            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    //
    public void getWaiForDeliverytOrders(Callback<OrderUserResponse> callback) {
        Call<OrderUserResponse> call = apiService.getWaiForDeliverytOrders();
        call.enqueue(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch orders"));
                }
            }

            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    //
    public void getDelivredtOrders(Callback<OrderUserResponse> callback) {
        Call<OrderUserResponse> call = apiService.getDelivredtOrders();
        call.enqueue(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch orders"));
                }
            }

            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    //
    public void geCanlledOrders(Callback<OrderUserResponse> callback) {
        Call<OrderUserResponse> call = apiService.geCanlledOrders();
        call.enqueue(new Callback<OrderUserResponse>() {
            @Override
            public void onResponse(Call<OrderUserResponse> call, Response<OrderUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch orders"));
                }
            }

            @Override
            public void onFailure(Call<OrderUserResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}

