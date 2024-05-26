package utility;

import id.ac.ui.cs.advprog.adproggameshop.utility.ReviewDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ReviewDTOTest {
    ReviewDTO reviewDTO;
    @BeforeEach
    public void setup() {
        reviewDTO = new ReviewDTO("good", 4, "username", true);
    }

    @Test
    public void testConstructor() {
        assertEquals("good", reviewDTO.getReviewText());
        assertEquals(4, reviewDTO.getRating());
        assertEquals("username", reviewDTO.getUserUsername());
        assertTrue(reviewDTO.isUserIsSeller());
    }

    @Test
    public void testSetReviewText() {
        reviewDTO.setReviewText("bad");

        assertEquals("bad", reviewDTO.getReviewText());
        assertEquals(4, reviewDTO.getRating());
        assertEquals("username", reviewDTO.getUserUsername());
        assertTrue(reviewDTO.isUserIsSeller());
    }

    @Test
    public void testSetRating() {
        reviewDTO.setRating(2);

        assertEquals("good", reviewDTO.getReviewText());
        assertEquals(2, reviewDTO.getRating());
        assertEquals("username", reviewDTO.getUserUsername());
        assertTrue(reviewDTO.isUserIsSeller());
    }

    @Test
    public void testSetUserUsername() {
        reviewDTO.setUserUsername("username2");

        assertEquals("good", reviewDTO.getReviewText());
        assertEquals(4, reviewDTO.getRating());
        assertEquals("username2", reviewDTO.getUserUsername());
        assertTrue(reviewDTO.isUserIsSeller());
    }

    @Test
    public void testSetUserIsSeller() {
        reviewDTO.setUserIsSeller(false);

        assertEquals("good", reviewDTO.getReviewText());
        assertEquals(4, reviewDTO.getRating());
        assertEquals("username", reviewDTO.getUserUsername());
        assertFalse(reviewDTO.isUserIsSeller());
    }
}

