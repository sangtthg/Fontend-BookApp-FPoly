package frontend_book_market_app.polytechnic.client.don_hang.network;


import static frontend_book_market_app.polytechnic.client.utils.Common.API_URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.don_hang.model.OrderUserResponse;

public class RepositoryDonHangUser {
    private final ApiServiceDonHangUser apiService;

    public RepositoryDonHangUser() {
        apiService = RetrofitManager.createService(ApiServiceDonHangUser.class, API_URL, null);
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

    public void cancelOrder(int orderId, Callback<Void> callback) {
        Call<Void> call = apiService.cancelOrder(orderId);
        call.enqueue(callback);
    }
}

