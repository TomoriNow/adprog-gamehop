package factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.XboxSeriesXGameHandler;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class XboxSeriesXGameHandlerTest {

    @Test
    void getGames_ReturnsListOfXboxSeriesXGames() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        List<GameDTO> expectedGames = Arrays.asList(
                new GameDTO(1L, "Halo Infinite", 59.99, 15, CategoryEnums.XBOXSERIESX.getLabel(), 1L, "user1"),
                new GameDTO(2L, "Forza Horizon 5", 49.99, 20, CategoryEnums.XBOXSERIESX.getLabel(), 2L, "user2")
        );
        when(gameRepository.findAllByCategory(CategoryEnums.XBOXSERIESX.getLabel())).thenReturn(expectedGames);

        XboxSeriesXGameHandler xboxSeriesXGameHandler = new XboxSeriesXGameHandler(gameRepository);

        List<GameDTO> actualGames = xboxSeriesXGameHandler.getGames();

        assertEquals(expectedGames.size(), actualGames.size());
        assertEquals(expectedGames, actualGames);
    }
}
