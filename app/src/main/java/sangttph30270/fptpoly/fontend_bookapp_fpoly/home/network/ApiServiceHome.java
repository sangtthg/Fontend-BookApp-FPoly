package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;

public interface ApiServiceHome {
    @GET("api/home/get-list-book")
    Call<HomeBookResponse> getApiHomeList();

    @FormUrlEncoded
    @POST("api/book/get-detail")
    Call<DetailBookResponse> getBookDetail(@Field("book_id") int bookId);

}
