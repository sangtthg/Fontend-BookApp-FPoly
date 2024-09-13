package frontend_book_market_app.polytechnic.client.search.network;

import frontend_book_market_app.polytechnic.client.search.model.BookSearchRequest;
import frontend_book_market_app.polytechnic.client.search.model.BookSearchResponse;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;

import static frontend_book_market_app.polytechnic.client.utils.Common.API_URL;

public class RepositorySearch {
    private final ApiServiceBook apiService;

    public RepositorySearch() {
        // Sử dụng RetrofitManager để tạo service cho ApiServiceBook
        apiService = RetrofitManager.createService(ApiServiceBook.class, API_URL, null);
    }

    // Phương thức tìm kiếm sách dựa trên từ khóa
    public void searchBooks( int totalAll, int limit, String searchQuery, Callback<BookSearchResponse> callback) {
        // Tạo đối tượng yêu cầu với các tham số cần thiết
        BookSearchRequest request = new BookSearchRequest(totalAll, limit, searchQuery);

        // Gọi API với token được thêm vào tiêu đề
        Call<BookSearchResponse> call = apiService.searchBooks("Bearer " , request);
        call.enqueue(callback);
    }
}
