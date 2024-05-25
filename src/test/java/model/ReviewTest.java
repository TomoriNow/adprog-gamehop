package model;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewTest {

    @Test
    public void testReviewModel() {
        Review review = new Review();
        review.setId(1L);
        review.setUser(new User());
        review.setGame(new Game());
        review.setReviewText("Great game!");
        review.setRating(5);

        assertEquals(1L, review.getId());
        assertEquals("Great game!", review.getReviewText());
        assertEquals(5, review.getRating());
    }
    @Test
    public void testGetUser() {
        Review review = new Review();
        User user = new User();
        review.setUser(user);

        assertEquals(user, review.getUser());
    }

    @Test
    public void testGetGame() {
        Review review = new Review();
        Game game = new Game();
        review.setGame(game);

        assertEquals(game, review.getGame());
    }
}