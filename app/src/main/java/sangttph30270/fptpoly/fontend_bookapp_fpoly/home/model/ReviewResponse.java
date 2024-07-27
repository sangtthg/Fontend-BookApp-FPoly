package sangttph30270.fptpoly.fontend_bookapp_fpoly.home.model;

import java.util.List;

public class ReviewResponse {
    private List<Review> reviews;
    private Book book;

    public ReviewResponse(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public static class Review {
        private int id;
        private int bookId;
        private String userId;
        private String reviewerName;
        private String reviewerAvatar;
        private int rating;
        private String comment;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public int getBookId() {
            return bookId;
        }

        public String getUserId() {
            return userId;
        }

        public String getReviewerName() {
            return reviewerName;
        }

        public String getReviewerAvatar() {
            return reviewerAvatar;
        }

        public int getRating() {
            return rating;
        }

        public String getComment() {
            return comment;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }

    public static class Book {
        private int bookId;
        private String title;
        private int authorId;
        private String description;
        private int categoryId;
        private int publicationYear;
        private String bookAvatar;
        private String createdAt;
        private int viewsCount;
        private int purchaseCount;
        private String oldPrice;
        private String newPrice;
        private String usedBooks;

        public int getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public int getAuthorId() {
            return authorId;
        }

        public String getDescription() {
            return description;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public int getPublicationYear() {
            return publicationYear;
        }

        public String getBookAvatar() {
            return bookAvatar;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getViewsCount() {
            return viewsCount;
        }

        public int getPurchaseCount() {
            return purchaseCount;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public String getNewPrice() {
            return newPrice;
        }

        public String getUsedBooks() {
            return usedBooks;
        }
    }
}