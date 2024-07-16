package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel;

import android.util.Log;

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
    private RepositoryHome repositoryHome;
    private final MutableLiveData<List<HomeBookModel>> newBookList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> bestSellerBookList = new MutableLiveData<>();

    public HomeViewModel() {
        this.repositoryHome = new RepositoryHome(); // Consider using dependency injection for better testability and flexibility
    }

    public LiveData<List<HomeBookModel>> getNewBookList() {
        return newBookList;
    }

    public LiveData<List<HomeBookModel>> getBestSellerBookList() {
        return bestSellerBookList;
    }

    public void fetchFirstApiProducts() {
        repositoryHome.fetchApiHomePageBook(new Callback<HomeBookResponse>() {
            @Override
            public void onResponse(Call<HomeBookResponse> call, Response<HomeBookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newBookList.postValue(response.body().getData().getNewBooks());
                    bestSellerBookList.postValue(response.body().getData().getBestSellerBooks());
                    Log.d("HomeViewModel", bestSellerBookList.toString());
                } else {
                    Log.e("HomeViewModel", "Fetch first API products failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<HomeBookResponse> call, Throwable t) {
                Log.e("HomeViewModel", "Fetch first API products onFailure: ", t);
            }
        });
    }

}
