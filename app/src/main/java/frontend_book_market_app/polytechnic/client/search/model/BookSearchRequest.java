package frontend_book_market_app.polytechnic.client.search.model;

// Lớp này đại diện cho yêu cầu tìm kiếm sách
public class BookSearchRequest {
    private int totalAll; // Tổng số lượng sách
    private int limit; // Giới hạn số lượng kết quả mỗi trang
    private Query query; // Điều kiện tìm kiếm (ở đây là từ khóa "search")

    // Constructor
    public BookSearchRequest(int totalAll, int limit, String search) {
        this.totalAll = totalAll;
        this.limit = limit;
        this.query = new Query(search);
    }

    // Getter và Setter
    public int getTotalAll() {
        return totalAll;
    }

    public void setTotalAll(int totalAll) {
        this.totalAll = totalAll;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    // Lớp con đại diện cho phần "query"
    public static class Query {
        private String search; // Từ khóa tìm kiếm

        public Query(String search) {
            this.search = search;
        }

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }


}
