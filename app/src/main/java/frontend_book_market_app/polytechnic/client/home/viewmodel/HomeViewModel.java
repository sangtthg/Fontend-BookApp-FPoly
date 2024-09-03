package frontend_book_market_app.polytechnic.client.home.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frontend_book_market_app.polytechnic.client.auth.login.model.AddressModel;
import frontend_book_market_app.polytechnic.client.order_user.model.OrderItem;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import frontend_book_market_app.polytechnic.client.favorite.model.ReviewRequest;
import frontend_book_market_app.polytechnic.client.home.model.CartDeleteRequest;
import frontend_book_market_app.polytechnic.client.home.model.CartItem;
import frontend_book_market_app.polytechnic.client.home.model.CartListResponse;
import frontend_book_market_app.polytechnic.client.home.model.CartRequest;
import frontend_book_market_app.polytechnic.client.home.model.DetailBookResponse;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookModel;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookResponse;
import frontend_book_market_app.polytechnic.client.home.model.OrderRequest;
import frontend_book_market_app.polytechnic.client.home.model.OrderResponseHome;
import frontend_book_market_app.polytechnic.client.home.model.PayOrderRequest;
import frontend_book_market_app.polytechnic.client.home.model.PayOrderResponse;
import frontend_book_market_app.polytechnic.client.home.model.ReviewResponse;
import frontend_book_market_app.polytechnic.client.home.network.RepositoryHome;
import frontend_book_market_app.polytechnic.client.home.view.BookDetailsActivity;
import frontend_book_market_app.polytechnic.client.WebViewActivity;

public class HomeViewModel extends ViewModel {
    private final String NAME = this.getClass().getSimpleName();
    private final SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(null);

    private final RepositoryHome repositoryHome = new RepositoryHome();
    private final MutableLiveData<List<HomeBookModel>> newBookList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> bestSellerBookList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> randomBooksList = new MutableLiveData<>();
    private final MutableLiveData<List<HomeBookModel>> mostViewBooksList = new MutableLiveData<>();
    private final MutableLiveData<DetailBookResponse> detailBook = new MutableLiveData<>();
    private final MutableLiveData<Integer> badge = new MutableLiveData<>();
    private final MutableLiveData<String> listen = new MutableLiveData<>();
    private final MutableLiveData<Integer> cartItemCount = new MutableLiveData<>();
    private final MutableLiveData<OrderResponseHome> orderResponseLiveData = new MutableLiveData<>();

    private final MutableLiveData<Integer> idOrder = new MutableLiveData<>();
    private final MutableLiveData<List<Integer>> selectedCartItemIds = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<ReviewResponse> bookReviews = new MutableLiveData<>();

    private final MutableLiveData<ReviewResponse> reviewResponseLiveData = new MutableLiveData<>();
    private final List<OrderItem> selectedItems = new ArrayList<>();


    List<Integer> cartItemIds = Arrays.asList(156, 157);
    String address = "Địa chỉ";


    private final MutableLiveData<List<CartListResponse.CartItemDetail>> cartItemList = new MutableLiveData<>();


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

    public LiveData<ReviewResponse> getReviewResponseLiveData() {
        return reviewResponseLiveData;
    }


    public MutableLiveData<String> getListen() {
        return listen;
    }

    public LiveData<Integer> getBadge() {
        return badge;
    }

    public MutableLiveData<Integer> getCartItemCount() {
        return cartItemCount;
    }

    public LiveData<OrderResponseHome> getOrderResponseLiveData() {
        return orderResponseLiveData;
    }

    public MutableLiveData<List<Integer>> getSelectedCartItemIds() {
        return selectedCartItemIds;
    }

    public LiveData<ReviewResponse> getBookReviews() {
        return bookReviews;
    }


    public void addItem(OrderItem item) {
        if (!selectedItems.contains(item)) {
            selectedItems.add(item);
        }
    }

    public void removeItem(OrderItem item) {
        selectedItems.remove(item);
    }

