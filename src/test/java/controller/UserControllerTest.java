package controller;

import id.ac.ui.cs.advprog.adproggameshop.controller.UserController;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.ShoppingCart;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.service.TransactionServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.service.UserServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.service.GameServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.TransactionDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TransactionServiceImpl transactionService;

    @Mock
    private GameServiceImpl gameService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    Long productId = 1L;
    String name = "Test Game";
    double price = 49.99;
    int quantity = 10;
    String category = "Adventure";
    Long ownerUserId = 123L;
    String ownerUsername = "seller123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRegisterPage() {
        String viewName = userController.getRegisterPage(model);
        assertEquals("register", viewName);
    }

    @Test
    void testGetLoginPage() {
        String viewName = userController.getLoginPage(model);
        assertEquals("login", viewName);
    }

    @Test
    void testPersonalPage() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.personalPage(session, model);

        assertEquals("personal_page", viewName);
        verify(model).addAttribute("authenticated", user);
    }

    @Test
    void testProfilePage() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.profilePage(session, model);

        assertEquals("profile_page", viewName);
        verify(model).addAttribute("authenticated", user);
    }

    @Test
    void testEditProfilePage() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.editProfilePage(session, model);

        assertEquals("edit_profile", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testEditProfile() {
        User user = new User("testuser", "test@example.com", "password");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        String viewName = userController.editProfile(user, session);

        assertEquals("redirect:/personal-page", viewName);
        verify(userService).save(user);
        verify(session).setAttribute("userLogin", user);
    }

    @Test
    void testGetProfilePage_UserExists() {
        User user = new UserBuilder("testuser", "test@example.com", "password").isSeller(false).build();
        when(userService.findUserById(1L)).thenReturn(user);

        String viewName = userController.getProfilePage(1L, model);

        assertEquals("other_user_profile", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testGetProfilePage_UserNotExists() {
        when(userService.findUserById(1L)).thenReturn(null);

        String viewName = userController.getProfilePage(1L, model);

        assertEquals("error_page", viewName);
    }

    @Test
    void testLogout() {
        // Arrange
        when(request.getSession(false)).thenReturn(session); // Mocking getSession() to return the session mock

        // Act
        String viewName = userController.logout(request);

        // Assert
        assertEquals("redirect:/login", viewName);
        verify(request).getSession(false); // Verify that getSession(false) was called
        verify(session).invalidate(); // Verify that invalidate() was called on the session mock
    }

    @Test
    void testChangeRoleSeller() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.changeRoleSeller(session, model);

        assertEquals("redirect:/personal-page", viewName);
        assertTrue(user.isSeller());
        verify(userService).save(user);
    }

    @Test
    void testTopUp() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.topUp(session, model);

        assertEquals("topUp", viewName);
    }

    @Test
    void testSubmitInteger() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.submitInteger(100, session);

        assertEquals("redirect:/personal-page", viewName);
        verify(userService).topUp(user, 100);
    }

    @Test
    void testTransactionHistory() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);
        List<TransactionDTO> transactions = Collections.emptyList();
        when(transactionService.findAllByBuyerOrSeller(user, user)).thenReturn(transactions);

        String viewName = userController.transactionHistory(session, model);

        assertEquals("transactionHistory", viewName);
        verify(model).addAttribute("transactions", transactions);
    }

    @Test
    void testViewShoppingCart_ValidUser_ReturnsShoppingCartView() {
        User user = new User();
        ShoppingCart cart = new ShoppingCart();
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(session.getAttribute("cart_" + user.getUserId())).thenReturn(cart);

        String viewName = userController.viewShoppingCart(session, model);

        assertEquals("shoppingCart", viewName);
        verify(model).addAttribute("cart", cart.getItems());
        verify(model).addAttribute("total", cart.calculateTotal());
        verify(model).addAttribute("gameService", gameService);
    }

    @Test
    void testViewShoppingCart_NullUser_RedirectsToLogin() {
        when(session.getAttribute("userLogin")).thenReturn(null);

        String viewName = userController.viewShoppingCart(session, model);

        assertEquals("redirect:/login", viewName);
    }

    @Test
    void testDeleteFromCart_NullUser_RedirectsToLogin() {
        when(session.getAttribute("userLogin")).thenReturn(null);

        String viewName = userController.deleteFromCart(1L, session);

        assertEquals("redirect:/login", viewName);
    }


    @Test
    void testAddToCart_NullUser_RedirectsToLogin() {
        when(session.getAttribute("userLogin")).thenReturn(null);

        String viewName = userController.addToCart(1L, session);

        assertEquals("redirect:/login", viewName);
    }

    @Test
    void testBuyFromCart_ValidUser_BuysGamesFromCart() {
        User buyer = new User();
        ShoppingCart cart = new ShoppingCart();
        when(session.getAttribute("userLogin")).thenReturn(buyer);
        when(session.getAttribute("cart_" + buyer.getUserId())).thenReturn(cart);

        String viewName = userController.buyFromCart(session, model);

        assertEquals("redirect:/shopping-cart", viewName);
        verify(gameService).cartBuyGames(cart, buyer);
    }

    @Test
    void testBuyFromCart_NullUser_RedirectsToLogin() {
        when(session.getAttribute("userLogin")).thenReturn(null);

        String viewName = userController.buyFromCart(session, model);

        assertEquals("redirect:/login", viewName);
    }

    @Test
    void testBuyFromCart_RuntimeExceptionThrown_ReturnsErrorPage() {
        User buyer = new User();
        ShoppingCart cart = new ShoppingCart();
        when(session.getAttribute("userLogin")).thenReturn(buyer);
        when(session.getAttribute("cart_" + buyer.getUserId())).thenReturn(cart);
        String errorMessage = "Insufficient funds";
        doThrow(new RuntimeException(errorMessage)).when(gameService).cartBuyGames(cart, buyer);

        String viewName = userController.buyFromCart(session, model);

        assertEquals("error_page1", viewName);
        assertTrue(cart.getItems().isEmpty());
        verify(model).addAttribute("error_message", errorMessage);
    }

    @Test
    void testAddToCart_ValidUser_AddsGameToCart() {
        User user = new User();
        user.setUserId(1L);
        Game game = new Game();
        game.setProductId(1L); // Set the productId property of the game
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(gameService.findByProductId(1L)).thenReturn(game);

        String viewName = userController.addToCart(1L, session);

        assertEquals("redirect:/game/list", viewName);
        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(session).setAttribute(eq("cart_" + user.getUserId()), cartCaptor.capture());
        ShoppingCart capturedCart = cartCaptor.getValue();
        assertEquals(1, capturedCart.getItems().size());
        assertTrue(capturedCart.getItems().containsKey(game));
        verify(gameService).findByProductId(1L);
    }

    @Test
    void testDeleteFromCart_ValidUser_DeletesGameFromCart() {
        User user = new User();
        user.setUserId(1L);
        ShoppingCart cart = mock(ShoppingCart.class); // Mock the ShoppingCart object
        Game game = new Game();
        game.setProductId(1L); // Set the productId property of the game
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(session.getAttribute("cart_" + user.getUserId())).thenReturn(cart);
        when(gameService.findByProductId(1L)).thenReturn(game);

        String viewName = userController.deleteFromCart(1L, session);

        assertEquals("redirect:/shopping-cart", viewName);
        verify(cart).removeItem(game);
        verify(gameService).findByProductId(1L);
    }
    @Test
    void testViewShoppingCart_CartNull_CreatesNewCart() {
        User user = new User();
        user.setUserId(1L);
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(session.getAttribute("cart_" + user.getUserId())).thenReturn(null);

        String viewName = userController.viewShoppingCart(session, model);

        assertEquals("shoppingCart", viewName);
        verify(session).setAttribute(eq("cart_" + user.getUserId()), any(ShoppingCart.class));
    }

    @Test
    void testViewShoppingCart_CartNotEmpty_PrintsCartItems() {
        User user = new User();
        user.setUserId(1L);
        ShoppingCart cart = new ShoppingCart();
        Game game = new Game();
        game.setProductId(1L);
        game.setName("Test Game");
        cart.addItem(game, 1);
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(session.getAttribute("cart_" + user.getUserId())).thenReturn(cart);

        String viewName = userController.viewShoppingCart(session, model);

        assertEquals("shoppingCart", viewName);
    }

    @Test
    void testDeleteFromCart_CartNull_SkipsDeletion() {
        User user = new User();
        user.setUserId(1L);
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(session.getAttribute("cart_" + user.getUserId())).thenReturn(null);

        String viewName = userController.deleteFromCart(1L, session);

        assertEquals("redirect:/shopping-cart", viewName);
        verify(gameService, never()).findByProductId(anyLong());
    }

    @Test
    void testDeleteFromCart_GameNull_SkipsDeletion() {
        User user = new User();
        user.setUserId(1L);
        ShoppingCart cart = mock(ShoppingCart.class);
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(session.getAttribute("cart_" + user.getUserId())).thenReturn(cart);
        when(gameService.findByProductId(1L)).thenReturn(null);

        String viewName = userController.deleteFromCart(1L, session);

        assertEquals("redirect:/shopping-cart", viewName);
        verify(cart, never()).removeItem(any(Game.class));
    }

    @Test
    void testAddToCart_CartNull_CreatesNewCart() {
        User user = new User();
        user.setUserId(1L);
        Game game = new Game();
        game.setProductId(1L);
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(session.getAttribute("cart_" + user.getUserId())).thenReturn(null);
        when(gameService.findByProductId(1L)).thenReturn(game);

        String viewName = userController.addToCart(1L, session);

        assertEquals("redirect:/game/list", viewName);
        verify(session).setAttribute(eq("cart_" + user.getUserId()), any(ShoppingCart.class));
    }

    @Test
    void testBuyFromCart_CartNull_CreatesNewCart() {
        User buyer = new User();
        buyer.setUserId(1L);
        when(session.getAttribute("userLogin")).thenReturn(buyer);
        when(session.getAttribute("cart_" + buyer.getUserId())).thenReturn(null);

        String viewName = userController.buyFromCart(session, model);

        assertEquals("redirect:/shopping-cart", viewName);
        verify(session).setAttribute(eq("cart_" + buyer.getUserId()), any(ShoppingCart.class));
    }
    @Test
    void testAddToCart_CartNotNull_AddsGameToExistingCart() {
        User user = new User();
        user.setUserId(1L);
        Game game = new Game();
        game.setProductId(1L);
        ShoppingCart cart = mock(ShoppingCart.class);
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(session.getAttribute("cart_" + user.getUserId())).thenReturn(cart);
        when(gameService.findByProductId(1L)).thenReturn(game);

        String viewName = userController.addToCart(1L, session);

        assertEquals("redirect:/game/list", viewName);
        verify(session, never()).setAttribute(eq("cart_" + user.getUserId()), any(ShoppingCart.class));
        verify(cart).addItem(eq(game), eq(1));
    }

    @Test
    void testGetProfilePage_UserIsSeller() {
        Long userId = 1L;
        User user = new UserBuilder("testuser", "test@example.com", "password").isSeller(true).build();
        List<GameDTO> games = Collections.singletonList(new GameDTO(productId, name, price, quantity, category, ownerUserId, ownerUsername));

        when(userService.findUserById(userId)).thenReturn(user);

        when(gameService.findAllByOwner(user)).thenReturn(games);

        String viewName = userController.getProfilePage(userId, model);

        assertEquals("other_user_profile", viewName);
        verify(userService).findUserById(userId);
        verify(gameService).findAllByOwner(user);
        verify(model).addAttribute("user", user);
    }
    @Test
    void testExtractGameData() {
        List<Game> games = Collections.singletonList(new Game());

        when(gameService.extractGameData()).thenReturn(games);

        String viewName = userController.extractGameData(model);

        assertEquals("gameList", viewName);
        verify(gameService).extractGameData();
        verify(model).addAttribute("games", games);
    }
    @Test
    void testRegister() {
        User user = new User("testuser", "test@example.com", "password");

        when(userService.registerUser(any(User.class))).thenReturn(user);

        String viewName = userController.register(user);

        assertEquals("redirect:/login", viewName);
        verify(userService).registerUser(any(User.class));
    }

    @Test
    void testLogin_InvalidCredentials() {
        when(userService.authenticate("testuser", "password")).thenReturn(null);

        String viewName = userController.login(new User("testuser", "test@example.com", "password"), session, model);

        assertEquals("error_page", viewName);
        verify(userService).authenticate("testuser", "password");
        verifyNoInteractions(model);
        verifyNoInteractions(session);
    }


}
