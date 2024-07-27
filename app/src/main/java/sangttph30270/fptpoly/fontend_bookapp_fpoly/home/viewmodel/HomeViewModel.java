package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.favorite.model.ReviewRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartDeleteRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartItem;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartListResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.OrderRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.OrderResponseHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.PayOrderRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.PayOrderResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.ReviewResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.network.RepositoryHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view.BookDetailsActivity;

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
    private final MutableLiveData<Integer> cartItemCount = new MutableLiveData<>();
    private final MutableLiveData<OrderResponseHome> orderResponseLiveData = new MutableLiveData<>();

    private final MutableLiveData<Integer> idOrder = new MutableLiveData<>();
    private final MutableLiveData<List<Integer>> selectedCartItemIds = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<ReviewResponse> bookReviews = new MutableLiveData<>();

    private final MutableLiveData<ReviewResponse> reviewResponseLiveData = new MutableLiveData<>();


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


    public void submitReview(int bookId, int rating, String comment, Context context) {
        ReviewRequest reviewRequest = new ReviewRequest(bookId, rating, comment);
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
        repositoryHome.fetchTotalItemInCart(new Callback<CartListResponse>() {
            @Override
            public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int totalItems = response.body().getTotalItems();
                    Log.d(NAME, "Total items fetched: " + totalItems); // Debug log
                    Log.d(NAME, "Total items fetched1: " + response.body().getTotalItems()); // Debug log
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
        OrderRequest orderRequest = new OrderRequest(cartItemIds, address);
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

    public void payOrder(Context context, int orderID) {
//        Integer orderIdValue = idOrder.getValue();
//        if (orderIdValue == null) {
//            return;
//        }

        int id = 0;

        if (orderID == -1){
            id = idOrder.getValue();
        } else{
            id = orderID;
        }

        PayOrderRequest payOrderRequest = new PayOrderRequest(id);
        repositoryHome.payOrder(payOrderRequest, new Callback<PayOrderResponse>() {
            @Override
            public void onResponse(Call<PayOrderResponse> call, Response<PayOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PayOrderResponse payOrderResponse = response.body();
//                    Log.d(NAME, "Payment successful: " + payOrderResponse.getMessage());

                    String payUrl = payOrderResponse.getPayUrl();
                    System.out.println(payUrl);

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(payUrl));
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);
                } else {
                    Log.e(NAME, "Payment failed");
                }
            }

            @Override
            public void onFailure(Call<PayOrderResponse> call, Throwable t) {
                Log.e(NAME, "Error during payment: " + t.getMessage());
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