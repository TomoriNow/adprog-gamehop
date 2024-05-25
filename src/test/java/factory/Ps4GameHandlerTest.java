package factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.Ps4GameHandler;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class Ps4GameHandlerTest {

    @Test
    void getGames_ReturnsListOfPs4Games() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        List<GameDTO> expectedGames = Arrays.asList(
                new GameDTO(1L, "The Last of Us Part II", 59.99, 20, CategoryEnums.PS4.getLabel(), 1L, "user1"),
                new GameDTO(2L, "Ghost of Tsushima", 49.99, 15, CategoryEnums.PS4.getLabel(), 2L, "user2")
        );
        when(gameRepository.findAllByCategory(CategoryEnums.PS4.getLabel())).thenReturn(expectedGames);

        Ps4GameHandler ps4GameHandler = new Ps4GameHandler(gameRepository);

        List<GameDTO> actualGames = ps4GameHandler.getGames();

        assertEquals(expectedGames.size(), actualGames.size());
        assertEquals(expectedGames, actualGames);
    }
}
