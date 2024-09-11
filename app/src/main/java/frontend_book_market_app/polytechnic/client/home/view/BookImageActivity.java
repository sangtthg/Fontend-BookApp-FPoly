package frontend_book_market_app.polytechnic.client.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.adapter.ImageListAdapter;
import frontend_book_market_app.polytechnic.client.home.viewmodel.ImageListViewModel;

public class BookImageActivity extends AppCompatActivity {
    private ImageListViewModel imageListViewModel;
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageListAdapter();
        recyclerView.setAdapter(adapter);

        imageListViewModel = new ViewModelProvider(this).get(ImageListViewModel.class);
        imageListViewModel.getImages().observe(this, images -> {
            System.out.println("kkkkkk " + images);
            adapter.setImages(images);
        });

        int bookId = getIntent().getIntExtra("bookID", -1);
        Log.d("Log id hien tai","bookId" + bookId);
        imageListViewModel.fetchImages(bookId);
//        imageListViewModel.fetchImages(83);


        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}