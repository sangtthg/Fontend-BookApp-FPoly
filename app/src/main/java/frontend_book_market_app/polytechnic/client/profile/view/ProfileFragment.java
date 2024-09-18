package frontend_book_market_app.polytechnic.client.profile.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.profile.adapter.AdapterProfile;
import frontend_book_market_app.polytechnic.client.profile.model.ProfileModel;
import frontend_book_market_app.polytechnic.client.profile.network.ApiServiceChangePicture;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.AViewModel;
import frontend_book_market_app.polytechnic.client.profile.viewmodel.ProfileViewModel;
import frontend_book_market_app.polytechnic.client.utils.URL;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment implements AdapterProfile.OnLogoutClickListener, AdapterProfile.OnImageSelectedListener {
    private static final int REQUEST_CODE_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 100;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private AdapterProfile adapter;
    private ProfileViewModel profileViewModel;
    private AViewModel aViewModel;
    private ImageView moreMenuNotification;
    private ImageView imgAvatar, imgChangeAvatar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        moreMenuNotification = view.findViewById(R.id.moreMenuNotification);
        imgAvatar = view.findViewById(R.id.imgAvatar1);
        imgChangeAvatar = view.findViewById(R.id.imgChangeAvatar);
        imgChangeAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        List<ProfileModel> profileList = new ArrayList<>();
        profileList.add(new ProfileModel(
                sharedPreferencesHelper.getUserId(),
                sharedPreferencesHelper.getUsername(),
                sharedPreferencesHelper.getEmail(),
                sharedPreferencesHelper.getAvatar(),
                sharedPreferencesHelper.getAuthToken(),
                sharedPreferencesHelper.getResetCode(),
                sharedPreferencesHelper.getUserStatus(),
                sharedPreferencesHelper.getRole(),
                sharedPreferencesHelper.getToken(),
                sharedPreferencesHelper.getDefaultAddress()
        ));
        logUserInfo();
        loadAvatar(sharedPreferencesHelper.getAvatar());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfile);
        adapter = new AdapterProfile(profileList, this, sharedPreferencesHelper, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aViewModel = new ViewModelProvider(requireActivity()).get(AViewModel.class);


    }

    private void loadAvatar(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.isEmpty()) {
            imgAvatar.setImageResource(R.drawable.ic_money); // Set default avatar image
        } else {
            // Load avatar from URL
            Glide.with(this)
                    .load(avatarUrl)
                    .placeholder(R.drawable.ic_money) // Placeholder image while loading
                    .into(imgAvatar);
        }
    }


    @Override
    public void onLogoutClick() {
        sharedPreferencesHelper.clear();
        loadProfileData(); // Update data after logout
        resetAvatar(); // Reset the avatar image

    }

    private void loadProfileData() {
        List<ProfileModel> profileList = new ArrayList<>();
        profileList.add(new ProfileModel(
                sharedPreferencesHelper.getUserId(),
                sharedPreferencesHelper.getUsername(),
                sharedPreferencesHelper.getEmail(),
                sharedPreferencesHelper.getAvatar(),
                sharedPreferencesHelper.getAuthToken(),
                sharedPreferencesHelper.getResetCode(),
                sharedPreferencesHelper.getUserStatus(),
                sharedPreferencesHelper.getRole(),
                sharedPreferencesHelper.getToken(),
                sharedPreferencesHelper.getDefaultAddress()
        ));
        adapter.updateProfileList(profileList);
    }

    private void logUserInfo() {
        int userId = sharedPreferencesHelper.getUserId();
        String username = sharedPreferencesHelper.getUsername();
        String email = sharedPreferencesHelper.getEmail();
        String avatar = sharedPreferencesHelper.getAvatar();
        String authToken = sharedPreferencesHelper.getAuthToken();
        String resetCode = sharedPreferencesHelper.getResetCode();
        int userStatus = sharedPreferencesHelper.getUserStatus();
        String role = sharedPreferencesHelper.getRole();
        String token = sharedPreferencesHelper.getToken();
        String address = sharedPreferencesHelper.getDefaultAddress();

        Log.d("ProfileFragment", "UserID: " + userId);
        Log.d("ProfileFragment", "Username: " + username);
        Log.d("ProfileFragment", "Email: " + email);
        Log.d("ProfileFragment", "Avatar: " + avatar);
        Log.d("ProfileFragment", "AuthToken: " + authToken);
        Log.d("ProfileFragment", "ResetCode: " + resetCode);
        Log.d("ProfileFragment", "UserStatus: " + userStatus);
        Log.d("ProfileFragment", "Role: " + role);
        Log.d("ProfileFragment", "Token: " + token);
        Log.d("ProfileFragment", "address: " + address);
    }

    @Override
    public void onImageSelected(Uri imageUri) {
        if (imageUri != null) {
            imgAvatar.setImageURI(imageUri);
            uploadImage(imageUri);
            updateAvatar(imageUri);

        }
    }


    private File compressImage(File file) {
        File compressedFile = new File(getContext().getCacheDir(), file.getName());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());

        if (bitmap == null) {
            Log.e("ProfileFragment", "Bitmap is null. Cannot compress image.");
            return null;
        }

        try (FileOutputStream out = new FileOutputStream(compressedFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out); // Điều chỉnh chất lượng theo nhu cầu
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressedFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            getActivity();
            if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    onImageSelected(selectedImageUri);
                }
            }
        }
    }


    private File getFileFromUri(Uri uri) {
        if (uri == null) return null;

        String fileName = null;
        InputStream inputStream = null;
        File file = null;
        try {
            if ("content".equals(uri.getScheme())) {
                Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (index != -1) {
                            fileName = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }

                inputStream = getContext().getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    File tempFile = new File(getContext().getCacheDir(), fileName);
                    FileOutputStream outputStream = new FileOutputStream(tempFile);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.flush();
                    outputStream.close();
                    file = tempFile;
                }
            } else if ("file".equals(uri.getScheme())) {
                fileName = uri.getLastPathSegment();
                file = new File(uri.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }

    private void updateAvatar(Uri imageUri) {
        imgAvatar = getView().findViewById(R.id.imgAvatar1);
        Glide.with(this)
                .load(imageUri)
                .apply(new RequestOptions().circleCrop())
                .into(imgAvatar);
    }

    private void resetAvatar() {
        imgAvatar.setImageResource(R.drawable.ic_money);
    }


    private void uploadImage(Uri imageUri) {
        File file = getFileFromUri(imageUri);
        if (file == null || !file.exists()) {
            Toast.makeText(getContext(), "Invalid image file or file not accessible", Toast.LENGTH_SHORT).show();
            return;
        }

        // Compress the image file before uploading
        File compressedFile = compressImage(file);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), compressedFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", compressedFile.getName(), requestFile);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceChangePicture service = retrofit.create(ApiServiceChangePicture.class);
        String token = "Bearer " + sharedPreferencesHelper.getToken();

        Call<ResponseBody> call = service.updateProfilePicture(token, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật hình ảnh thành công", Toast.LENGTH_SHORT).show();
                    aViewModel.fetchProfile(getContext());
                } else {
                    try {
                        String responseBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        Log.e("ProfileFragment", "Error response body: " + responseBody);
                        Log.e("ProfileFragment", "Error response code: " + response.code());
                        Log.e("ProfileFragment", "Error response message: " + response.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), "Cập nhật hình ảnh thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("ProfileFragment", "Network error", throwable);
                Toast.makeText(getContext(), "Network error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
