package frontend_book_market_app.polytechnic.client.home.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

//import q.rorbin.badgeview.QBadgeView;
import cn.bingoogolapple.badgeview.BGABadgeImageView;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapterBookDetail;
import frontend_book_market_app.polytechnic.client.home.model.DetailBookResponse;
import frontend_book_market_app.polytechnic.client.home.model.ReviewResponse;
import frontend_book_market_app.polytechnic.client.home.viewmodel.HomeViewModel;
import frontend_book_market_app.polytechnic.client.utils.SkeletonAdapter;

public class BookDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBookDetailScreen;
    private AdapterBookDetail adapter;

    private HomeViewModel homeViewModel;
    private int bookID;

    private TextToSpeech tts;

    private boolean listening = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SkeletonAdapter skeletonAdapter;


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
//        homeViewModel.getCartItemCount().observe(this, itemCount -> {
//            Log.d("BookDetailsActivity", "Updating badge count: " + itemCount);
////            new QBadgeView(this)
////                    .bindTarget(findViewById(R.id.btnCart))
////                    .setBadgeNumber(itemCount)
////                    .setBadgeBackgroundColor(Color.RED)
////                    .setBadgeTextColor(Color.WHITE)
////                    .setGravityOffset(-2, -1, true)
////                    .setBadgeGravity(Gravity.END | Gravity.TOP);
//        });

        homeViewModel.getCartItemCount().observe(this, itemCount -> {
            Log.d("BookDetailsActivity", "Updating badge count: " + itemCount); // Debug log
            @SuppressLint("WrongViewCast") BGABadgeImageView badgeImageView = findViewById(R.id.btnCart); // Thay đổi này
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
        showReviewDialogButton.setOnClickListener(v -> showReviewDialog());


    }

    private void setupRecyclerView() {
        recyclerViewBookDetailScreen = findViewById(R.id.recyclerViewDetailBook);
        recyclerViewBookDetailScreen.setLayoutManager(new LinearLayoutManager(this));
        skeletonAdapter = new SkeletonAdapter(5);
        recyclerViewBookDetailScreen.setAdapter(skeletonAdapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerViewBookDetailScreen.setAdapter(skeletonAdapter);
            homeViewModel.fetchBookDetail(bookID);
            homeViewModel.fetchBookReviews(bookID);
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

        findViewById(R.id.btnThanhToan).setOnClickListener(v -> {
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
            int bookId = getIntent().getIntExtra("bookID", -1);
            Intent intent = new Intent(BookDetailsActivity.this, BookImageActivity.class);
            intent.putExtra("bookID", bookId);
            startActivity(intent);
        });

        findViewById(R.id.btnCart).setOnClickListener(v -> {
            Intent intent = new Intent(BookDetailsActivity.this, CartActivity.class);
            startActivity(intent);
        });
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
