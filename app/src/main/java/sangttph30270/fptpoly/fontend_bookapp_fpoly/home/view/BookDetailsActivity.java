package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import q.rorbin.badgeview.QBadgeView;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterBookDetail;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.CartDeleteRequest;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.DetailBookResponse;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.viewmodel.HomeViewModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.utils.SkeletonAdapter;

public class BookDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;

    private TextToSpeech tts;

    private boolean listening = false;


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
        int bookID = intent.getIntExtra("bookID", -1);


        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(new Locale("vi"));
            }
        });

        initView();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.fetchBookDetail(bookID);
        homeViewModel.fetchCartList();


        new QBadgeView(this)
                .bindTarget(findViewById(R.id.btnCart))
                .setBadgeNumber(25)
                .setBadgeBackgroundColor(Color.RED)
                .setBadgeTextColor(Color.WHITE)
                .setGravityOffset(-2, -1, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP);


        recyclerView = findViewById(R.id.recyclerViewDetailBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SkeletonAdapter skeletonAdapter = new SkeletonAdapter(1);
        recyclerView.setAdapter(skeletonAdapter);


        homeViewModel.getDetailBook().observe(this, detailBookResponse -> {
            List<Object> items = new ArrayList<>();
            items.add(detailBookResponse);
            AdapterBookDetail adapter = new AdapterBookDetail(items);
            RecyclerView recyclerView = findViewById(R.id.recyclerViewDetailBook);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        });

    }

    private void initView() {
        findViewById(R.id.backDetailButton).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.btnMuaNgay).setOnClickListener(v -> {
            Toast.makeText(this, "Mua Ngay", Toast.LENGTH_SHORT).show();
//            homeViewModel.updateSelectedCartItemIds(9, true);
//            System.out.println(homeViewModel.getSelectedCartItemIds());
        });

        findViewById(R.id.btnAddToCart).setOnClickListener(v -> {
            int bookId = getIntent().getIntExtra("bookID", -1);
            if (bookId != -1) {
                homeViewModel.addToCart(bookId, 1, this);
                Toast.makeText(this, "Đang thêm vào giỏ hàng...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lỗi: Không thể xác định ID sách", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnCallNow).setOnClickListener(v -> {
//            List<Integer> selectedCartItemIds = homeViewModel.getSelectedCartItemIds();
//
//            List<CartDeleteRequest.CartItemDelete> cartItemsToDelete = new ArrayList<>();
//            for (Integer cartItemId : selectedCartItemIds) {
//                cartItemsToDelete.add(new CartDeleteRequest.CartItemDelete(cartItemId));
//            }
//
//            homeViewModel.deleteCartItems(cartItemsToDelete);
//            Toast.makeText(this, "Deleting items...", Toast.LENGTH_SHORT).show();
        });;

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
            Intent intent = new Intent(BookDetailsActivity.this, ShoppingCartActivity.class);
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
