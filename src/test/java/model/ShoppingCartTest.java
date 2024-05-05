package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.ShoppingCart;
import id.ac.ui.cs.advprog.adproggameshop.service.GameService;

public class ShoppingCartTest {

    private ShoppingCart cart;
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        cart = ShoppingCart.getInstance();
        cart.getItems().clear();
        gameService = mock(GameService.class);
    }

    @Test
    public void testAddItem_Positive() {
        Game game = new Game("Game 1", 50, "Description", 5, "Category", null);
        cart.addItem("Game 1", 1);
        assertTrue(cart.getItems().containsKey("Game 1"));
        assertEquals(1, (int) cart.getItems().get("Game 1"));
    }

    @Test
    public void testAddItem_NullQuantity_Negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("Game 1", null);
        });
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testAddItem_NullItemName_Negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(null, 1);
        });
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testRemoveItem_Positive() {
        cart.addItem("Game 1", 1);
        cart.removeItem("Game 1");
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testRemoveItem_NotExist_Negative() {
        cart.removeItem("Game 1");
        assertTrue(cart.getItems().isEmpty());
    }
}




