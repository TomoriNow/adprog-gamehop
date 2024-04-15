package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;

public class GameTest {

    @Test
    public void testGameConstructor() {
        String name = "Test Game";
        int price = 10;
        String description = "Test description";
        int quantity = 5;
        String category = "Test Category";
        User owner = new User();

        Game game = new Game(name, price, description, quantity, category, owner);

        assertNotNull(game);
        assertEquals(name, game.getName());
        assertEquals(price, game.getPrice());
        assertEquals(description, game.getDescription());
        assertEquals(quantity, game.getQuantity());
        assertEquals(category, game.getCategory());
        assertEquals(owner, game.getOwner());
    }


}
