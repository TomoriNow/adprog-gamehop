package factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.SwitchGameHandler;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SwitchGameHandlerTest {

    @Test
    void getGames_ReturnsListOfSwitchGames() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        List<GameDTO> expectedGames = Arrays.asList(
                new GameDTO(1L, "The Legend of Zelda: Breath of the Wild", 59.99, 20, CategoryEnums.SWITCH.getLabel(), 1L, "user1"),
                new GameDTO(2L, "Super Mario Odyssey", 49.99, 15, CategoryEnums.SWITCH.getLabel(), 2L, "user2")
        );
        when(gameRepository.findAllByCategory(CategoryEnums.SWITCH.getLabel())).thenReturn(expectedGames);

        SwitchGameHandler switchGameHandler = new SwitchGameHandler(gameRepository);

        List<GameDTO> actualGames = switchGameHandler.getGames();

        assertEquals(expectedGames.size(), actualGames.size());
        assertEquals(expectedGames, actualGames);
    }
}
