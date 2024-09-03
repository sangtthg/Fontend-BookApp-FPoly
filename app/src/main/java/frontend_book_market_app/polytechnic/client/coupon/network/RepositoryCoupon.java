package frontend_book_market_app.polytechnic.client.coupon.network;

import retrofit2.Call;
import retrofit2.Callback;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.coupon.model.CouponRequestModel;

import java.util.List;

import static frontend_book_market_app.polytechnic.client.utils.Common.API_URL;

public class RepositoryCoupon {
    private final ApiServiceCoupon apiService;

    public RepositoryCoupon() {
        apiService = RetrofitManager.createService(ApiServiceCoupon.class, API_URL, null);
    }


    public void fetchVouchers(Callback<List<CouponRequestModel>> callback) {
        Call<List<CouponRequestModel>> call = apiService.getVouchers();
        call.enqueue(callback);
    }
}
