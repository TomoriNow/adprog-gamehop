package factory;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.factory.*;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryFactoryTest {

    @Test
    void createCategoryHandler_ReturnsCorrectHandler() {
        // Arrange
        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        // Act & Assert
        assertEquals(ToyCategoryHandler.class, CategoryFactory.createCategoryHandler(CategoryEnums.TOY, gameRepository).getClass());
        assertEquals(BoardGameHandler.class, CategoryFactory.createCategoryHandler(CategoryEnums.BOARDGAME, gameRepository).getClass());
        assertEquals(SwitchGameHandler.class, CategoryFactory.createCategoryHandler(CategoryEnums.SWITCH, gameRepository).getClass());
        assertEquals(XboxSeriesXGameHandler.class, CategoryFactory.createCategoryHandler(CategoryEnums.XBOXSERIESX, gameRepository).getClass());
        assertEquals(XboxOneGameHandler.class, CategoryFactory.createCategoryHandler(CategoryEnums.XBOXONE, gameRepository).getClass());
        assertEquals(Ps5GameHandler.class, CategoryFactory.createCategoryHandler(CategoryEnums.PS5, gameRepository).getClass());
        assertEquals(Ps4GameHandler.class, CategoryFactory.createCategoryHandler(CategoryEnums.PS4, gameRepository).getClass());
    }

    @Test
    void createCategoryHandler_ThrowsExceptionForUnsupportedCategory() {
        GameRepository gameRepository = Mockito.mock(GameRepository.class);

        assertThrows(NullPointerException.class, () -> CategoryFactory.createCategoryHandler(null, gameRepository));
    }
}
