package frontend_book_market_app.polytechnic.client.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.home.model.ImageResponse;
import frontend_book_market_app.polytechnic.client.home.network.RepositoryHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageListViewModel extends ViewModel {

    private final MutableLiveData<List<ImageResponse.ImageData>> images = new MutableLiveData<>();
    private final RepositoryHome repositoryHome = new RepositoryHome();

    public LiveData<List<ImageResponse.ImageData>> getImages() {
        return images;
    }

    public void fetchImages(int bookId) {
        repositoryHome.fetchImages(bookId, new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        System.out.println("kkkkkk 1 " + response.body().getData().get(0).getUrl());
                         images.postValue(response.body().getData());
                    } else {
                        System.out.println("kkkkkk null rồi " + response.body().getData());
                        images.postValue(new ArrayList<>());
                    }
                } catch (Exception e) {
                    System.err.println("Error processing response: " + e.getMessage());
                    e.printStackTrace();
                    images.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                images.postValue(new ArrayList<>());
            }
        });
    }
}