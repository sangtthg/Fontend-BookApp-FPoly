package frontend_book_market_app.polytechnic.client.search.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import frontend_book_market_app.polytechnic.client.home.model.HomeBookModel;
import frontend_book_market_app.polytechnic.client.home.view.BookDetailsActivity;
import frontend_book_market_app.polytechnic.client.search.adapter.AdapterAuthorSearch;
import frontend_book_market_app.polytechnic.client.search.adapter.AdapterBookPopupNew;
import frontend_book_market_app.polytechnic.client.search.adapter.BookSuggestionAdapter;
import frontend_book_market_app.polytechnic.client.search.model.Book;
import frontend_book_market_app.polytechnic.client.search.model.BookSearchResponse;
import frontend_book_market_app.polytechnic.client.search.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {
    private ImageView imageViewBack, imageViewSearch, imageViewClear;
    private EditText editTextSearch;
    private RecyclerView recyclerViewBooks, recyclerViewBookSuggestions, recyclerViewAuthors;
    private ProgressBar progressBar;
    private AdapterBookPopupNew bookAdapter;
    private AdapterAuthorSearch authorAdapter;
    private List<HomeBookModel> bookList;
    private List<HomeBookModel> authorList;
    private List<HomeBookModel> bookSuggestionList;
    private TextView textViewSuggestions;
    private SearchViewModel searchViewModel;
    private BookSuggestionAdapter bookSuggestionAdapter;
    private static final int REQUEST_CODE_SEARCH_RESULTS = 1; // Định nghĩa mã yêu cầu
    private Handler handler = new Handler();
    private Runnable searchRunnable;
    private  View view1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        imageViewClear= findViewById(R.id.imageViewClear);
        textViewSuggestions = findViewById(R.id.textViewSuggestions);
        recyclerViewBookSuggestions = findViewById(R.id.recyclerViewBookSuggestions);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewSearch = findViewById(R.id.imageViewSearch);
        editTextSearch = findViewById(R.id.editTextSearch);
        view1 = findViewById(R.id.view1);
        recyclerViewBooks = findViewById(R.id.recyclerViewBooksLISTNEW);


        imageViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSearch.setText("");
            }
        });
//        progressBar = findViewById(R.id.progressBar);
        bookList = new ArrayList<>();
        bookAdapter = new AdapterBookPopupNew(bookList, bookID -> {
            Intent intent = new Intent(SearchActivity.this, BookDetailsActivity.class);
            intent.putExtra("bookID", bookID);
            startActivity(intent);
        });
        bookSuggestionList = new ArrayList<>();


//        bookSuggestionAdapter = new BookSuggestionAdapter(bookSuggestionList);
//        recyclerViewBookSuggestions.setLayoutManager(new LinearLayoutManager(this));
//        recyclerViewBookSuggestions.setAdapter(bookSuggestionAdapter);

        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setAdapter(bookAdapter);


        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBooks.setAdapter(bookAdapter);

        // Set up RecyclerView for authors
//        authorList = new ArrayList<>();
//        authorAdapter = new AdapterAuthorSearch(authorList, position -> {
//            HomeBookModel selectedAuthor = authorList.get(position);
//            Toast.makeText(SearchActivity.this, "Selected Author: " + selectedAuthor.getAuthorName(), Toast.LENGTH_SHORT).show();
//        });
//        recyclerViewAuthors.setLayoutManager(new LinearLayoutManager(this));
//        recyclerViewAuthors.setAdapter(authorAdapter);

        // Initialize ViewModel
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Observe ViewModel data
        observeViewModel();
