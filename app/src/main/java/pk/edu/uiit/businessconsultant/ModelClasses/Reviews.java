package pk.edu.uiit.businessconsultant.ModelClasses;

public class Reviews {
    String Ratings;
    String Review;
    String uid;
    String timestamp;

    public Reviews() {
    }

    public Reviews(String ratings,String review,  String uid, String timestamp) {
        this.Ratings = ratings;
        this.Review = review;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public Reviews(String ratings) {
        Ratings = ratings;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }
}
