package frontend_book_market_app.polytechnic.client.profile.viewmodel;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import frontend_book_market_app.polytechnic.client.profile.model.AddressDeleteRequest;
import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;
import frontend_book_market_app.polytechnic.client.profile.model.UpdateAddressModel;
import frontend_book_market_app.polytechnic.client.profile.network.RepositoryAddress;
import frontend_book_market_app.polytechnic.client.profile.network.SharedService;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressViewModel extends ViewModel {
    private final MutableLiveData<List<AddressModel>> addressList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> addressAddSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> addressDeleteSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> addressUpdateSuccess = new MutableLiveData<>(); // New LiveData

    private final RepositoryAddress repositoryAddress;
    private final SharedPreferences sharedPreferences;
    private SharedPreferencesHelper sharedPreferencesHelper;



    private final Gson gson = new Gson(); // Initialize Gson
    SharedPreferences.Editor editor;

    public AddressViewModel(SharedPreferences sharedPreferences, RepositoryAddress repositoryAddress) {
        this.sharedPreferences = sharedPreferences;
        this.repositoryAddress = repositoryAddress;
    }

    public LiveData<List<AddressModel>> getAddressList() {
        return addressList;
    }

    public LiveData<Boolean> getAddressAddSuccess() {
        return addressAddSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getAddressDeleteSuccess() {
        return addressDeleteSuccess;
    }

    public LiveData<Boolean> getAddressUpdateSuccess() { // New getter method
        return addressUpdateSuccess;
    }

    public void addAddress(Context context, AddressModel addressModel) {
        String token = sharedPreferences.getString("token", ""); // Get token from SharedPreferences
        repositoryAddress.addAddress(token, addressModel, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("kkkkkkkkkkkkkk2");
                    addressAddSuccess.setValue(true);
                    System.out.println("heheheh"+ addressModel);

//                    setUserAddress(context, addressModel.getAddress_id() + "");
                    setUserAddress(context, "1");
                } else {
                    String error = "Error: " + response.code() + " - " + response.message();
                    errorMessage.setValue(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error = "Network Error: " + t.getMessage();
                errorMessage.setValue(error);
            }
        });
    }

    public void setUserAddress(Context context, String defaultAddress) {

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferencesHelper = new SharedPreferencesHelper(null);

        editor =  sharedPreferences.edit();

        editor.putString("default_address", defaultAddress);

        boolean isSaved = editor.commit(); // Use commit() to immediately save the data

        // Log the result
        if (isSaved) {
            Log.d("SharedPreferencesHelper123", "Default address saved successfully.");
            Log.d("SharedPreferencesHelper123", "DefaultAddress: " + defaultAddress);
        } else {
            Log.e("SharedPreferencesHelper123", "Failed to save default address.");
        }
    }


    public AddressModel getDefaultAddress() {
        List<AddressModel> addresses = addressList.getValue();
        if (addresses != null) {
            for (AddressModel address : addresses) {
                if (address.isIs_default()) {
                    return address;
                }
            }
        }
        return null;
    }




    public void loadAddresses() {
        String token = sharedPreferences.getString("token", "");
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), "{}" // Empty JSON if no data to send
        );

        repositoryAddress.getAllAddresses(token, requestBody, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().string();
                        Log.d("AddressViewModel", "Response: " + jsonResponse);
                        List<AddressModel> addresses = parseAddresses(jsonResponse);
                        addressList.setValue(addresses);
                        AddressModel defaultAddress = getDefaultAddress();
                        SharedService.getInstance().setDefaultAddress(defaultAddress);

                    } catch (IOException e) {
                        errorMessage.setValue("Error parsing response: " + e.getMessage());
                    }
                } else {
                    String error = "Error: " + response.code() + " - " + response.message();
                    errorMessage.setValue(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error = "Network Error: " + t.getMessage();
                errorMessage.setValue(error);
            }
        });
    }

    public void deleteAddress(int id) {
        String token = sharedPreferences.getString("token", "");
        AddressDeleteRequest request = new AddressDeleteRequest(id);
        repositoryAddress.deleteAddress(token, request, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    addressDeleteSuccess.setValue(true);
                } else {
                    String error = "Error: " + response.code() + " - " + response.message();
                    errorMessage.setValue(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error = "Network Error: " + t.getMessage();
                errorMessage.setValue(error);
            }
        });
    }

    public void updateAddress(UpdateAddressModel updatedAddress) {
        String token = sharedPreferences.getString("token", "");
        repositoryAddress.updateAddress(token, updatedAddress, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    addressUpdateSuccess.setValue(true);
                } else {
                    String error = "Error: " + response.code() + " - " + response.message();
                    errorMessage.setValue(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error = "Network Error: " + t.getMessage();
                errorMessage.setValue(error);
            }
        });
    }


    private List<AddressModel> parseAddresses(String jsonResponse) {
        List<AddressModel> addressList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            if (jsonArray != null) {
                Type listType = new TypeToken<List<AddressModel>>() {
                }.getType();
                addressList = gson.fromJson(jsonArray.toString(), listType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return addressList;
    }
}