    public List<OrderItem> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }

    public List<Integer> getSelectedItemIds() {
        List<Integer> selectedItemIds = new ArrayList<>();
        for (OrderItem item : selectedItems) {
            selectedItemIds.add(item.getBook_id());
        }
        return selectedItemIds;
    }

    public void fetchHomeBookAPI() {
        repositoryHome.fetchApiHomePageBook(new Callback<HomeBookResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeBookResponse> call, @NonNull Response<HomeBookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HomeBookResponse.BookData data = response.body().getData();
                    if (data != null) {
                        postBookList(data.getBestSellerBooks(), bestSellerBookList, "Fetch SellerBookList Success");
                        postBookList(data.getNewBooks(), newBookList, "Fetch New Book Success");
                        postBookList(data.getRandomBooks(), randomBooksList, "Fetch Random Success");
                        postBookList(data.getMostViewBooks(), mostViewBooksList, "Fetch MostViewBooks Success");
                    } else {
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

    private void postBookList(List<HomeBookModel> books, MutableLiveData<List<HomeBookModel>> liveData, String successMessage) {
        if (books != null) {
            liveData.postValue(books);
            Log.d(NAME, successMessage);
        }
    }

    public void fetchBookDetail(int bookId) {
        repositoryHome.fetchBookDetail(bookId, new Callback<DetailBookResponse>() {
            @Override
            public void onResponse(@NonNull Call<DetailBookResponse> call, @NonNull Response<DetailBookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detailBook.postValue(response.body());
                    listen.postValue(response.body().getData().getDescription());
                    Log.d(NAME, "Fetch BookDetail Success");
                    fetchBookReviews(bookId);
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

    public void fetchBookReviews(int bookId) {
        repositoryHome.fetchBookReviews(bookId, new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getReviews() != null) {
                    bookReviews.postValue(response.body());
                } else {
                    Log.e(NAME, "Failed to fetch book reviews: " + response.body());
                    bookReviews.postValue(new ReviewResponse(new ArrayList<>()));
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(NAME, "Error fetching book reviews", t);
                bookReviews.postValue(new ReviewResponse(new ArrayList<>()));
            }
        });
    }


    public void submitReview(int bookId, int orderID, int rating, String comment, Context context) {
        ReviewRequest reviewRequest = new ReviewRequest(bookId, orderID, rating, comment);
        repositoryHome.submitReview(reviewRequest, new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, "Đánh giá sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(context, BookDetailsActivity.class);
                        intent.putExtra("bookID", bookId);
                        context.startActivity(intent);
                    }, 2000);
                    reviewResponseLiveData.postValue(response.body());
                } else {
                    reviewResponseLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                reviewResponseLiveData.postValue(null);
            }
        });
    }

    //=============

    public void fetchCartList() {
        repositoryHome.fetchCartList(new Callback<CartListResponse>() {
            @Override
            public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {
                cartItemCount.postValue(0);
                if (response.isSuccessful() && response.body() != null) {
                    CartListResponse.CartData cartData = response.body().getData();
                    List<CartListResponse.CartItemDetail> cartItems = cartData.getData();
                    cartItemList.postValue(cartItems);
                } else {
                    System.out.println("Failed to fetch cart list");
                    cartItemList.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<CartListResponse> call, Throwable t) {
                System.out.println("Error fetching cart list: " + t.getMessage());
                cartItemList.postValue(new ArrayList<>());
                cartItemCount.postValue(0);
            }
        });
    }

    public void fetchTotalItemInCart() {
        String token = sharedPreferencesHelper.getToken();
        if (!token.isEmpty()) {
            repositoryHome.fetchTotalItemInCart(new Callback<CartListResponse>() {
                @Override
                public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int totalItems = response.body().getTotalItems();
                        Log.d(NAME, "Total items fetched: " + totalItems);
                        cartItemCount.postValue(totalItems);
                    } else {
                        Log.d(NAME, "Failed to fetch total items. Response unsuccessful or null.");
                        cartItemCount.postValue(0);
                    }
                }

                @Override
                public void onFailure(Call<CartListResponse> call, Throwable t) {
                    Log.e(NAME, "Error fetching total items: " + t.getMessage());
                    cartItemCount.postValue(0);
                }
            });
        }
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
                    fetchTotalItemInCart();
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

    public void updateCartQuantity(int cartId, int quantity, Callback<Void> callback) {
        repositoryHome.updateCartQuantity(cartId, quantity, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchCartList();
                } else {
                    Log.e(NAME, "Failed to update cart quantity");
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(NAME, "Error updating cart quantity", t);
                callback.onFailure(call, t);
            }
        });
    }


    public void updateSelectedCartItemIds(int cartItemId, boolean isSelected) {
        List<Integer> currentList = selectedCartItemIds.getValue();
        if (currentList == null) currentList = new ArrayList<>();

        if (isSelected) {
            if (!currentList.contains(cartItemId)) {
                currentList.add(cartItemId);
            }
        } else {
            currentList.remove(Integer.valueOf(cartItemId));
        }
        selectedCartItemIds.setValue(currentList);
        System.out.println("Current selectedCartItemIds: " + selectedCartItemIds.getValue());
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


    public void fetchOrderByCartID(List<Integer> cartItemIds) {
        idOrder.postValue(0);
        System.out.println(cartItemIds);

        AddressModel defaultAddressModel = getDefaultAddress();
        String phone = defaultAddressModel.getPhone();
        String diaChi = defaultAddressModel.getAddress();

        OrderRequest orderRequest = new OrderRequest(cartItemIds, diaChi, phone);
        repositoryHome.createOrder(orderRequest, new Callback<OrderResponseHome>() {
            @Override
            public void onResponse(Call<OrderResponseHome> call, Response<OrderResponseHome> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderResponseHome orderResponseHome = response.body();
                    Log.d(NAME, "Order created successfully: " + orderResponseHome.getMessage());
                    orderResponseLiveData.postValue(orderResponseHome);
                    idOrder.postValue(response.body().getOrderId());
                } else {
                    logErrorResponse("Failed to create order", response);
                }
            }

            @Override
            public void onFailure(Call<OrderResponseHome> call, Throwable t) {
                Log.e(NAME, "Error creating order", t);
            }
        });
    }

    public void payOrder(Context context, int orderID, String discountCode, String token) {
        Toast.makeText(context, "Chuẩn bị tiến hành thanh toán...", Toast.LENGTH_LONG).show();

        if (token.isEmpty()) {
            Toast.makeText(context, "Token không hợp lệ, không thể thanh toán!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Xác định ID đơn hàng
        int id = (orderID == -1) ? idOrder.getValue() : orderID;

        // Tạo đối tượng yêu cầu thanh toán
        PayOrderRequest payOrderRequest = new PayOrderRequest(id, discountCode, token);

        // Ghi log thông tin yêu cầu
        Log.d("HomeViewModel", "Sending PayOrderRequest with ID: " + id + " and DiscountCode: " + discountCode);
        Log.d("HomeViewModel", "PayOrderRequest: ID=" + id + ", DiscountCode=" + discountCode + ", Token=" + token);

        // Gửi yêu cầu thanh toán
        repositoryHome.payOrder(payOrderRequest, new Callback<PayOrderResponse>() {
            @Override
            public void onResponse(Call<PayOrderResponse> call, Response<PayOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy URL thanh toán từ phản hồi
                    String payUrl = response.body().getPayUrl();

                    // Ghi log thông tin phản hồi
                    Log.d("HomeViewModel", "Payment URL: " + payUrl);

                    // Mở WebViewActivity với URL thanh toán
                    if (payUrl != null && !payUrl.isEmpty()) {
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra("url", payUrl);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Log.e("HomeViewModel", "Invalid payment URL received");
                    }
                } else {
                    // Ghi log lỗi nếu thanh toán không thành công
                    String errorMessage = response.errorBody() != null ? response.errorBody().toString() : "Unknown error";
                    Log.e("HomeViewModel", "Payment failed: " + response.message() + ", Error Body: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<PayOrderResponse> call, Throwable t) {
                // Ghi log lỗi nếu có lỗi trong quá trình thanh toán
                Log.e("HomeViewModel", "Error during payment: " + t.getMessage());
            }
        });
    }


    private AddressModel getDefaultAddress() {
        List<AddressModel> addresses = sharedPreferencesHelper.getAddresses();

        if (addresses == null || addresses.isEmpty()) {
            return null;
        }

        for (AddressModel address : addresses) {
            if (address.isDefault()) {
                return address;
            }
        }

        return null;
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