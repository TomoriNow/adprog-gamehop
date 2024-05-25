package model;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class testReviewModel {
    @Test
    public void testSetID() {
        Review review = new Review();
        review.setId(1L);
        assertEquals(1L, review.getId());
    }

    @Test
    public void testSetUser() {
        Review review = new Review();
        User user = new User();
        review.setUser(user);
        assertEquals(user, review.getUser());
    }

    @Test
    public void testSetGameID() {
        Review review = new Review();
        Game game = new Game();
        review.setGame(game);
        assertEquals(game, review.getGame());
    }

    @Test
    public void testReviewText() {
        Review review = new Review();
        String reviewText = "I despise this game, utter filth, please kill me";
        review.setReviewText(reviewText);
        assertEquals(reviewText, review.getReviewText());
    }

    @Test
    public void testRating() {
        Review review = new Review();
        Integer rating = 1;
        review.setRating(rating);
        assertEquals(rating, review.getRating());
    }






}
