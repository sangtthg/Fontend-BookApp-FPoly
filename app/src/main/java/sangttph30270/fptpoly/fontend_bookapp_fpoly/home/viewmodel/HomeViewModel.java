package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network.RepositoryHome;

public class HomeViewModel extends ViewModel {
    private final String NAME = this.getClass().getSimpleName();
    private final RepositoryHome repositoryHome = new RepositoryHome();
    private final MutableLiveData<List<HomeBookModel>> newBookList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> bestSellerBookList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> randomBooksList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> mostViewBooksList = new MutableLiveData<>();
    private final MutableLiveData<DetailBookResponse> detailBook = new MutableLiveData<>();

    public LiveData<List<HomeBookModel>> getBestSellerBookList() {
        return bestSellerBookList;
    }

    public LiveData<List<HomeBookModel>> getNewBookList() {
        return newBookList;
    }

    public LiveData<List<HomeBookModel>> getRandomBookList() {
        return randomBooksList;
    }

    public LiveData<List<HomeBookModel>> getMostViewBooksList() {
        return mostViewBooksList;
    }

    public LiveData<DetailBookResponse> getDetailBook() {
        return detailBook;
    }

    public void fetchHomeBookAPI() {
        repositoryHome.fetchApiHomePageBook(new Callback<HomeBookResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeBookResponse> call, @NonNull Response<HomeBookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HomeBookResponse.BookData data = response.body().getData();
                    if (data != null) {
                        if (data.getBestSellerBooks() != null) {
                            bestSellerBookList.postValue(data.getBestSellerBooks());
                            Log.d(NAME, "Fetch SellerBookList Success");
                        }
                        if (data.getNewBooks() != null) {
                            newBookList.postValue(data.getNewBooks());
                            Log.d(NAME, "Fetch New Book Success");
                        }
                        if (data.getRandomBooks() != null) {
                            randomBooksList.postValue(data.getRandomBooks());
                            Log.d(NAME, "Fetch Random Success");
                        }
                        if (data.getMostViewBooks() != null) {
                            mostViewBooksList.postValue(data.getMostViewBooks());
                            Log.d(NAME, "Fetch MostViewBooks Success:");
                        }
                    } else {
                        Log.e(NAME, "Data is null");
                    }
                } else {
                    Log.e(NAME, "Fetch API products failed: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeBookResponse> call, @NonNull Throwable t) {
                Log.e(NAME, "Fetch first API products onFailure: ", t);
            }
        });
    }

    public void fetchBookDetail(int bookId, String token) {
    try {
        repositoryHome.fetchBookDetail(bookId, token, new Callback<DetailBookResponse>() {
            @Override
            public void onResponse(@NonNull Call<DetailBookResponse> call, @NonNull Response<DetailBookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detailBook.postValue(response.body());
                    Log.d(NAME, "Fetch BookDetail Success");
                } else {
                    Log.e(NAME, "Fetch BookDetail failed: " + response.code() + " " + response.message());
                    try {
                        Log.e(NAME, "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e(NAME, "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailBookResponse> call, @NonNull Throwable t) {
                Log.e(NAME, "Fetch BookDetail onFailure: ", t);
            }
        });
    } catch (Exception e) {
        Log.e(NAME, "Exception in fetchBookDetail: ", e);
    }
}

    public void clearAllLists() {
        bestSellerBookList.postValue(new ArrayList<>());
        newBookList.postValue(new ArrayList<>());
        randomBooksList.postValue(new ArrayList<>());
        mostViewBooksList.postValue(new ArrayList<>());
    }
}