//        showProgressBar(true);
        searchViewModel.fetchRandomBooks();
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    // Xử lý hành động tìm kiếm hoặc hoàn thành tại đây
                    String token = getTokenFromSharedPreferences();

                    if (token != null && !token.isEmpty()) {
                        String query = editTextSearch.getText().toString().trim();

                        if (!query.isEmpty()) {
//                    showProgressBar(true);

                            // Gọi phương thức tìm kiếm với query và token
                            searchViewModel.searchBooks(token, 1000, 1000, query);

                            // Quan sát kết quả tìm kiếm
                            searchViewModel.getSearchResults().observe(SearchActivity.this, bookSearchResponse -> {
//                        showProgressBar(false);
                                handleSearchResults(bookSearchResponse, query); // Pass the query to handleSearchResults
                            });
                        } else {
                            Toast.makeText(SearchActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SearchActivity.this, "Token not found. Please log in.", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                searchRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() > 0) {
                            recyclerViewBookSuggestions.setVisibility(View.VISIBLE);
                            recyclerViewBooks.setVisibility(View.GONE);
                            textViewSuggestions.setVisibility(View.GONE);
                            view1.setVisibility(View.GONE);
                            showBookSuggestions(s.toString(), hasSuggestions -> {
                                if (!hasSuggestions) {
                                    recyclerViewBookSuggestions.setVisibility(View.GONE);
                                    recyclerViewBooks.setVisibility(View.GONE);
                                    textViewSuggestions.setVisibility(View.GONE);
                                    view1.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            recyclerViewBookSuggestions.setVisibility(View.GONE);
                            recyclerViewBooks.setVisibility(View.VISIBLE);
                            textViewSuggestions.setVisibility(View.VISIBLE);
                            view1.setVisibility(View.VISIBLE);
                            bookSuggestionList.clear();
                            if (bookSuggestionAdapter != null) {
                                bookSuggestionAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                };
                handler.postDelayed(searchRunnable, 300); // Delay 300ms
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        imageViewSearch.setOnClickListener(v -> {
            String token = getTokenFromSharedPreferences();

            if (token != null && !token.isEmpty()) {
                String query = editTextSearch.getText().toString().trim();

                if (!query.isEmpty()) {
//                    showProgressBar(true);

                    // Gọi phương thức tìm kiếm với query và token
                    searchViewModel.searchBooks(token, 1000, 1000, query);

                    // Quan sát kết quả tìm kiếm
                    searchViewModel.getSearchResults().observe(SearchActivity.this, bookSearchResponse -> {
//                        showProgressBar(false);
                        handleSearchResults(bookSearchResponse, query); // Pass the query to handleSearchResults
                    });
                } else {
                    Toast.makeText(SearchActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SearchActivity.this, "Token not found. Please log in.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle back button click
        imageViewBack.setOnClickListener(v -> onBackPressed());
    }

    private String getTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    private void observeViewModel() {
        searchViewModel.getRandomBookList().observe(this, books -> {
//            showProgressBar(false);
            if (books != null && !books.isEmpty()) {
                updateBookList(books);
            } else {
                Toast.makeText(SearchActivity.this, "No books available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBookList(List<HomeBookModel> books) {
        if (bookList == null) {
            bookList = new ArrayList<>();
        }
        if (authorList == null) {
            authorList = new ArrayList<>();
        }

        bookList.clear();
        bookList.addAll(books);

        Set<String> uniqueAuthors = new HashSet<>();
        List<HomeBookModel> uniqueAuthorModels = new ArrayList<>();

        for (HomeBookModel book : books) {
            String authorName = book.getAuthorName();
            if (authorName != null && !uniqueAuthors.contains(authorName)) {
                uniqueAuthors.add(authorName);
                HomeBookModel authorModel = new HomeBookModel();
                authorModel.setAuthorName(authorName);
                uniqueAuthorModels.add(authorModel);
            }
        }

        authorList.clear();
        authorList.addAll(uniqueAuthorModels);
        bookAdapter.notifyDataSetChanged();
        // Đảm bảo rằng authorAdapter được cập nhật nếu bạn sử dụng nó
        // authorAdapter.notifyDataSetChanged();
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

    private void handleSearchResults(BookSearchResponse bookSearchResponse, String query) {
        if (bookSearchResponse != null && bookSearchResponse.getData() != null) {
            List<Book> books = bookSearchResponse.getData().getData();

            Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
            intent.putExtra("SEARCH_QUERY", query);

            if (books != null && !books.isEmpty()) {
                intent.putExtra("BOOK_LIST", new ArrayList<>(books));
                intent.putExtra("HAS_BOOKS", true); // Thêm flag này để cho biết có sách
            } else {
                intent.putExtra("HAS_BOOKS", false); // Thêm flag này để cho biết không có sách
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, REQUEST_CODE_SEARCH_RESULTS);
        } else {
            Log.d(TAG, "No search results found.");
        }
    }


//    private void showProgressBar(boolean show) {
//        if (show) {
//            progressBar.setVisibility(View.VISIBLE);
//            recyclerViewBooks.setVisibility(View.GONE);
////            recyclerViewAuthors.setVisibility(View.GONE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//            recyclerViewBooks.setVisibility(View.VISIBLE);
////            recyclerViewAuthors.setVisibility(View.VISIBLE);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SEARCH_RESULTS) {
            if (resultCode == RESULT_OK) {
                // Xử lý kết quả nếu cần
                // Ví dụ: bạn có thể làm mới dữ liệu tìm kiếm hoặc thực hiện các thao tác khác
                searchViewModel.fetchRandomBooks(); // Tải lại dữ liệu nếu cần
            }
        }
    }

    private void showBookSuggestions(String query, SearchCallback callback) {
        // Kiểm tra nếu token hợp lệ
        String token = getTokenFromSharedPreferences();
        if (token != null && !token.isEmpty()) {
            // Gọi API để lấy kết quả tìm kiếm
            searchViewModel.searchBooks(token, 1000, 1000, query);

            searchViewModel.getSearchResults().observe(SearchActivity.this, bookSearchResponse -> {
                boolean hasSuggestions = false;
                if (query.isEmpty()) {
                    // Khi không có từ khóa, ẩn danh sách gợi ý và hiển thị danh sách sách chính
                    recyclerViewBookSuggestions.setVisibility(View.GONE);
                    recyclerViewBooks.setVisibility(View.VISIBLE);
                    textViewSuggestions.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);
                    bookSuggestionList.clear();
                    bookSuggestionAdapter.notifyDataSetChanged();
                } else {
                    // Xử lý kết quả tìm kiếm khi có từ khóa
                    if (bookSearchResponse != null && bookSearchResponse.getData() != null && bookSearchResponse.getData().getData() != null) {
                        List<Book> suggestions = bookSearchResponse.getData().getData();
                        if (suggestions != null && !suggestions.isEmpty()) {
                            // Cập nhật danh sách gợi ý sách
                            bookSuggestionList.clear();
                            for (Book book : suggestions) {
                                HomeBookModel homeBookModel = convertBookToHomeBookModel(book);
                                bookSuggestionList.add(homeBookModel);
                            }
                            // Cập nhật adapter với từ tìm kiếm
                            if (bookSuggestionAdapter == null) {
                                bookSuggestionAdapter = new BookSuggestionAdapter(bookSuggestionList, query, title -> {
                                    searchBooksByTitle(title); // Xử lý khi chọn một gợi ý
                                });
                                recyclerViewBookSuggestions.setAdapter(bookSuggestionAdapter);
                            } else {
                                bookSuggestionAdapter.setQuery(query); // Cập nhật từ khóa cho adapter
                                bookSuggestionAdapter.notifyDataSetChanged();
                            }
                            // Hiển thị gợi ý nếu có kết quả tìm kiếm
                            recyclerViewBookSuggestions.setVisibility(View.VISIBLE);
                            recyclerViewBooks.setVisibility(View.GONE);
                            textViewSuggestions.setVisibility(View.GONE);
                            view1.setVisibility(View.GONE);
                            hasSuggestions = true;
                        } else {
                            // Không có kết quả tìm kiếm
                            recyclerViewBookSuggestions.setVisibility(View.GONE);
                            recyclerViewBooks.setVisibility(View.GONE);
                            textViewSuggestions.setVisibility(View.GONE);
                            view1.setVisibility(View.GONE);
                        }
                    } else {
                        // Không có kết quả từ API
                        recyclerViewBookSuggestions.setVisibility(View.GONE);
                        recyclerViewBooks.setVisibility(View.GONE);
                        textViewSuggestions.setVisibility(View.GONE);
                        view1.setVisibility(View.GONE);
                    }
                }
                // Gọi callback với kết quả tìm kiếm
                if (callback != null) {
                    callback.onSearchCompleted(hasSuggestions);
                }
            });
        } else {
            Toast.makeText(SearchActivity.this, "Token not found. Please log in.", Toast.LENGTH_SHORT).show();
            if (callback != null) {
                callback.onSearchCompleted(false);
            }
        }
    }

    public interface SearchCallback {
        void onSearchCompleted(boolean hasSuggestions);
    }


    private HomeBookModel convertBookToHomeBookModel(Book book) {
        HomeBookModel homeBookModel = new HomeBookModel();
        homeBookModel.setBookId(book.getBook_id()); // Map fields as needed
        homeBookModel.setTitle(book.getTitle());
        homeBookModel.setAuthorName(book.getAuthor_name());
        // Set other fields as necessary
        return homeBookModel;
    }
    private void searchBooksByTitle(String title) {
        // Hiển thị tiêu đề sách lên màn hình bằng Toast
        editTextSearch.setText(title);
        Toast.makeText(SearchActivity.this, "Title: " + title, Toast.LENGTH_SHORT).show();
    }



}
