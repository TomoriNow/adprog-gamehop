package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void testAddItemToCart_Positive() {
        Game game = new Game("Game 1", 50, "Description", 5, "Category", null);
        when(gameService.findByProductId(1L)).thenReturn(game);
        cart.addItem("Game 1", 1);
        assertTrue(cart.getItems().containsKey("Game 1"));
        assertEquals(1, (int) cart.getItems().get("Game 1"));
    }

    @Test
    public void testAddItemToCart_Negative_NullQuantity() {
        Game game = new Game("Game 1", 50, "Description", 5, "Category", null);
        when(gameService.findByProductId(1L)).thenReturn(game);
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem("Game 1", null);
        });
        assertFalse(cart.getItems().containsKey("Game 1"));
        assertTrue(cart.getItems().isEmpty());
    }
    @Test
    public void testAddItemToCart_NullItemName() {
        Game game = new Game(null, 50, "Description", 5, "Category", null);
        when(gameService.findByProductId(1L)).thenReturn(game);
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(null, 1);
        });
        assertTrue(cart.getItems().isEmpty());
    }






}



