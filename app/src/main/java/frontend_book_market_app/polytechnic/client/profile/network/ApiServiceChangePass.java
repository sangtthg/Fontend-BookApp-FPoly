package frontend_book_market_app.polytechnic.client.profile.network;

import frontend_book_market_app.polytechnic.client.profile.model.ChangePasswordModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiServiceChangePass {

    @POST("/api/user/change-password")
    Call<ResponseBody> changePassword(
            @Header("Authorization") String token,
            @Body ChangePasswordModel changePasswordModel
    );
}
