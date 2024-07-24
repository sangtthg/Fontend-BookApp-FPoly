package sangttph30270.fptpoly.fontend_bookapp_fpoly.search.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sangttph30270.fptpoly.fontend_bookapp_fpoly.R;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.adapter.AdapterSachHome;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model.HomeBookModel;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.search.adapter.AdapterBookPopupNew;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.search.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {
    private ImageView imageViewBack;
    private EditText editTextSearch;
    private RecyclerView recyclerViewBooks;
    private ProgressBar progressBar;
    private AdapterBookPopupNew adapter;
    private List<HomeBookModel> bookList;
    private SearchViewModel searchViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageViewBack = findViewById(R.id.imageViewBack);
        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerViewBooks = findViewById(R.id.recyclerViewBooksLISTNEW);
        progressBar = findViewById(R.id.progressBar);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Khởi tạo ViewModel
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Thiết lập RecyclerView và Adapter
        bookList = new ArrayList<>();
        adapter = new AdapterBookPopupNew(bookList, new AdapterSachHome.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HomeBookModel selectedBook = bookList.get(position);
                Toast.makeText(SearchActivity.this, "Selected: " + selectedBook.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setAdapter(adapter);

        // Quan sát dữ liệu từ ViewModel
        observeViewModel();

        // Hiển thị ProgressBar khi tải dữ liệu
        showProgressBar(true);
        searchViewModel.fetchRandomBooks();
    }

    private void observeViewModel() {
        searchViewModel.getRandomBookList().observe(this, books -> {
            showProgressBar(false); // Ẩn ProgressBar khi dữ liệu đã tải xong
            if (books != null && !books.isEmpty()) {
                bookList.clear();
                bookList.addAll(books);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(SearchActivity.this, "No books available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerViewBooks.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerViewBooks.setVisibility(View.VISIBLE);
        }
    }
}
