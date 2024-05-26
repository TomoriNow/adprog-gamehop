package model;

import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class GameTest {

    @Test
    public void testGameConstructor() {
        String name = "Test Game";
        double price = 10.0; // Changed to double to match the type in the Game model
        String description = "Test description";
        int quantity = 5;
        String category = "Test Category";
        User owner = new User();
        MockMultipartFile imageFile = new MockMultipartFile("imageFile", "test-image.jpg", "image/jpeg", new byte[0]);

        Game game = new Game(name, price, description, quantity, category, owner, imageFile);

        assertNotNull(game);
        assertEquals(name, game.getName());
        assertEquals(price, game.getPrice());
        assertEquals(description, game.getDescription());
        assertEquals(quantity, game.getQuantity());
        assertEquals(category, game.getCategory());
        assertEquals(owner, game.getOwner());
        assertEquals(imageFile, game.getImageFile());
    }

    @Test
    public void testGameDescription() {
        Game game = new Game();

        game.setDescription("Game from Hell");

        assertEquals("Game from Hell", game.getDescription());
    }


}
