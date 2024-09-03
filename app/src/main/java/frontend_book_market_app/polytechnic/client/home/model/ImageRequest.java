package frontend_book_market_app.polytechnic.client.home.model;

public class ImageRequest {
    private int page;
    private int limit;
    private Query query;

    public ImageRequest(int page, int limit, Query query) {
        this.page = page;
        this.limit = limit;
        this.query = query;
    }

    public static class Query {
        private int book_id;

        public Query(int book_id) {
            this.book_id = book_id;
        }
    }
}