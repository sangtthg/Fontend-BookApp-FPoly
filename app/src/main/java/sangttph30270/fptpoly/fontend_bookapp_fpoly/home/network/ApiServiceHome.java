package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeModel;

public interface ApiServiceHome {
    @GET("api/home/get-list-book")
    Call<HomeBookResponse> getFirstApiProducts();

//    @GET("products/{id}")
//    Call<Product> getProductDetail(@Path("id") int productId);
}
