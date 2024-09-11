package frontend_book_market_app.polytechnic.client.search.model;

public class BookSearchResponse {
    private String status;
    private String message;
    private BookData data;

    // Getters và setters

    public BookSearchResponse(String status, String message, BookData data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BookSearchResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookData getData() {
        return data;
    }

    public void setData(BookData data) {
        this.data = data;
    }
}
