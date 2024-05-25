package utility;

import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class GameDTOTest {

    @Test
    public void testConstructor() {
        GameDTO gameDTO = new GameDTO(1L, "name", 40.5, 20, "category",
                11L, "owner's name");

        assertEquals(1L, gameDTO.getProductId());
        assertEquals("name", gameDTO.getName());
        assertEquals(40.5, gameDTO.getPrice());
        assertEquals(20, gameDTO.getQuantity());
        assertEquals("category", gameDTO.getCategory());
        assertEquals(11L, gameDTO.getOwnerUserId());
        assertEquals("owner's name", gameDTO.getOwnerUsername());
    }

    @Test
    public void testSetProductId() {
        GameDTO gameDTO = new GameDTO(1L, "name", 40.5, 20, "category",
                11L, "owner's name");

        gameDTO.setProductId(2L);

        assertEquals(2L, gameDTO.getProductId());
        assertEquals("name", gameDTO.getName());
        assertEquals(40.5, gameDTO.getPrice());
        assertEquals(20, gameDTO.getQuantity());
        assertEquals("category", gameDTO.getCategory());
        assertEquals(11L, gameDTO.getOwnerUserId());
        assertEquals("owner's name", gameDTO.getOwnerUsername());
    }

    @Test
    public void testSetName() {
        GameDTO gameDTO = new GameDTO(1L, "name", 40.5, 20, "category",
                11L, "owner's name");

        gameDTO.setName("name2");

        assertEquals(1L, gameDTO.getProductId());
        assertEquals("name2", gameDTO.getName());
        assertEquals(40.5, gameDTO.getPrice());
        assertEquals(20, gameDTO.getQuantity());
        assertEquals("category", gameDTO.getCategory());
        assertEquals(11L, gameDTO.getOwnerUserId());
        assertEquals("owner's name", gameDTO.getOwnerUsername());
    }

    @Test
    public void testSetPrice() {
        GameDTO gameDTO = new GameDTO(1L, "name", 40.5, 20, "category",
                11L, "owner's name");

        gameDTO.setPrice(20.5);

        assertEquals(1L, gameDTO.getProductId());
        assertEquals("name", gameDTO.getName());
        assertEquals(20.5, gameDTO.getPrice());
        assertEquals(20, gameDTO.getQuantity());
        assertEquals("category", gameDTO.getCategory());
        assertEquals(11L, gameDTO.getOwnerUserId());
        assertEquals("owner's name", gameDTO.getOwnerUsername());
    }

    @Test
    public void testSetQuantity() {
        GameDTO gameDTO = new GameDTO(1L, "name", 40.5, 20, "category",
                11L, "owner's name");

        gameDTO.setQuantity(40);

        assertEquals(1L, gameDTO.getProductId());
        assertEquals("name", gameDTO.getName());
        assertEquals(40.5, gameDTO.getPrice());
        assertEquals(40, gameDTO.getQuantity());
        assertEquals("category", gameDTO.getCategory());
        assertEquals(11L, gameDTO.getOwnerUserId());
        assertEquals("owner's name", gameDTO.getOwnerUsername());
    }

    @Test
    public void testSetCategory() {
        GameDTO gameDTO = new GameDTO(1L, "name", 40.5, 20, "category",
                11L, "owner's name");

        gameDTO.setCategory("category2");

        assertEquals(1L, gameDTO.getProductId());
        assertEquals("name", gameDTO.getName());
        assertEquals(40.5, gameDTO.getPrice());
        assertEquals(20, gameDTO.getQuantity());
        assertEquals("category2", gameDTO.getCategory());
        assertEquals(11L, gameDTO.getOwnerUserId());
        assertEquals("owner's name", gameDTO.getOwnerUsername());
    }

    @Test
    public void testSetOwnerUserId() {
        GameDTO gameDTO = new GameDTO(1L, "name", 40.5, 20, "category",
                11L, "owner's name");

        gameDTO.setOwnerUserId(22L);

        assertEquals(1L, gameDTO.getProductId());
        assertEquals("name", gameDTO.getName());
        assertEquals(40.5, gameDTO.getPrice());
        assertEquals(20, gameDTO.getQuantity());
        assertEquals("category", gameDTO.getCategory());
        assertEquals(22L, gameDTO.getOwnerUserId());
        assertEquals("owner's name", gameDTO.getOwnerUsername());
    }

    @Test
    public void testSetOwnerUsername() {
        GameDTO gameDTO = new GameDTO(1L, "name", 40.5, 20, "category",
                11L, "owner's name");

        gameDTO.setOwnerUsername("other's name");

        assertEquals(1L, gameDTO.getProductId());
        assertEquals("name", gameDTO.getName());
        assertEquals(40.5, gameDTO.getPrice());
        assertEquals(20, gameDTO.getQuantity());
        assertEquals("category", gameDTO.getCategory());
        assertEquals(11L, gameDTO.getOwnerUserId());
        assertEquals("other's name", gameDTO.getOwnerUsername());
    }
}

