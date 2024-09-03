package frontend_book_market_app.polytechnic.client.coupon.network;

import java.util.List;

import frontend_book_market_app.polytechnic.client.coupon.model.CouponRequestModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServiceCoupon {
    @GET("admin/vouchers/json")
    Call<List<CouponRequestModel>> getVouchers();
}
