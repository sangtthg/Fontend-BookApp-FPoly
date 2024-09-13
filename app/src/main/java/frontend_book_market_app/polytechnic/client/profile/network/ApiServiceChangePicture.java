package frontend_book_market_app.polytechnic.client.profile.network;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Header;

public interface ApiServiceChangePicture {
    @Multipart
    @POST("api/user/member/update")
    Call<ResponseBody> updateProfilePicture(@Header("Authorization") String token, @Part MultipartBody.Part image);
}
