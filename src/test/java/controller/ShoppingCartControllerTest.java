import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.ShoppingCart;
import id.ac.ui.cs.advprog.adproggameshop.service.GameService;
import id.ac.ui.cs.advprog.adproggameshop.controller.GameController;

public class ShoppingCartControllerTest {

    private ShoppingCart cart;
    private GameService gameService;
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        cart = ShoppingCart.getInstance();
        cart.getItems().clear();
        gameService = mock(GameService.class);
        gameController = new GameController();
        gameController.setGameService(gameService);
    }

    @Test
    public void testAddToCart_Positive() {
        Game game = new Game("Game 1", 50, "Description", 5, "Category", null);
        when(gameService.findByProductId(1L)).thenReturn(game);
        String result = gameController.addToCart("1", mock(HttpSession.class));
        assertEquals("redirect:/game/list", result);
        assertTrue(cart.getItems().containsKey("Game 1"));
        assertEquals(1, (int) cart.getItems().get("Game 1"));
    }

    @Test
    public void testDeleteFromCart_Positive() {
        cart.addItem("Game 1", 1);
        String result = gameController.deleteFromCart("Game 1", mock(HttpSession.class));
        assertEquals("redirect:/game/shopping-cart", result);
        assertFalse(cart.getItems().containsKey("Game 1"));
    }

    @Test
    public void testViewShoppingCart_Positive() {
        cart.addItem("Game 1", 1);
        Model model = mock(Model.class);
        String result = gameController.viewShoppingCart(mock(HttpSession.class), model);
        assertEquals("shoppingCart", result);
        assertEquals(cart.getItems(), model.getAttribute("cart"));
    }
}

