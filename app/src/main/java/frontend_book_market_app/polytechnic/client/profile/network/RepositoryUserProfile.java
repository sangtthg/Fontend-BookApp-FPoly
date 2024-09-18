package frontend_book_market_app.polytechnic.client.profile.network;

import static frontend_book_market_app.polytechnic.client.utils.URL.API_URL;

import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.profile.model.UserProfileRequest;
import retrofit2.Call;
import retrofit2.Callback;

public class RepositoryUserProfile {

    private final ApiServiceUserProfile apiService;


    public RepositoryUserProfile() {
        apiService = RetrofitManager.createService(ApiServiceUserProfile.class, API_URL, null);
    }


    public void getUserProfile(Callback<UserProfileRequest> callback) {
        Call<UserProfileRequest> call = apiService.getUserProfile();
        call.enqueue(callback);
    }


//    public void getUserProfile(String token, final UserProfileCallback callback) {
//        // Tạo đối tượng UserProfileRequest với thông tin cần thiết
//        UserProfileRequest request = new UserProfileRequest();
//
//        // Gửi UserProfileRequest trong POST request
//        Call<RepositoryUserProfile> call = apiService.getUserProfile("Bearer " + token, request);
//        call.enqueue(new Callback<RepositoryUserProfile>() {
//            @Override
//            public void onResponse(Call<RepositoryUserProfile> call, Response<RepositoryUserProfile> response) {
//                if (response.isSuccessful()) {
//                    callback.onSuccess(response.body());
//                } else {
//                    callback.onFailure("Error: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RepositoryUserProfile> call, Throwable t) {
//                callback.onFailure("Error: " + t.getMessage());
//            }
//        });
//    }

//    public interface UserProfileCallback {
//        void onSuccess(RepositoryUserProfile userProfileResponse);
//
//        void onFailure(String errorMessage);
//    }
}
