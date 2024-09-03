package frontend_book_market_app.polytechnic.client.home.model;

import java.util.List;

public class ImageResponse {
    private String status;
    private String message;
    private List<ImageData> data;
    private int total;
    private int page;
    private int totalPages;

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

    public List<ImageData> getData() {
        return data;
    }

    public void setData(List<ImageData> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public static class ImageData {
        private int avatar_review_id;
        private int book_id;
        private String url;

        public int getAvatarReviewId() {
            return avatar_review_id;
        }

        public void setAvatarReviewId(int avatar_review_id) {
            this.avatar_review_id = avatar_review_id;
        }

        public int getBookId() {
            return book_id;
        }

        public void setBookId(int book_id) {
            this.book_id = book_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}