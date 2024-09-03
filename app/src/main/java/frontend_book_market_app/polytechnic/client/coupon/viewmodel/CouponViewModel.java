package frontend_book_market_app.polytechnic.client.coupon.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import frontend_book_market_app.polytechnic.client.coupon.model.CouponRequestModel;
import frontend_book_market_app.polytechnic.client.coupon.network.RepositoryCoupon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponViewModel extends ViewModel {
    private final String NAME = this.getClass().getSimpleName();

    private final RepositoryCoupon repositoryCoupon = new RepositoryCoupon();
    private final MutableLiveData<List<CouponRequestModel>> couponList = new MutableLiveData<>();

    public LiveData<List<CouponRequestModel>> getCouponList() {
        return couponList;
    }

    public void fetchCouponList() {
        repositoryCoupon.fetchVouchers(new Callback<List<CouponRequestModel>>() {
            @Override
            public void onResponse(Call<List<CouponRequestModel>> call, Response<List<CouponRequestModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    couponList.postValue(response.body());
                    Log.d(NAME, "Fetch Coupon List Success");
                } else {
                    logErrorResponse("Fetch Coupon List failed: ", response);
                }
            }

            @Override
            public void onFailure(Call<List<CouponRequestModel>> call, Throwable t) {
                Log.e(NAME, "Fetch Coupon List onFailure: ", t);
            }
        });
    }

    private void logErrorResponse(String name, Response<?> response) {
        String logMessage = String.format("📛 Error: %s => Code: %d => Message: %s", name, response.code(), response.message());
        Log.e(NAME, logMessage);

        if (response.errorBody() != null) {
            try {
                String errorBody = response.errorBody().string();
                Log.e(NAME, "📛 Error Body: " + errorBody);
            } catch (Exception e) {
                Log.e(NAME, "📛 Error reading error body", e);
            }
        } else {
            Log.e(NAME, "📛 No error body");
        }
    }
}
