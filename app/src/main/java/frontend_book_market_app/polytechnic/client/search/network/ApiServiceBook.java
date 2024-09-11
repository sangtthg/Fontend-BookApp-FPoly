package frontend_book_market_app.polytechnic.client.search.network;


import frontend_book_market_app.polytechnic.client.search.model.BookSearchRequest;
import frontend_book_market_app.polytechnic.client.search.model.BookSearchResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiServiceBook {
    @POST("/api/book/get")
    Call<BookSearchResponse> searchBooks(@Header("Authorization") String authToken, @Body BookSearchRequest request);
}