package frontend_book_market_app.polytechnic.client.home.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.adapter.ImageListAdapter;

public class BookImageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private TextView noDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewImages);
        noDataTextView = findViewById(R.id.noDataTextView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageListAdapter();
        recyclerView.setAdapter(adapter);

        ArrayList<String> avatarReviews = getIntent().getStringArrayListExtra("avatarReviews");
        if (avatarReviews != null && !avatarReviews.isEmpty()) {
            adapter.setImages(avatarReviews);
            noDataTextView.setVisibility(View.GONE);
        } else {
            noDataTextView.setVisibility(View.VISIBLE);
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
