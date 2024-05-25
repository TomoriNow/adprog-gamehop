package factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.ToyCategoryHandler;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ToyCategoryHandlerTest {

    @Test
    void getGames_ReturnsListOfToyGames() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);
        List<GameDTO> expectedGames = Arrays.asList(
                new GameDTO(1L, "LEGO Star Wars Millennium Falcon", 149.99, 10, CategoryEnums.TOY.getLabel(), 1L, "user1"),
                new GameDTO(2L, "Barbie Dreamhouse", 99.99, 20, CategoryEnums.TOY.getLabel(), 2L, "user2")
        );
        when(gameRepository.findAllByCategory(CategoryEnums.TOY.getLabel())).thenReturn(expectedGames);

        ToyCategoryHandler toyCategoryHandler = new ToyCategoryHandler(gameRepository);

        List<GameDTO> actualGames = toyCategoryHandler.getGames();

        assertEquals(expectedGames.size(), actualGames.size());
        assertEquals(expectedGames, actualGames);
    }
}
