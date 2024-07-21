package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartDeleteRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartItem;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network.RepositoryHome;

public class HomeViewModel extends ViewModel {
    private final String NAME = this.getClass().getSimpleName();
    private final RepositoryHome repositoryHome = new RepositoryHome();
    private final MutableLiveData<List<HomeBookModel>> newBookList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> bestSellerBookList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> randomBooksList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> mostViewBooksList = new MutableLiveData<>();
    private final MutableLiveData<DetailBookResponse> detailBook = new MutableLiveData<>();
    private final MutableLiveData<Integer> badge = new MutableLiveData<>();
    private final MutableLiveData<String> listen = new MutableLiveData<>();

    private final List<Integer> selectedCartItemIds = new ArrayList<>();

    private MutableLiveData<List<CartListResponse.CartItemDetail>> cartItemList = new MutableLiveData<>();


    public LiveData<List<HomeBookModel>> getBestSellerBookList() {
        return bestSellerBookList;
    }

    public LiveData<List<HomeBookModel>> getNewBookList() {
        return newBookList;
    }

    public LiveData<List<HomeBookModel>> getRandomBookList() {
        return randomBooksList;
    }

    public LiveData<List<HomeBookModel>> getMostViewBooksList() {
        return mostViewBooksList;
    }

    public LiveData<DetailBookResponse> getDetailBook() {
        return detailBook;
    }

    public LiveData<List<CartListResponse.CartItemDetail>> getCartItemList() {
        return cartItemList;
    }

    public List<Integer> getSelectedCartItemIds() {
        return selectedCartItemIds;
    }

    public MutableLiveData<String> getListen() {
        return listen;
    }

    public LiveData<Integer> getBadge() {
        return badge;
    }

    public void fetchHomeBookAPI() {
        repositoryHome.fetchApiHomePageBook(new Callback<HomeBookResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeBookResponse> call, @NonNull Response<HomeBookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HomeBookResponse.BookData data = response.body().getData();
                    if (data != null) {
                        if (data.getBestSellerBooks() != null) {
                            bestSellerBookList.postValue(data.getBestSellerBooks());
                            Log.d(NAME, "Fetch SellerBookList Success");
                        }
                        if (data.getNewBooks() != null) {
                            newBookList.postValue(data.getNewBooks());
                            Log.d(NAME, "Fetch New Book Success");
                        }
                        if (data.getRandomBooks() != null) {
                            randomBooksList.postValue(data.getRandomBooks());
                            Log.d(NAME, "Fetch Random Success");
                        }
                        if (data.getMostViewBooks() != null) {
                            mostViewBooksList.postValue(data.getMostViewBooks());
                            Log.d(NAME, "Fetch MostViewBooks Success:");
                        }
                    } else {
//                        Log.e(NAME, "Không có dữ liệu nào trả về");
                        logErrorResponse("Không có dữ liệu nào trả về", response);
                    }
                } else {
                    logErrorResponse("Fetch HomeBookAPI failed: ", response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeBookResponse> call, @NonNull Throwable t) {
                Log.wtf(NAME, "Fetch HomeBookAPI failed onFailure: ", t);
            }
        });
    }

    public void fetchBookDetail(int bookId) {
        repositoryHome.fetchBookDetail(bookId, new Callback<DetailBookResponse>() {
            @Override
            public void onResponse(@NonNull Call<DetailBookResponse> call, @NonNull Response<DetailBookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detailBook.postValue(response.body());
                    listen.postValue(response.body().getData().getDescription());
                    Log.d(NAME, "Fetch BookDetail Success");
                } else {
                    logErrorResponse("Fetch BookDetail failed: ", response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailBookResponse> call, @NonNull Throwable t) {
                Log.e(NAME, "Fetch BookDetail onFailure: ", t);
            }
        });
    }

    //=============

    public void fetchCartList() {
    repositoryHome.fetchCartList(new Callback<CartListResponse>() {
        @Override
        public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                CartListResponse.CartData cartData = response.body().getData();
                List<CartListResponse.CartItemDetail> cartItems = cartData.getData();
                cartItemList.postValue(cartItems); // Update LiveData list
            } else {
                System.out.println("Failed to fetch cart list");
                cartItemList.postValue(new ArrayList<>()); // Post an empty list in case of failure
            }
        }

        @Override
        public void onFailure(Call<CartListResponse> call, Throwable t) {
            System.out.println("Error fetching cart list: " + t.getMessage());
            cartItemList.postValue(new ArrayList<>()); // Post an empty list in case of failure
        }
    });
}

    public void addToCart(int bookId, int quantity, Context context) {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(bookId, quantity));
        CartRequest cartRequest = new CartRequest(cartItems);

        repositoryHome.addToCart(cartRequest, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Đã thêm!", Toast.LENGTH_SHORT).show();
                } else {
                    logErrorResponse("Failed to add to cart", response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(NAME, "Error adding to cart", t);
            }
        });
    }

    public void updateSelectedCartItemIds(int cartItemId, boolean isSelected) {
        if (isSelected) {
            if (!selectedCartItemIds.contains(cartItemId)) {
                selectedCartItemIds.add(cartItemId);
            }
        } else {
            selectedCartItemIds.remove(Integer.valueOf(cartItemId));
        }
    }

    public void deleteCartItems(List<CartDeleteRequest.CartItemDelete> cartItems) {
        CartDeleteRequest request = new CartDeleteRequest(cartItems);
        repositoryHome.deleteCartItems(request, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(NAME, "Items deleted successfully");
                } else {
                    logErrorResponse("Failed to delete items", response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(NAME, "Error deleting items", t);
            }
        });
    }


    public void clearAllLists() {
        bestSellerBookList.postValue(new ArrayList<>());
        newBookList.postValue(new ArrayList<>());
        randomBooksList.postValue(new ArrayList<>());
        mostViewBooksList.postValue(new ArrayList<>());
    }

    private void logErrorResponse(String name, @NonNull Response<?> response) {
        String logMessage = String.format("📛 Lỗi: %s => Mã lỗi: %d => Thông báo: %s", name, response.code(), response.message());
        Log.e(NAME, logMessage);

        ResponseBody errorBody = response.errorBody();
        if (errorBody != null) {
            try (ResponseBody responseBody = errorBody) {
                String errorMessage = String.format("📛 Nội dung lỗi: %s", responseBody.string());
                Log.e(NAME, errorMessage);
            } catch (IOException e) {
                Log.e(NAME, "📛 Lỗi khi đọc nội dung lỗi", e);
            }
        } else {
            Log.e(NAME, "📛 Nội dung lỗi không tồn tại");
        }
    }

}
