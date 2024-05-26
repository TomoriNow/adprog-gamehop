
package controller;

import id.ac.ui.cs.advprog.adproggameshop.controller.GameController;
import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.exception.GameDoesNotExistException;
import id.ac.ui.cs.advprog.adproggameshop.exception.InsufficientFundsException;
import id.ac.ui.cs.advprog.adproggameshop.exception.NotEnoughLeftException;
import id.ac.ui.cs.advprog.adproggameshop.factory.CategoryFactory;
import id.ac.ui.cs.advprog.adproggameshop.factory.CategoryHandler;
import id.ac.ui.cs.advprog.adproggameshop.factory.Ps4GameHandler;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.service.GameService;
import id.ac.ui.cs.advprog.adproggameshop.service.UserService;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameForm;
import id.ac.ui.cs.advprog.adproggameshop.utility.OneClickBuy;
import id.ac.ui.cs.advprog.adproggameshop.utility.ReviewDTO;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



public class GameControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private UserService userService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private GameController gameController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameController = new GameController();
        gameController.gameService = gameService;
        gameController.userService = userService;
        gameController.gameRepository = gameRepository;
    }

    @Test
    void testGameListPage() {
        GameDTO gameDTO = new GameDTO(1L, "Game Name", 19.99, 10, "Action", 2L, "OwnerUsername");
        List<GameDTO> games = Collections.singletonList(gameDTO);
        when(gameService.findAllBy()).thenReturn(games);

        String viewName = gameController.gameListPage(model);

        // Use Matchers for verifying model attributes
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
        verify(model, times(1)).addAttribute(eq("games"), eq(games));
        assertEquals("gameList", viewName);
    }

    @Test
    void testPersonalGameListPage() {
        User user = new User();
        GameDTO gameDTO = new GameDTO(1L, "Game Name", 19.99, 10, "Action", 2L, "OwnerUsername");
        List<GameDTO> games = Collections.singletonList(gameDTO);
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(gameService.findAllByOwner(user)).thenReturn(games);

        String viewName = gameController.personalGameListPage(session, model);

        verify(model, times(1)).addAttribute("games", games);
        assertEquals("personalGameList", viewName);
    }

    @Test
    void testAddGamePage() {
        Model model = mock(Model.class);
        String viewName = gameController.addGamePage(new GameForm(), model);

        // Verify that category options are added to the model
        verify(model, times(1)).addAttribute(eq("categoryOptions"), anyList());
        assertEquals("addGame", viewName);
    }

    @Test
    void testAddGamePost() {
        GameForm gameForm = new GameForm();
        User user = new User();
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = gameController.addGamePost(gameForm, new BeanPropertyBindingResult(gameForm, "gameForm"), session, model, gameForm.getImageFile());

        verify(gameService, times(1)).saveWithOwner(any(Game.class), eq(user));
        assertEquals("redirect:list/personal", viewName);
    }

    @Test
    void testBuyGame() {
        User buyer = new User();
        when(session.getAttribute("userLogin")).thenReturn(buyer);

        String viewName = gameController.buyGame(model, session, "1");

        verify(gameService, times(1)).buyGame(anyLong(), eq(buyer), eq(1), any(OneClickBuy.class));
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testGameDetailPage_Happy() {
        Long gameId = 1L;
        Game game = new Game();
        game.setProductId(gameId);
        List<ReviewDTO> reviews = new ArrayList<>();
        reviews.add(new ReviewDTO("good", 4, "seller", true));
        reviews.add(new ReviewDTO("bad", 2, "buyer", false));
        User user = new User();

        when(gameService.findByProductId(gameId)).thenReturn(game);
        when(gameService.getReviewsByGame(game)).thenReturn(reviews);
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = gameController.gameDetailPage(gameId, model, session);

        verify(gameService, times(1)).findByProductId(gameId);
        verify(gameService, times(1)).getReviewsByGame(game);
        verify(session, times(1)).getAttribute("userLogin");
        assertEquals("gameDetail", viewName);
    }

    @Test
    void testGameDetailPage_Unhappy() {
        Long gameId = 1L;

        when(gameService.findByProductId(gameId)).thenReturn(null);

        String viewName = gameController.gameDetailPage(gameId, model, session);

        verify(gameService, times(1)).findByProductId(gameId);
        verify(gameService, never()).getReviewsByGame(any(Game.class));
        verify(session, never()).getAttribute("userLogin");
        assertEquals("error_page", viewName);
    }
    @Test
    void testAddReview_Happy() {
        Long gameId = 1L;
        String reviewText = "Great game!";
        int rating = 5;
        User user = new User();
        Game game = new Game();
        game.setProductId(gameId);

        when(session.getAttribute("userLogin")).thenReturn(user);
        when(gameService.findByProductId(gameId)).thenReturn(game);

        String viewName = gameController.addReview(gameId, reviewText, rating, session);

        verify(session, times(1)).getAttribute("userLogin");
        verify(gameService, times(1)).findByProductId(gameId);
        verify(gameService, times(1)).saveReview(any(Review.class));
        assertEquals("redirect:/game/" + gameId, viewName);
    }

    @Test
    void testAddReview_Unhappy() {
        Long gameId = 1L;
        String reviewText = "Great game!";
        int rating = 5;

        when(session.getAttribute("userLogin")).thenReturn(null);

        String viewName = gameController.addReview(gameId, reviewText, rating, session);

        verify(session, times(1)).getAttribute("userLogin");
        assertEquals("redirect:/login", viewName);
    }
    @Test
    void testAddReview_GameNotFound() {
        Long gameId = 1L;
        String reviewText = "Great game!";
        int rating = 5;
        User user = new User();

        when(session.getAttribute("userLogin")).thenReturn(user);
        when(gameService.findByProductId(gameId)).thenReturn(null);

        String viewName = gameController.addReview(gameId, reviewText, rating, session);

        verify(session, times(1)).getAttribute("userLogin");
        verify(gameService, times(1)).findByProductId(gameId);
        assertEquals("redirect:/game/list", viewName);
    }

    @Test
    public void testAddGamePostWithInvalidData() {
        // Mocks
        GameForm gameForm = new GameForm();
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);

        // Simulate a POST request with invalid data
        when(bindingResult.hasErrors()).thenReturn(true);

        // Call the method
        String result = gameController.addGamePost(gameForm, bindingResult, session, model, gameForm.getImageFile());

        // Verify interactions
        verify(model, times(1)).addAttribute(eq("categoryOptions"), anyList());
        assertEquals("addGame", result);
    }

    @Test
    public void testBuyGameWithNotExistError() {
        HttpSession session = new MockHttpSession();
        User buyer = new User(); // Mock User
        session.setAttribute("userLogin", buyer);
        Model model = mock(Model.class);


        String gameId = "123";
        Game game = new Game();
        game.setProductId(Long.parseLong(gameId));
        game.setName("Pokemon");
        when(gameService.buyGame(anyLong(), eq(buyer), eq(1), any(OneClickBuy.class)))
                .thenThrow(new GameDoesNotExistException(game));

        String result = gameController.buyGame(model, session, gameId);

        verify(gameService, times(1)).buyGame(anyLong(), eq(buyer), eq(1), any(OneClickBuy.class));
        verify(model, times(1)).addAttribute("error_message", "The game Pokemon no longer exists");
        verifyNoMoreInteractions(model);
        assertEquals("error_page1", result);
    }

    @Test
    public void testBuyGameWithInsufficientFundError() {
        HttpSession session = new MockHttpSession();
        User buyer = new User(); // Mock User
        session.setAttribute("userLogin", buyer);
        Model model = mock(Model.class);


        String gameId = "123";
        Game game = new Game();
        game.setProductId(Long.parseLong(gameId));
        game.setName("Pokemon");
        when(gameService.buyGame(anyLong(), eq(buyer), eq(1), any(OneClickBuy.class)))
                .thenThrow(new InsufficientFundsException(50000, 100));

        String result = gameController.buyGame(model, session, gameId);

        verify(gameService, times(1)).buyGame(anyLong(), eq(buyer), eq(1), any(OneClickBuy.class));
        verify(model, times(1)).addAttribute("error_message",
                "Insufficient funds to buy the product. \nThe total bill is 50000.0 \nYour Current Balance is 100.0");
        verifyNoMoreInteractions(model);
        assertEquals("error_page1", result);
    }

    @Test
    public void testBuyGameWithNotEnoughLeftError() {
        HttpSession session = new MockHttpSession();
        User buyer = new User(); // Mock User
        session.setAttribute("userLogin", buyer);
        Model model = mock(Model.class);


        String gameId = "123";
        Game game = new Game();
        game.setProductId(Long.parseLong(gameId));
        game.setName("Pokemon");
        when(gameService.buyGame(anyLong(), eq(buyer), eq(1), any(OneClickBuy.class)))
                .thenThrow(new NotEnoughLeftException(game));

        String result = gameController.buyGame(model, session, gameId);

        verify(gameService, times(1)).buyGame(anyLong(), eq(buyer), eq(1), any(OneClickBuy.class));
        verify(model, times(1)).addAttribute("error_message",
                "We do not have enough copies of the game: Pokemon");
        verifyNoMoreInteractions(model);
        assertEquals("error_page1", result);
    }

    @Test
    public void testRemoveGame() {
        HttpSession session = new MockHttpSession();
        Model model = mock(Model.class);


        String gameId = "123";

        String result = gameController.removeGame(model,session, gameId);

        verify(gameService, times(1)).deleteGameById(123L);
        assertEquals("redirect:list/personal", result);
    }

    @Test
    public void testGamesByCategoryWithInvalidCategory() {
        Model model = mock(Model.class);
        when(gameService.getGameRepository()).thenReturn(mock(GameRepository.class));

        String invalidCategory = "PS4 Gam";

        String result = gameController.gamesByCategory(invalidCategory, model);

        // Verify interactions
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
        verify(model, times(1)).addAttribute("error", "Invalid category: PS4 Gam");
        verifyNoMoreInteractions(model);
        assertEquals("error", result);
    }

    @Test
    public void testGamesByCategoryWithValidCategory() {
        // Arrange
        String validCategory = "PS4 Game";
        List<GameDTO> games = List.of(new GameDTO(1L, "game", 500.5, 2, "categpry", 11L, "owner")); // Replace with your actual GameDTO instances

        when(gameService.getGameRepository()).thenReturn(gameRepository);
        when(gameRepository.findAllByCategory(CategoryEnums.PS4.getLabel())).thenReturn(games);

        Model model = mock(Model.class);

        // Act
        String viewName = gameController.gamesByCategory(validCategory, model);

        // Assert
        verify(model).addAttribute(eq("categories"), any(List.class));
        verify(model).addAttribute("games", games);
        assertEquals("gameList", viewName);
    }

    @Test
    void testAddGamePost_WithValidData() throws IOException {
        // Mocks
        GameForm gameForm = mock(GameForm.class);
        BindingResult bindingResult = mock(BindingResult.class);
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        MultipartFile imageFile = mock(MultipartFile.class);
        User user = new User(); // Mock User
        byte[] imageData = new byte[] { /* some image data */ };

        // Simulate a POST request with valid data
        when(bindingResult.hasErrors()).thenReturn(false);
        when(session.getAttribute("userLogin")).thenReturn(user);
        when(gameForm.createGame()).thenReturn(new Game());
        when(imageFile.getBytes()).thenReturn(imageData);

        // Call the method
        String result = gameController.addGamePost(gameForm, bindingResult, session, model, imageFile);

        // Verify interactions
        verify(gameService, times(1)).saveWithOwner(any(Game.class), eq(user));
        assertEquals("redirect:list/personal", result);
    }

    @Test
    void testAddGamePost_WithInvalidData() throws IOException {
        // Mocks
        GameForm gameForm = new GameForm();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // Call the method
        String result = gameController.addGamePost(gameForm, bindingResult, session, model, null);

        // Verify interactions
        verify(model, times(1)).addAttribute(eq("categoryOptions"), anyList());
        assertEquals("addGame", result);
    }

    @Test
    void testGameDetailPage_WithExistingGame() {
        // Mocks
        Long gameId = 1L;
        Game game = new Game();
        game.setProductId(gameId);
        List<ReviewDTO> reviews = new ArrayList<>();
        reviews.add(new ReviewDTO("review1", 5, "user1", true));
        reviews.add(new ReviewDTO("review2", 5, "user2", false));
        User user = new User();

        when(gameService.findByProductId(gameId)).thenReturn(game);
        when(gameService.getReviewsByGame(game)).thenReturn(reviews);
        when(session.getAttribute("userLogin")).thenReturn(user);

        // Call the method
        String result = gameController.gameDetailPage(gameId, model, session);

        // Verify interactions
        verify(gameService, times(1)).findByProductId(gameId);
        verify(gameService, times(1)).getReviewsByGame(game);
        verify(session, times(1)).getAttribute("userLogin");
        assertEquals("gameDetail", result);
    }

    @Test
    void testGameDetailPage_WithNonExistingGame() {
        // Mocks
        Long gameId = 1L;

        when(gameService.findByProductId(gameId)).thenReturn(null);

        // Call the method
        String result = gameController.gameDetailPage(gameId, model, session);

        // Verify interactions
        verify(gameService, times(1)).findByProductId(gameId);
        verify(gameService, never()).getReviewsByGame(any(Game.class));
        verify(session, never()).getAttribute("userLogin");
        assertEquals("error_page", result);
    }
}
