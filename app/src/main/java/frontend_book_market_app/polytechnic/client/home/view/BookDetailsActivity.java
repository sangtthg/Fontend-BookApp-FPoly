package frontend_book_market_app.polytechnic.client.home.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.bingoogolapple.badgeview.BGABadgeImageView;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.auth.login.view.LoginScreen;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapterBookDetail;
import frontend_book_market_app.polytechnic.client.home.model.DetailBookResponse;
import frontend_book_market_app.polytechnic.client.home.model.ReviewResponse;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.notification.view.NotificationFragment;
import frontend_book_market_app.polytechnic.client.utils.SharedPreferencesHelper;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class BookDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBookDetailScreen;
    private AdapterBookDetail adapter;

    private HomeViewModel homeViewModel;
    private int bookID;

    private TextToSpeech tts;

    private boolean listening = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SkeletonAdapter skeletonAdapter;
    private SharedPreferencesHelper sharedPreferencesHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferencesHelper = new SharedPreferencesHelper(this);

        Intent intent = getIntent();
        bookID = intent.getIntExtra("bookID", -1);
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(new Locale("vi"));
            }
        });

        initView();
        setupRecyclerView();


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.fetchBookDetail(bookID);

        homeViewModel.getCartItemCount().observe(this, itemCount -> {
            @SuppressLint("WrongViewCast") BGABadgeImageView badgeImageView = findViewById(R.id.btnCart);
            badgeImageView.showCirclePointBadge();
            badgeImageView.showTextBadge(String.valueOf(itemCount));
            badgeImageView.getBadgeViewHelper().setBadgeBgColorInt(Color.RED);
            badgeImageView.getBadgeViewHelper().setBadgeTextColorInt(Color.WHITE);
        });
        homeViewModel.getDetailBook().observe(this, new Observer<DetailBookResponse>() {
            @Override
            public void onChanged(DetailBookResponse detailBookResponse) {
                List<Object> items = new ArrayList<>();
                items.add(detailBookResponse);

                homeViewModel.getBookReviews().observe(BookDetailsActivity.this, new Observer<ReviewResponse>() {
                    @Override
                    public void onChanged(ReviewResponse reviewResponse) {
                        items.remove("EMPTY_STATE");
                        if (reviewResponse != null && reviewResponse.getReviews() != null && !reviewResponse.getReviews().isEmpty()) {
                            items.addAll(reviewResponse.getReviews());
                        } else {
                            items.add("EMPTY_STATE");
                        }
                        adapter = new AdapterBookDetail(items);
                        recyclerViewBookDetailScreen.setAdapter(adapter);
                    }
                });
            }
        });

        ImageButton showReviewDialogButton = findViewById(R.id.btnCallNow);
        showReviewDialogButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                makePhoneCall();
            }
        });;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makePhoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0969086132"));
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        } else {
            Toast.makeText(BookDetailsActivity.this, "No application available to handle the call", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerView() {
        recyclerViewBookDetailScreen = findViewById(R.id.recyclerViewDetailBook);
        recyclerViewBookDetailScreen.setLayoutManager(new LinearLayoutManager(this));
        skeletonAdapter = new SkeletonAdapter(5);
        recyclerViewBookDetailScreen.setAdapter(skeletonAdapter);


        swipeRefreshLayout.setOnRefreshListener(() -> {
            homeViewModel.clearBookReviews();
            swipeRefreshLayout.setRefreshing(true);
            recyclerViewBookDetailScreen.setAdapter(skeletonAdapter);
            homeViewModel.fetchBookDetail(bookID);
//            homeViewModel.fetchBookReviews(bookID);
            homeViewModel.fetchTotalItemInCart();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void showReviewDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_review);

        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        EditText editTextComment = dialog.findViewById(R.id.editTextComment);
        MaterialButton buttonSubmit = dialog.findViewById(R.id.buttonSubmit);

//        buttonSubmit.setOnClickListener(v -> {
//            int rating = (int) ratingBar.getRating();
//            String comment = editTextComment.getText().toString();
//
//            homeViewModel.submitReview(bookID, rating, comment, getApplicationContext());
//
//            dialog.dismiss();
//        });

        dialog.show();
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        findViewById(R.id.backDetailButton).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.btnDatHang).setOnClickListener(v -> {
            int bookId = getIntent().getIntExtra("bookID", -1);
            if (bookId != -1) {
                homeViewModel.addToCart(bookId, 1, this);
            } else {
                Toast.makeText(this, "Lỗi: Không thể xác định ID sách", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnCallNow).setOnClickListener(v -> {
        });

        ImageButton btnListen = findViewById(R.id.btnListen);
        btnListen.setOnClickListener(v -> {
            if (!listening) {
                homeViewModel.getListen().observe(this, s -> {
                    if (s != null) {
                        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");
                    }
                });
                btnListen.setImageResource(R.drawable.ic_pause);
                listening = true;
            } else {
                tts.stop();
                btnListen.setImageResource(R.drawable.ic_listen);
                listening = false;
            }
        });


        ImageButton btnDocThu = findViewById(R.id.btnDocThu);
        btnDocThu.setOnClickListener(v -> {
            List<String> reviewImages = homeViewModel.getAvatarReviews();
            Intent intent = new Intent(BookDetailsActivity.this, BookImageActivity.class);
            intent.putStringArrayListExtra("avatarReviews", new ArrayList<>(reviewImages));
            startActivity(intent);
        });

        findViewById(R.id.btnCart).setOnClickListener(v -> {
            if (checkUserLogin()) {
                Intent intent = new Intent(BookDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkUserLogin() {
        String token = sharedPreferencesHelper.getToken();
        if (token == null || token.isEmpty()) {
            Log.d("HomeFragment", "User is not logged in.");
            promptLogin();
            return false;
        } else {
            Log.d("HomeFragment", "User is logged in.");
            return true;
        }
    }

    private void promptLogin() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_custom_login, null);

        Button btnDialogCancel = dialogView.findViewById(R.id.btnDialogCancel);
        Button btnDialogOk = dialogView.findViewById(R.id.btnDialogOk);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        btnDialogOk.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            dialog.dismiss();
        });

        btnDialogCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onResume() {
        homeViewModel.fetchTotalItemInCart();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }



}

