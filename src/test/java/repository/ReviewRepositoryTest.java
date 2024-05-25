package repository;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import id.ac.ui.cs.advprog.adproggameshop.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewRepositoryTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    public void testFindByGame() {
        Game game = new Game();
        game.setProductId(1L);

        Review review1 = new Review();
        review1.setGame(game);
        Review review2 = new Review();
        review2.setGame(game);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);

        when(reviewRepository.findByGame(any(Game.class))).thenReturn(reviews);

        List<Review> result = reviewRepository.findByGame(game);

        assertEquals(2, result.size());
    }
}