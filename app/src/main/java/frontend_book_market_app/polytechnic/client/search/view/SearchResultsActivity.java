package frontend_book_market_app.polytechnic.client.search.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import frontend_book_market_app.polytechnic.client.MainActivity;
import frontend_book_market_app.polytechnic.client.R;
import frontend_book_market_app.polytechnic.client.home.view.BookDetailsActivity;
import frontend_book_market_app.polytechnic.client.search.adapter.AdapterSearchView;
import frontend_book_market_app.polytechnic.client.search.model.Book;
import frontend_book_market_app.polytechnic.client.search.viewmodel.SearchViewModel;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBooks;
    private AdapterSearchView bookAdapter;
    private List<Book> bookList;
    private ImageView imageViewBackSearch, imageViewSearch2,imageViewClear2;
    private EditText editTextSearch2;
    private ProgressBar progressBar;
    private SearchViewModel searchViewModel;
    private TextView textViewNoBooks;
    private Button buttonFilterByAuthor, buttonSortByPrice;
    private boolean isSortByPriceDescending = true; // Flag to track the sorting order
    private Spinner spinnerFilterByAuthor;
    private List<String> authorList = new ArrayList<>();

    private ArrayAdapter<String> authorAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        imageViewClear2= findViewById(R.id.imageViewClear2);
        spinnerFilterByAuthor = findViewById(R.id.spinnerFilterByAuthor);
        buttonSortByPrice = findViewById(R.id.buttonSortByPrice);
        textViewNoBooks = findViewById(R.id.textViewNoBooks);
        recyclerViewBooks = findViewById(R.id.recyclerViewBooksSearch);
        imageViewBackSearch = findViewById(R.id.imageViewBackSearch);
        imageViewSearch2 = findViewById(R.id.imageViewSearch2);
        editTextSearch2 = findViewById(R.id.editTextSearch2);
        progressBar = findViewById(R.id.progressBarSearch);
        imageViewClear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSearch2.setText("");
            }
        });
        Intent intentText = getIntent();
        if (intentText != null && intentText.hasExtra("SEARCH_QUERY")) {
            String query = intentText.getStringExtra("SEARCH_QUERY");
            editTextSearch2.setText(query);
        }

        imageViewBackSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        buttonSortByPrice.setOnClickListener(new View.OnClickListener() {
            private boolean isDescending = true; // Track the current sort order

            @Override
            public void onClick(View v) {
                if (isDescending) {
                    // Sort by price from high to low
                    bookList.sort(new Comparator<Book>() {
                        @Override
                        public int compare(Book b1, Book b2) {
                            try {
                                double price1 = Double.parseDouble(b1.getNew_price());
                                double price2 = Double.parseDouble(b2.getNew_price());
                                return Double.compare(price2, price1); // Descending order
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                return 0; // In case of a format error, treat as equal
                            }
                        }
                    });
                    buttonSortByPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_downward_24, 0); // Set down arrow
                } else {
                    // Sort by price from low to high
                    bookList.sort(new Comparator<Book>() {
                        @Override
                        public int compare(Book b1, Book b2) {
                            try {
                                double price1 = Double.parseDouble(b1.getNew_price());
                                double price2 = Double.parseDouble(b2.getNew_price());
                                return Double.compare(price1, price2); // Ascending order
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                return 0; // In case of a format error, treat as equal
                            }
                        }
                    });
                    buttonSortByPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_arrow_upward_24, 0); // Set up arrow
                }
                bookAdapter.notifyDataSetChanged(); // Update the RecyclerView with the new order
                isDescending = !isDescending; // Toggle sort order
            }
        });

        imageViewSearch2.setOnClickListener(v -> {

                String query = editTextSearch2.getText().toString().trim();
                if (!query.isEmpty()) {
                    showProgressBar(true);
                    performSearch(query);  // Perform the search
                } else {
                    Toast.makeText(SearchResultsActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
                }

        });


        editTextSearch2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                showProgressBar(true);
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {

                        String query = editTextSearch2.getText().toString().trim();
                        if (!query.isEmpty()) {
                            performSearch(query);  // Perform the search
                        } else {
                            Toast.makeText(SearchResultsActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
                        }

                    return true;
                }
                return false;
            }
        });
        bookList = new ArrayList<>();
        bookAdapter = new AdapterSearchView(bookList, bookId -> {
            // Xử lý sự kiện nhấp chuột
            if (isUserLoggedIn()) {
                Intent intent = new Intent(SearchResultsActivity.this, BookDetailsActivity.class);
                intent.putExtra("bookID", bookId);
                startActivity(intent);
            } else {
                promptLogin();
            }
        });

        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setLayoutManager(new GridLayoutManager(this, 2)); // 2 là số cột

        recyclerViewBooks.setAdapter(bookAdapter);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);


        Intent intent = getIntent();
        if (intent != null) {
            boolean hasBooks = intent.getBooleanExtra("HAS_BOOKS", false);

            if (hasBooks) {
                ArrayList<Book> books = (ArrayList<Book>) intent.getSerializableExtra("BOOK_LIST");
                if (books != null) {
                    bookList.clear();
                    bookList.addAll(books);
                    bookAdapter.notifyDataSetChanged();
                    textViewNoBooks.setVisibility(View.GONE);
                    for (Book book : bookList) {
                        Log.d("SearchResultsActivity", "Book ID: " + book.getBook_id());
                        Log.d("SearchResultsActivity", "Book Title: " + book.getTitle());
                        Log.d("SearchResultsActivity", "Book Author: " + book.getAuthor_name());
                    }

                } else {
                    textViewNoBooks.setVisibility(View.VISIBLE);
                }
            } else {

                textViewNoBooks.setVisibility(View.VISIBLE);
            }
        } else {

            textViewNoBooks.setVisibility(View.VISIBLE);
        }
        authorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authorList);
        authorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilterByAuthor.setAdapter(authorAdapter);
        authorAdapter.notifyDataSetChanged(); // Cập nhật Spinner
        fetchAuthors();

        spinnerFilterByAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAuthor = authorList.get(position).trim();

                // Log giá trị hiện tại để kiểm tra
                Log.d("SpinnerSelected", "Position: " + position + ", Selected Author: " + selectedAuthor);

                // Nếu chọn mục đầu tiên, không làm gì
                if (position == 0) {
                    return;
                }
                // Kiểm tra nếu người dùng chọn một tác giả cụ thể
                if (!selectedAuthor.equalsIgnoreCase("Lọc theo tên tác giả")) {
                    List<Book> filteredBooks = new ArrayList<>();
                    for (Book book : bookList) {
                        if (book.getAuthor_name().trim().equalsIgnoreCase(selectedAuthor)) {
                            filteredBooks.add(book);
                        }
                    }
                    updateBookList(filteredBooks); // Cập nhật danh sách sách đã lọc
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có gì được chọn
            }
        });


    }

    private boolean isUserLoggedIn() {
        // Implement your login check logic here
        return true; // Replace with actual login check
    }

    private void promptLogin() {
        // Implement your login prompt here
        Toast.makeText(this, "Please log in to view book details.", Toast.LENGTH_SHORT).show();
    }

    private String getTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    private void performSearch( String query) {
        showProgressBar(true);
        searchViewModel.searchBooks( 1000, 1000, query);

        // Observe the search results
        searchViewModel.getSearchResults().observe(this, bookSearchResponse -> {
            showProgressBar(false);
            if (bookSearchResponse != null && bookSearchResponse.getData() != null) {
                List<Book> books = bookSearchResponse.getData().getData();
                if (books != null && !books.isEmpty()) {
                    updateBookList(books);
                    textViewNoBooks.setVisibility(View.GONE);

                } else {
                    handleEmptyBookList();
                }
            } else {
                handleEmptyBookList();
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

    private void updateBookList(List<Book> books) {
        bookList.clear(); // Xóa danh sách sách cũ
        bookList.addAll(books); // Thêm sách mới nếu có
        bookAdapter.notifyDataSetChanged(); // Cập nhật adapter

        if (books.isEmpty()) {
            textViewNoBooks.setVisibility(View.VISIBLE); // Hiện thông báo "Không có sách"
            Log.d("SearchResultsActivity", "No books found, textViewNoBooks is set to VISIBLE.");
        } else {
            textViewNoBooks.setVisibility(View.GONE); // Ẩn thông báo "Không có sách"
            Log.d("SearchResultsActivity", "Books found, textViewNoBooks is set to GONE.");
        }
        // Log thông tin sách để kiểm tra
        for (Book book : bookList) {
            Log.d("SearchResultsActivity", "Book ID: " + book.getBook_id());
            Log.d("SearchResultsActivity", "Book Title: " + book.getTitle());
            Log.d("SearchResultsActivity", "Book Author: " + book.getAuthor_name());

        }
        fetchAuthors();
    }

    private void fetchAuthors() {
        // Xóa tất cả các tác giả hiện tại
        authorList.clear();
        authorList.add("Lọc theo tên tác giả");
        // Thêm mục mặc định để không lọc
        for (Book book : bookList) {
            String author = book.getAuthor_name();
            if (author != null && !authorList.contains(author)) {
                authorList.add(author);
            }
        }

        // Sắp xếp các tác giả từ vị trí 1 (bỏ qua mục mặc định)
        if (authorList.size() > 1) {
            Collections.sort(authorList.subList(1, authorList.size()));
        }

        authorAdapter.notifyDataSetChanged();
    }

    private void handleEmptyBookList() {
        // Clear the book list and update the UI
        updateBookList(new ArrayList<>()); // Clear the old book list
        textViewNoBooks.setVisibility(View.VISIBLE); // Show "No books" message
        authorList.clear(); // Clear the author list
        authorAdapter.notifyDataSetChanged(); // Update the Spinner
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SearchResultsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


}
