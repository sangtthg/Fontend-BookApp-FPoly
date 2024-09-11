package frontend_book_market_app.polytechnic.client.search.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import frontend_book_market_app.polytechnic.client.search.model.BookSearchRequest;
import frontend_book_market_app.polytechnic.client.search.model.BookSearchResponse;
import frontend_book_market_app.polytechnic.client.search.network.ApiServiceBook;
import frontend_book_market_app.polytechnic.client.search.network.RepositorySearch;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookModel;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookResponse;
import frontend_book_market_app.polytechnic.client.home.network.RepositoryHome;

public class SearchViewModel extends ViewModel {
    private final String NAME = this.getClass().getSimpleName();
    private final RepositoryHome repositoryHome = new RepositoryHome();
    private final RepositorySearch repositorySearch = new RepositorySearch(); // Thêm RepositorySearch
    private final MutableLiveData<List<HomeBookModel>> randomBooksList = new MutableLiveData<>();
    private final MutableLiveData<BookSearchResponse> searchResults = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> bookSuggestions = new MutableLiveData<>(); // Thêm MutableLiveData cho gợi ý sách

    public LiveData<List<HomeBookModel>> getRandomBookList() {
        return randomBooksList;
    }

    public LiveData<BookSearchResponse> getSearchResults() {
        return searchResults;
    }

    public LiveData<List<HomeBookModel>> getBookSuggestions() {
        return bookSuggestions;
    }

    public void fetchRandomBooks() {
        repositoryHome.fetchApiHomePageBook(new Callback<HomeBookResponse>() {
            @Override
            public void onResponse(Call<HomeBookResponse> call, Response<HomeBookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HomeBookResponse.BookData data = response.body().getData();
                    if (data != null && data.getRandomBooks() != null) {
                        randomBooksList.postValue(data.getRandomBooks());
                        Log.d(NAME, "Fetch RandomBooks Success");
                    } else {
                        logErrorResponse("Không có dữ liệu nào trả về", response);
                    }
                } else {
                    logErrorResponse("Fetch RandomBooks failed: ", response);
                }
            }

            @Override
            public void onFailure(Call<HomeBookResponse> call, Throwable t) {
                Log.wtf(NAME, "Fetch RandomBooks failed onFailure: ", t);
            }
        });
    }

    public void searchBooks(String token, int totalAll, int limit, String searchQuery) {
        repositorySearch.searchBooks(token, totalAll, limit, searchQuery, new Callback<BookSearchResponse>() {
            @Override
            public void onResponse(Call<BookSearchResponse> call, Response<BookSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchResults.setValue(response.body());
                    handleSearchResponse(response.body()); // Xử lý phản hồi
                    Log.d(NAME, "Search books success");
                } else {
                    logErrorResponse("Search books failed: ", response);
                }
            }

            @Override
            public void onFailure(Call<BookSearchResponse> call, Throwable t) {
                Log.e(NAME, "Search books failed onFailure: ", t);
            }
        });
    }


    private void logErrorResponse(String name, Response<?> response) {
        String logMessage = String.format("📛 Lỗi: %s => Mã lỗi: %d => Thông báo: %s", name, response.code(), response.message());
        Log.e(NAME, logMessage);

        ResponseBody errorBody = response.errorBody();
        if (errorBody != null) {
            try (ResponseBody responseBody = errorBody) {
                String errorMessage = String.format("📛 Nội dung lỗi: %s", responseBody.string());
                Log.e(NAME, errorMessage);
            } catch (IOException e) {
                Log.e(NAME, "📛 Lỗi khi đọc nội dung lỗi", e);
            }
        } else {
            Log.e(NAME, "📛 Nội dung lỗi không tồn tại");
        }
    }

    public void handleSearchResponse(BookSearchResponse response) {
        if (response != null && response.getData() != null) {
            int totalAll = response.getData().getTotalAll();
            // Xử lý totalAll nếu cần
            Log.d(NAME, "Total all books: " + totalAll);
        }
    }

}
