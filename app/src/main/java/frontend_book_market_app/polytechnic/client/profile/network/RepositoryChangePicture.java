package frontend_book_market_app.polytechnic.client.profile.network;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.core.RetrofitManager;

import static frontend_book_market_app.polytechnic.client.utils.URL.API_URL;

public class RepositoryChangePicture {
    private final ApiServiceChangePicture apiService;

    public RepositoryChangePicture() {
        // Initialize ApiServiceChangePicture with Retrofit
        apiService = RetrofitManager.createService(ApiServiceChangePicture.class, API_URL, null);
    }

    public void changeProfilePicture(String token, MultipartBody.Part image, final ChangePictureCallback callback) {
        // Call the API with the provided token and image
        Call<ResponseBody> call = apiService.updateProfilePicture("Bearer " + token, image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Notify the success
                    callback.onSuccess(response.body());
                } else {
                    // Notify the failure
                    callback.onFailure("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Notify the failure
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    // Define an interface for callback
    public interface ChangePictureCallback {
        void onSuccess(ResponseBody responseBody);
        void onFailure(String errorMessage);
    }


}
