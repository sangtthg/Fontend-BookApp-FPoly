package frontend_book_market_app.polytechnic.client.profile.network;

import frontend_book_market_app.polytechnic.client.core.RetrofitManager;
import frontend_book_market_app.polytechnic.client.profile.model.ChangePasswordModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static frontend_book_market_app.polytechnic.client.utils.Common.API_URL;

public class RepositoryChangePass {
    private final ApiServiceChangePass apiService;

    public RepositoryChangePass() {
        apiService = RetrofitManager.createService(ApiServiceChangePass.class, API_URL, null);
    }

    public void changePassword(String token, String oldPassword, String newPassword, Callback<ResponseBody> callback) {
        ChangePasswordModel changePasswordModel = new ChangePasswordModel(oldPassword, newPassword, newPassword);
        Call<ResponseBody> call = apiService.changePassword("Bearer " + token, changePasswordModel);
        call.enqueue(callback);
    }
}
