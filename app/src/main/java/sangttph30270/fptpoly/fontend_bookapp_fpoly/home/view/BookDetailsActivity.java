package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import q.rorbin.badgeview.QBadgeView;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterBookDetail;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

public class BookDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;
    private int bookID;

    private TextToSpeech tts;

    private boolean listening = false;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_details);

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

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.fetchBookDetail(bookID);
        homeViewModel.fetchBookReviews(bookID);
        homeViewModel.fetchTotalItemInCart();

        homeViewModel.getCartItemCount().observe(this, itemCount -> {
            Log.d("BookDetailsActivity", "Updating badge count: " + itemCount); // Debug log
            new QBadgeView(this)
                    .bindTarget(findViewById(R.id.btnCart))
                    .setBadgeNumber(itemCount)
                    .setBadgeBackgroundColor(Color.RED)
                    .setBadgeTextColor(Color.WHITE)
                    .setGravityOffset(-2, -1, true)
                    .setBadgeGravity(Gravity.END | Gravity.TOP);
        });

        recyclerView = findViewById(R.id.recyclerViewDetailBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SkeletonAdapter skeletonAdapter = new SkeletonAdapter(5);
        recyclerView.setAdapter(skeletonAdapter);

        recyclerView = findViewById(R.id.recyclerViewDetailBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homeViewModel.getDetailBook().observe(this, detailBookResponse -> {
            List<Object> items = new ArrayList<>();
            items.add(detailBookResponse);

            homeViewModel.getBookReviews().observe(this, reviewResponse -> {
                if (reviewResponse != null && reviewResponse.getReviews() != null) {
                    items.addAll(reviewResponse.getReviews());
                }
                AdapterBookDetail adapter = new AdapterBookDetail(items);
                recyclerView.setAdapter(adapter);
            });
        });

        ImageButton showReviewDialogButton = findViewById(R.id.btnCallNow);
        showReviewDialogButton.setOnClickListener(v -> showReviewDialog());

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerView.setAdapter(skeletonAdapter);
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

        buttonSubmit.setOnClickListener(v -> {
            int rating = (int) ratingBar.getRating();
            String comment = editTextComment.getText().toString();

            homeViewModel.submitReview(bookID, rating, comment, getApplicationContext());

            dialog.dismiss();
        });

        dialog.show();
    }

    private void initView() {
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

        findViewById(R.id.btnCart).setOnClickListener(v -> {
            Intent intent = new Intent(BookDetailsActivity.this, CartActivity.class);
            startActivity(intent);
        });
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