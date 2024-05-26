package id.ac.ui.cs.advprog.adproggameshop.utility;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewDTO {
    private String reviewText;
    private int rating;
    private String userUsername;
    private boolean userIsSeller;

    public ReviewDTO(String reviewText,  int rating, String userUsername, boolean userIsSeller) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.userUsername = userUsername;
        this.userIsSeller = userIsSeller;
    }
}
