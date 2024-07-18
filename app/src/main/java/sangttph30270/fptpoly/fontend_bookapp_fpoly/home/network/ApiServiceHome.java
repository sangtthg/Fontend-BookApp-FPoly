package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import retrofit2.Call;
import retrofit2.http.GET;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;

public interface ApiServiceHome {
    @GET("api/home/get-list-book")
    Call<HomeBookResponse> getApiHomeList();

//    @GET("products/{id}")
//    Call<Product> getProductDetail(@Path("id") int productId);
}
