package ru.maxlt.carbase;

public class UserReview {
    private String comment,thumbnail;
    private float stars;
    private int user_order;

    public UserReview(String comment, String thumbnail, float stars, int user_order) {
        this.comment = comment;
        this.thumbnail = thumbnail;
        this.stars = stars;
        this.user_order = user_order;
    }

    public UserReview() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public int getUser_order() {
        return user_order;
    }

    public void setUser_order(int user_order) {
        this.user_order = user_order;
    }
}
