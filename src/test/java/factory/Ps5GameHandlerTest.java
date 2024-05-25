package factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.Ps5GameHandler;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class Ps5GameHandlerTest {

    @Test
    void getGames_ReturnsListOfPs5Games() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        List<GameDTO> expectedGames = Arrays.asList(
                new GameDTO(1L, "Demon's Souls", 69.99, 15, CategoryEnums.PS5.getLabel(), 1L, "user1"),
                new GameDTO(2L, "Spider-Man: Miles Morales", 59.99, 20, CategoryEnums.PS5.getLabel(), 2L, "user2")
        );
        when(gameRepository.findAllByCategory(CategoryEnums.PS5.getLabel())).thenReturn(expectedGames);

        Ps5GameHandler ps5GameHandler = new Ps5GameHandler(gameRepository);

        List<GameDTO> actualGames = ps5GameHandler.getGames();

        assertEquals(expectedGames.size(), actualGames.size());
        assertEquals(expectedGames, actualGames);
    }
}
