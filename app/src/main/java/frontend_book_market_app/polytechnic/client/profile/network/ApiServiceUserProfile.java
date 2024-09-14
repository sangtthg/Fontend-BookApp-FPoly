package frontend_book_market_app.polytechnic.client.profile.network;

import frontend_book_market_app.polytechnic.client.home.model.HomeBookResponse;
import frontend_book_market_app.polytechnic.client.profile.model.UserProfileRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiServiceUserProfile {
    @POST("api/user/profile")
    Call<UserProfileRequest> getUserProfile();
}
