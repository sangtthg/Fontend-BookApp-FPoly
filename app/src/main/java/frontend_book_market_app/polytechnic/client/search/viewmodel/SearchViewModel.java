package frontend_book_market_app.polytechnic.client.search.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

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
    private final MutableLiveData<List<HomeBookModel>> randomBooksList = new MutableLiveData<>();

    public LiveData<List<HomeBookModel>> getRandomBookList() {
        return randomBooksList;
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
}
