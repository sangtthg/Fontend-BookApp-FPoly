package frontend_book_market_app.polytechnic.client.search.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.adapter.AdapteHomerSach;
import frontend_book_market_app.polytechnic.client.home.model.HomeBookModel;
import frontend_book_market_app.polytechnic.client.search.adapter.AdapterAuthorSearch;
import frontend_book_market_app.polytechnic.client.search.adapter.AdapterBookPopupNew;
import frontend_book_market_app.polytechnic.client.search.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {
    private ImageView imageViewBack;
    private EditText editTextSearch;
    private RecyclerView recyclerViewBooks, recyclerViewBooksLISTTACGIA;
    private ProgressBar progressBar;
    private AdapterBookPopupNew bookAdapter;
    private AdapterAuthorSearch authorAdapter;
    private List<HomeBookModel> bookList;
    private List<HomeBookModel> authorList;
    private SearchViewModel searchViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageViewBack = findViewById(R.id.imageViewBack);
        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerViewBooks = findViewById(R.id.recyclerViewBooksLISTNEW);
        recyclerViewBooksLISTTACGIA = findViewById(R.id.recyclerViewBooksLISTTACGIA);
        progressBar = findViewById(R.id.progressBar);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Khởi tạo ViewModel
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Thiết lập RecyclerView và Adapter cho sách
        bookList = new ArrayList<>();
        bookAdapter = new AdapterBookPopupNew(bookList, new AdapteHomerSach.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HomeBookModel selectedBook = bookList.get(position);
                Toast.makeText(SearchActivity.this, "Selected: " + selectedBook.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setAdapter(bookAdapter);

        // Thiết lập RecyclerView và Adapter cho tác giả
        authorList = new ArrayList<>();
        authorAdapter = new AdapterAuthorSearch(authorList, new AdapterAuthorSearch.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HomeBookModel selectedAuthor = authorList.get(position);
                Toast.makeText(SearchActivity.this, "Selected Author: " + selectedAuthor.getAuthorName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewBooksLISTTACGIA.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooksLISTTACGIA.setAdapter(authorAdapter);

        // Quan sát dữ liệu từ ViewModel
        observeViewModel();

        // Hiển thị ProgressBar khi tải dữ liệu
        showProgressBar(true);
        searchViewModel.fetchRandomBooks();

        // Thêm TextWatcher cho EditText
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBooks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });
    }

    private void observeViewModel() {
        searchViewModel.getRandomBookList().observe(this, books -> {
            showProgressBar(false); // Ẩn ProgressBar khi dữ liệu đã tải xong
            if (books != null && !books.isEmpty()) {
                bookList.clear();
                bookList.addAll(books);
                bookAdapter.notifyDataSetChanged();

                // Lọc danh sách tác giả duy nhất từ danh sách sách
                Set<String> uniqueAuthors = new HashSet<>();
                List<HomeBookModel> uniqueAuthorModels = new ArrayList<>();

                for (HomeBookModel book : books) {
                    String authorName = book.getAuthorName();
                    if (authorName != null && !uniqueAuthors.contains(authorName)) {
                        uniqueAuthors.add(authorName);
                        // Tạo một đối tượng HomeBookModel mới cho tác giả
                        HomeBookModel authorModel = new HomeBookModel();
                        authorModel.setAuthorName(authorName);
                        uniqueAuthorModels.add(authorModel);
                    }
                }
                authorList.clear();
                authorList.addAll(uniqueAuthorModels);
                authorAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(SearchActivity.this, "No books available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterBooks(String query) {
        List<HomeBookModel> filteredBooks = new ArrayList<>();
        for (HomeBookModel book : bookList) {
            if (book.getTitle() != null && book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        bookAdapter.updateBookList(filteredBooks);
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerViewBooks.setVisibility(View.GONE);
            recyclerViewBooksLISTTACGIA.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerViewBooks.setVisibility(View.VISIBLE);
            recyclerViewBooksLISTTACGIA.setVisibility(View.VISIBLE);
        }
    }
}