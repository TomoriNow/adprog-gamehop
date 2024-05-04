package controller;

import id.ac.ui.cs.advprog.adproggameshop.AdprogGameshopApplication;
import id.ac.ui.cs.advprog.adproggameshop.controller.GameController;
import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.service.GameService;
import id.ac.ui.cs.advprog.adproggameshop.service.UserService;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.OneClickBuy;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(GameController.class)
@AutoConfigureMockMvc
public class GameControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    @Test
    void testTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/game/test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGameListPage() throws Exception {
        List<GameDTO> games = Arrays.asList(
                new GameDTO(1L, "Game1", 10.0, 5, "Category1", 1L, "Owner1"),
                new GameDTO(2L, "Game2", 20.0, 3, "Category2", 2L, "Owner2")
        );
        when(gameService.findAllBy()).thenReturn(games);

        mockMvc.perform(MockMvcRequestBuilders.get("/game/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("gameList"))
                .andExpect(MockMvcResultMatchers.model().attribute("games", games));
    }

    @Test
    void testPersonalGameListPage() throws Exception {
        User user = new User();
        List<GameDTO> games = Arrays.asList(
                new GameDTO(1L, "Game1", 10.0, 5, "Category1", 1L, "Owner1"),
                new GameDTO(2L, "Game2", 20.0, 3, "Category2", 2L, "Owner2")
        );
        when(gameService.findAllByOwner(user)).thenReturn(games);

        mockMvc.perform(MockMvcRequestBuilders.get("/game/list/personal")
                        .sessionAttr("userLogin", user))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("personalGameList"))
                .andExpect(MockMvcResultMatchers.model().attribute("games", games));
    }

    @Test
    void testAddGamePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/game/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("addGame"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categoryOptions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("game"));
    }

    @Test
    void testAddGamePost() throws Exception {
        User user = new User("username", "email", "password", 100, "bio", new byte[]{}, false);
        when(userService.save(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/game/create")
                        .sessionAttr("userLogin", user)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Test Game")
                        .param("price", "10")
                        .param("description", "Test Description")
                        .param("quantity", "5")
                        .param("category", "Test Category"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/personal-page"));

        verify(gameService, times(1)).saveWithOwner(any(Game.class), eq(user));
    }

    @Test
    void testBuyGame() throws Exception {
        User user = new User();
        Game purchasedGame = new Game();
        when(gameService.buyGame(anyLong(), any(User.class), anyInt(), any(OneClickBuy.class))).thenReturn(purchasedGame);

        mockMvc.perform(MockMvcRequestBuilders.post("/game/buy")
                        .sessionAttr("userLogin", user)
                        .param("gameId", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/game/list"));

        verify(gameService, times(1)).buyGame(eq(1L), eq(user), eq(1), any(OneClickBuy.class));
    }




}
