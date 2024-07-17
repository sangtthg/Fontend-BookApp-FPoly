package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network.RepositoryHome;

public class HomeViewModel extends ViewModel {
    private final RepositoryHome repositoryHome = new RepositoryHome();
    private final MutableLiveData<List<HomeBookModel>> newBookList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> bestSellerBookList = new MutableLiveData<>();


    public LiveData<List<HomeBookModel>> getBestSellerBookList() {
        return bestSellerBookList;
    }

    public LiveData<List<HomeBookModel>> getNewBookList() {
        return newBookList;
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
                            Log.d("HomeViewModel", "SellerBookList:" + bestSellerBookList);
                        }
                        if (data.getNewBooks() != null) {
                            newBookList.postValue(data.getNewBooks());
                            Log.d("HomeViewModel", "New Book:" + newBookList);
                        }
                    } else {
                        Log.e("HomeViewModel", "Data is null");
                    }
                } else {
                    Log.e("HomeViewModel", "Fetch API products failed: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeBookResponse> call, @NonNull Throwable t) {
                Log.e("HomeViewModel", "Fetch first API products onFailure: ", t);
            }
        });
    }


}
