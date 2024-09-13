package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import frontend_book_market_app.polytechnic.client.profile.network.RepositoryChangePicture;

public class ProfilePictureViewModel extends AndroidViewModel {
    private final RepositoryChangePicture repositoryChangePicture;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();

    public ProfilePictureViewModel(@NonNull Application application) {
        super(application);
        repositoryChangePicture = new RepositoryChangePicture();
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> isSuccess() {
        return success;
    }

    public void changeProfilePicture(String token, Uri imageUri) {
        isLoading.setValue(true);

        // Convert Uri to MultipartBody.Part
        MultipartBody.Part imagePart = prepareFilePart("avatar", imageUri);

        repositoryChangePicture.changeProfilePicture(token, imagePart, new RepositoryChangePicture.ChangePictureCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                isLoading.setValue(false);
                success.setValue(true);
                Log.d("ProfilePictureViewModel", "Profile picture updated successfully");
            }

            @Override
            public void onFailure(String errorMessage) {
                isLoading.setValue(false);
                success.setValue(false);
                ProfilePictureViewModel.this.errorMessage.setValue(errorMessage);
                Log.e("ProfilePictureViewModel", "Failed to update profile picture: " + errorMessage);
            }
        });
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // Convert Uri to File and then to RequestBody
        File file = new File(fileUri.getPath());
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
}
