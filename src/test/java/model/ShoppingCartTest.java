package model;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartTest {

    private ShoppingCart cart;

    @BeforeEach
    public void setUp() {
        cart = new ShoppingCart();
        cart.getItems().clear();
    }

    @Test
    public void testAddItem_Positive() {
        Game game = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        game.setProductId(1L);
        cart.addItem(game, 1);
        assertTrue(cart.getItems().containsKey(game));
        assertEquals(1, (int) cart.getItems().get(game));
    }

    @Test
    public void testAddItem_NullGame_Negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(null, 1);
        });
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testAddItem_NullProductId_Negative() {
        Game game = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(game, 1);
        });
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testRemoveItem_Positive() {
        Game game = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        game.setProductId(1L);
        cart.addItem(game, 1);
        cart.removeItem(game);
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testRemoveItem_Negative() {
        Game game = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        game.setProductId(1L);
        cart.addItem(game, 1);
        Game gameToRemove = new Game("Game 2", 20.0, "Description", 1, "Category", null);
        gameToRemove.setProductId(2L);
        cart.removeItem(gameToRemove);
        assertTrue(cart.getItems().containsKey(game));
        assertEquals(1, (int) cart.getItems().get(game));
    }


    @Test
    public void testAddItem_UpdateQuantityForExistingGame() {
        Game existingGame = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        existingGame.setProductId(1L);
        cart.addItem(existingGame, 1);
        cart.addItem(existingGame, 2);
        assertEquals(1, cart.getItems().size());
        assertEquals(3, (int) cart.getItems().get(existingGame));
    }

    @Test
    public void testCalculateTotal_HappyPath() {
        Game game1 = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        game1.setProductId(1L);
        Game game2 = new Game("Game 2", 20.0, "Description", 1, "Category", null);
        game2.setProductId(2L);
        Game game3 = new Game("Game 3", 30.0, "Description", 1, "Category", null);
        game3.setProductId(3L);

        cart.addItem(game1, 2);
        cart.addItem(game2, 3);
        cart.addItem(game3, 1);
        double total = cart.calculateTotal();
        assertEquals(110.0, total);
    }

    @Test
    public void testCalculateTotal_EmptyCart() {
        double total = cart.calculateTotal();
        assertEquals(0.0, total);
    }

    @Test
    public void testCalculateTotal_NullQuantity() {
        Game game = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        game.setProductId(1L);
        cart.addItem(game, null);
        double total = cart.calculateTotal();
        assertEquals(0.0, total);
    }

    @Test
    public void testCalculateTotal_NullProductId() {
        Game game = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(game, 1);
        });
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testCalculateTotal_ProductIdNotNull() {
        Game game1 = new Game("Game 1", 10.0, "Description", 1, "Category", null);
        game1.setProductId(1L);
        Game game2 = new Game("Game 2", 20.0, "Description", 1, "Category", null);
        game2.setProductId(2L);
        cart.addItem(game1, 2);
        cart.addItem(game2, 3);

        double expectedTotal = (game1.getPrice() * 2) + (game2.getPrice() * 3);

        double total = cart.calculateTotal();

        assertEquals(expectedTotal, total);
    }
}








