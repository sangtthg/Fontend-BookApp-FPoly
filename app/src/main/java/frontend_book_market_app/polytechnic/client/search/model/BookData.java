package frontend_book_market_app.polytechnic.client.search.model;

import java.util.List;

public class BookData {
    private int page;
    private int limit;
    private int total;
    private int totalAll;
    private List<Book> data; // Danh sách sách

    // Getters và setters

    public BookData(int page, int limit, int total, int totalAll, List<Book> data) {
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.totalAll = totalAll;
        this.data = data;
    }

    public BookData() {
    }

    public List<Book> getData() {
        return data;
    }

    public void setData(List<Book> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalAll() {
        return totalAll;
    }

    public void setTotalAll(int totalAll) {
        this.totalAll = totalAll;
    }
}
