package utility;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class GameFormTest {

    GameForm gameForm;

    @BeforeEach
    public void setup() {
        gameForm = new GameForm();
    }

    @Test
    public void testSetName() {

        gameForm.setName("name");

        assertEquals("name", gameForm.getName());
    }

    @Test
    public void testSetPrice() {

        gameForm.setPrice(55.5);

        assertEquals(55.5, gameForm.getPrice());
    }

    @Test
    public void testSetQuantity() {

        gameForm.setQuantity(300);

        assertEquals(300, gameForm.getQuantity());
    }

    @Test
    public void testSetCategory() {

        gameForm.setCategory("category");

        assertEquals("category", gameForm.getCategory());
    }

    @Test
    public void testSetOwner() {
        User owner = new User();
        gameForm.setOwner(owner);

        assertEquals(owner, gameForm.getOwner());
    }

    @Test
    public void testSetDescription() {

        gameForm.setDescription("description");

        assertEquals("description", gameForm.getDescription());
    }

    @Test
    public void testCreateGame() {
        gameForm.setName("name");
        gameForm.setPrice(55.5);
        gameForm.setQuantity(300);
        gameForm.setCategory("category");
        gameForm.setDescription("description");
        User owner = new User();
        gameForm.setOwner(owner);

        Game game = gameForm.createGame();

        assertEquals("name", game.getName());
        assertEquals(55.5, game.getPrice());
        assertEquals(300, game.getQuantity());
        assertEquals("category", game.getCategory());
        assertEquals("description", game.getDescription());
        assertEquals(owner, game.getOwner());
    }
}
