package utility;

import id.ac.ui.cs.advprog.adproggameshop.enums.CategoryEnums;
import id.ac.ui.cs.advprog.adproggameshop.utility.CategoryOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class CategoryOptionTest {
    @Test
    public void testConstructor() {
        CategoryOption categoryOption = new CategoryOption(CategoryEnums.PS4.getLabel(), CategoryEnums.PS4.getLabel());

        assertEquals(CategoryEnums.PS4.getLabel(), categoryOption.getLabel());
        assertEquals(CategoryEnums.PS4.getLabel(), categoryOption.getValue());
    }

    @Test
    public void testSetLabel() {
        CategoryOption categoryOption = new CategoryOption(CategoryEnums.PS4.getLabel(), CategoryEnums.PS4.getLabel());

        categoryOption.setLabel(CategoryEnums.PS5.getLabel());

        assertEquals(CategoryEnums.PS5.getLabel(), categoryOption.getLabel());
        assertEquals(CategoryEnums.PS4.getLabel(), categoryOption.getValue());
    }

    @Test
    public void testSetValue() {
        CategoryOption categoryOption = new CategoryOption(CategoryEnums.PS4.getLabel(), CategoryEnums.PS4.getLabel());

        categoryOption.setValue(CategoryEnums.TOY.getLabel());

        assertEquals(CategoryEnums.PS4.getLabel(), categoryOption.getLabel());
        assertEquals(CategoryEnums.TOY.getLabel(), categoryOption.getValue());
    }

    @Test
    public void testEnums() {
        assertEquals(CategoryEnums.PS4, CategoryEnums.fromString("PS4 Game"));
        assertEquals(CategoryEnums.PS5, CategoryEnums.fromString("PS5 Game"));
        assertEquals(CategoryEnums.XBOXONE, CategoryEnums.fromString("Xbox One Game"));
        assertEquals(CategoryEnums.XBOXSERIESX, CategoryEnums.fromString("Xbox Series X Game"));
        assertEquals(CategoryEnums.TOY, CategoryEnums.fromString("Toy"));
        assertEquals(CategoryEnums.BOARDGAME, CategoryEnums.fromString("Board Game"));
        assertEquals(CategoryEnums.SWITCH, CategoryEnums.fromString("Switch Game"));
        assertNull(CategoryEnums.fromString("Not a Game"));
    }
}
