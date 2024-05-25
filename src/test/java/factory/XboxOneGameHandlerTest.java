package factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.XboxOneGameHandler;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class XboxOneGameHandlerTest {

    @Test
    void getGames_ReturnsListOfXboxOneGames() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        List<GameDTO> expectedGames = Arrays.asList(
                new GameDTO(1L, "Halo Infinite", 59.99, 15, CategoryEnums.XBOXONE.getLabel(), 1L, "user1"),
                new GameDTO(2L, "Forza Horizon 5", 49.99, 20, CategoryEnums.XBOXONE.getLabel(), 2L, "user2")
        );
        when(gameRepository.findAllByCategory(CategoryEnums.XBOXONE.getLabel())).thenReturn(expectedGames);

        XboxOneGameHandler xboxOneGameHandler = new XboxOneGameHandler(gameRepository);

        List<GameDTO> actualGames = xboxOneGameHandler.getGames();

        assertEquals(expectedGames.size(), actualGames.size());
        assertEquals(expectedGames, actualGames);
    }
}
