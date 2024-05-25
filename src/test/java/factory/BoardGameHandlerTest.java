package factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.BoardGameHandler;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BoardGameHandlerTest {

    @Test
    void getGames_ReturnsListOfBoardGames() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        List<GameDTO> expectedGames = Arrays.asList(
                new GameDTO(1L, "Catan", 49.99, 10, CategoryEnums.BOARDGAME.getLabel(), 1L, "user1"),
                new GameDTO(2L, "Ticket to Ride", 39.99, 15, CategoryEnums.BOARDGAME.getLabel(), 2L, "user2")
        );
        when(gameRepository.findAllByCategory(CategoryEnums.BOARDGAME.getLabel())).thenReturn(expectedGames);

        BoardGameHandler boardGameHandler = new BoardGameHandler(gameRepository);

        List<GameDTO> actualGames = boardGameHandler.getGames();

        assertEquals(expectedGames.size(), actualGames.size());
        assertEquals(expectedGames, actualGames);
    }
}
