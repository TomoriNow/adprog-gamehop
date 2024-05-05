
package controller;

import id.ac.ui.cs.advprog.adproggameshop.controller.GameController;
import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.CategoryFactory;
import id.ac.ui.cs.advprog.adproggameshop.factory.CategoryHandler;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.service.GameService;
import id.ac.ui.cs.advprog.adproggameshop.service.UserService;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameForm;
import id.ac.ui.cs.advprog.adproggameshop.utility.OneClickBuy;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class GameControllerTest {

    private GameController gameController;

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

        String viewName = gameController.addGamePost(gameForm, new BeanPropertyBindingResult(gameForm, "gameForm"), session, model);

        verify(gameService, times(1)).saveWithOwner(any(Game.class), eq(user));
        assertEquals("redirect:/personal-page", viewName);
    }

    @Test
    void testBuyGame() {
        User buyer = new User();
        when(session.getAttribute("userLogin")).thenReturn(buyer);

        String viewName = gameController.buyGame(model, session, "1");

        verify(gameService, times(1)).buyGame(anyLong(), eq(buyer), eq(1), any(OneClickBuy.class));
        assertEquals("redirect:list", viewName);
    }

}

